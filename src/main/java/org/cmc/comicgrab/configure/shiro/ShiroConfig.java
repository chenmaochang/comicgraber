package org.cmc.comicgrab.configure.shiro;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.tomcat.util.codec.binary.Base64;
import org.cmc.comicgrab.service.IRolePermissionRelationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {
	@Resource
	private IRolePermissionRelationService rolePermissionRelationService;

	@Bean
	public ShiroFilterFactoryBean shirFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
	    filters.put("perms", new RestAuthorizationFilter());//设置自定义的restFilter来代替原本的permsFilter
		// setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
		shiroFilterFactoryBean.setLoginUrl("/");
		shiroFilterFactoryBean.setSuccessUrl("/web/home");
		// 设置无权限时跳转的 url;
		shiroFilterFactoryBean.setUnauthorizedUrl("/web/notRole");
		// 设置拦截器
		Map<String, String> filterChainDefinitionMap = rolePermissionRelationService.loadAuthorizationFromDatabase();
		// 开放登陆接口
		filterChainDefinitionMap.put("/web/login", "anon");
		
		//filterChainDefinitionMap.put("/manager-user/changePassword", "roles[admin]");//test
		//filterChainDefinitionMap.put("/permission/permissionConfig/1", "perms[角色:权限配置]");//test
		// 其余接口一律拦截
		// filterChainDefinitionMap.put("/**", "authc");
		filterChainDefinitionMap.put("/api/**", "anon");
		// 以下是static目录下的静态资源,匿名访问静态资源
		filterChainDefinitionMap.put("/*.js", "anon");
		filterChainDefinitionMap.put("/encript/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/layui/**", "anon");
		filterChainDefinitionMap.put("/login/**", "anon");
		filterChainDefinitionMap.put("/web/logout", "logout");
		filterChainDefinitionMap.put("/anno/**", "anon");//游客访问
		// 主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
		filterChainDefinitionMap.put("/**", "user");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/**
	 * 注入 securityManager
	 */
	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(customRealm());
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	/**
	 * 自定义身份认证 realm;
	 * <p>
	 * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm， 否则会影响 CustomRealm类 中其他类的依赖注入
	 */
	@Bean
	public CustomRealm customRealm() {
		return new CustomRealm();
	}

	/**
	 * 为了在thymeleaf中使用shiro的自定义tag 
	 * 2019年8月27日 下午2:40:15 
	 * 作者 陈茂昌 
	 * email:chenmc@createw.com
	 * 
	 * @return
	 */
	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
	
	public SimpleCookie rememberMeCookie() {
	    // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
	    SimpleCookie cookie = new SimpleCookie("rememberMe");
	    // 设置cookie的过期时间，单位为秒，这里为一天
	    cookie.setMaxAge(86400);
	    return cookie;
	}
	public CookieRememberMeManager rememberMeManager() {
	    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
	    cookieRememberMeManager.setCookie(rememberMeCookie());
	    // rememberMe cookie加密的密钥 
	    cookieRememberMeManager.setCipherKey(Base64.decodeBase64("4AvVhmFLUs0KTA3Kprsdag=="));
	    return cookieRememberMeManager;
	}
}
