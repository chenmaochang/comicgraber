package org.cmc.comicgrab.service;

import java.util.Map;

import org.cmc.comicgrab.entity.RolePermissionRelation;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜单角色权限表 服务类
 * </p>
 *
 * @author cmc
 * @since 2019-08-28
 */
public interface IRolePermissionRelationService extends IService<RolePermissionRelation> {

	public Map<String, String> loadAuthorizationFromDatabase();

}
