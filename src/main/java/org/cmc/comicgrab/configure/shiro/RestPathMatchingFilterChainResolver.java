package org.cmc.comicgrab.configure.shiro;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

	public RestPathMatchingFilterChainResolver() {
		super();
	}

	public RestPathMatchingFilterChainResolver(FilterConfig filterConfig) {
		super(filterConfig);
	}

	/*
	 * *
	 * @Description 重写filterChain匹配
	 * @Param [request, response, originalChain]
	 * @Return javax.servlet.FilterChain
	 */
	@Override
	public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
		FilterChainManager filterChainManager = this.getFilterChainManager();
		if (!filterChainManager.hasChains()) {
			return null;
		}
		String requestURI = this.getPathWithinApplication(request);
		for (String pathPattern : filterChainManager.getChainNames()) {
			String[] urlArray = pathPattern.split("==");
			if (urlArray.length >= 2 && WebUtils.toHttp(request).getMethod().toUpperCase().equals(urlArray[1].toUpperCase())) {// 如果匹配请求类型
				if (pathMatches(urlArray[0], requestURI)) {
					return filterChainManager.proxy(originalChain, pathPattern);
				}
			} else if (urlArray.length == 1) {
				if (pathMatches(pathPattern, requestURI)) {
					return filterChainManager.proxy(originalChain, pathPattern);
				}
			}
		}
		return null;
	}

}