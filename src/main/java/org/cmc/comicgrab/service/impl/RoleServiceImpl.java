package org.cmc.comicgrab.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.cmc.comicgrab.entity.Role;
import org.cmc.comicgrab.mapper.RoleMapper;
import org.cmc.comicgrab.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
	@Resource
	private RoleMapper roleMapper;

	/**通过managerUser 的id查找拥有的role
	 *2019年8月22日 上午10:32:12
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param userId
	 * @return
	 */
	public List<Role> selectRoleByUserIds(Integer userId) {
		QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
		roleQueryWrapper.inSql(true, "id_", "select role_id_ from role_ministries_relation_ where ministries_id_ =(select ministries_id_ FROM user_ministries_ WHERE user_id_="+userId+")");
		List<Role> roles = roleMapper.selectList(roleQueryWrapper);
		return roles;
	}
}
