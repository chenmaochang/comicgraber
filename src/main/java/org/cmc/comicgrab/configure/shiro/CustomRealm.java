package org.cmc.comicgrab.configure.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cmc.comicgrab.entity.ManagerUser;
import org.cmc.comicgrab.entity.Role;
import org.cmc.comicgrab.service.IManagerUserService;
import org.cmc.comicgrab.service.IMenuService;
import org.cmc.comicgrab.service.IRoleService;

public class CustomRealm extends AuthorizingRealm {
	@Resource
	private IManagerUserService managerUserService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IMenuService menuService;
	/**
	 * 获取身份验证信息 Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
	 *
	 * @param authenticationToken 用户身份信息 token
	 * @return 返回封装了用户信息的 AuthenticationInfo 实例
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		ManagerUser user = new ManagerUser();
		List<ManagerUser> managerUsers=managerUserService.selectListByAccount(token.getUsername());
		if (managerUsers.size() > 0) {
			user = managerUsers.get(0);// 从数据库获取对应用户名密码的用户
			String password = user.getPassword();
			if (!password.equals(new String((char[])token.getCredentials()))) {
				throw new AccountException("密码不正确");
			}
		} else {
			throw new AccountException("用户名不正确");
		}
		SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user, new String((char[])token.getCredentials()), getName());
		return authenticationInfo;
	}

	/**
	 * 获取授权信息
	 *
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		ManagerUser managerUser=(ManagerUser) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获得该用户角色
		Set<String> set = new HashSet<>();
		List<Role> roles=roleService.selectRoleByUserIds(managerUser.getId());
		roles.forEach(role->set.add(role.getRoleName()));
		// 设置该用户拥有的角色
		info.setRoles(set);
		Set<String> stringPermissions=menuService.getPermissionMenuByRoles(managerUser.getId());
		info.setStringPermissions(stringPermissions);
		return info;
	}
}
