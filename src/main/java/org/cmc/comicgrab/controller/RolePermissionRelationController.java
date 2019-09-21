package org.cmc.comicgrab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.entity.Role;
import org.cmc.comicgrab.entity.RolePermissionRelation;
import org.cmc.comicgrab.service.IRolePermissionRelationService;
import org.cmc.comicgrab.service.IRoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 * 菜单角色权限表 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/role-permission-relation")
public class RolePermissionRelationController {
	@Resource
	IRolePermissionRelationService rolePermissionRelationService;
	@Resource
	IRoleService roleService;

	/**保存角色所拥有的菜单权限
	 *2019年8月29日 下午2:34:17
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@PostMapping()
	public JSONObject saveRolePermissionRelation(@RequestParam("roleId")Integer roleId,@RequestParam("menuIds")String menuIds) {
		JSONObject rtnObj=new JSONObject();
		try {
			Role role=roleService.getById(roleId);
			List<RolePermissionRelation> rolePermissionRelations=new ArrayList<>();
			JSONArray menuArrays=JSONObject.parseArray(menuIds);
			fetchRolePermissionRelationsFromJSONArray(role, rolePermissionRelations, menuArrays);
			QueryWrapper<RolePermissionRelation> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("role_id_", roleId);
			rolePermissionRelationService.remove(queryWrapper);
			rolePermissionRelationService.saveBatch(rolePermissionRelations);
	    	rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), e.getMessage());
		}
    	
        return rtnObj;
	}

	/**从jsonarray里取出rolePermissionRelation放到list里
	 *2019年9月19日 上午11:42:11
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param roleId
	 * @param role
	 * @param rolePermissionRelations
	 * @param menuArrays
	 */
	private void fetchRolePermissionRelationsFromJSONArray(Role role, List<RolePermissionRelation> rolePermissionRelations, JSONArray menuArrays) {
		menuArrays.forEach(menu->{ 
			RolePermissionRelation rolePermissionRelation=new RolePermissionRelation()
					.setRoleId(role.getId())
					.setRoleName(role.getRoleName())
					.setMenuUrl(((JSONObject)menu).getString("menuUrl"))
					.setMenuId(((JSONObject)menu).getInteger("value"));
			rolePermissionRelations.add(rolePermissionRelation);
		});
	}
}
