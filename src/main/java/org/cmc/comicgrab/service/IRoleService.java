package org.cmc.comicgrab.service;

import java.util.List;

import org.cmc.comicgrab.entity.Role;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
public interface IRoleService extends IService<Role> {
	public List<Role> selectRoleByUserIds(Integer userId);
}
