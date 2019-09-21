package org.cmc.comicgrab.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.entity.Ministries;
import org.cmc.comicgrab.entity.Role;
import org.cmc.comicgrab.entity.RoleMinistriesRelation;
import org.cmc.comicgrab.entity.RolePermissionRelation;
import org.cmc.comicgrab.service.IMinistriesService;
import org.cmc.comicgrab.service.IRoleMinistriesRelationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 * 角色部门关联表 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/role-ministries-relation")
public class RoleMinistriesRelationController {
@Resource
private IMinistriesService ministriesService;
@Resource
private IRoleMinistriesRelationService roleMinistriesRelationService;
	
	@PostMapping()
	public JSONObject saveRoleMinistriesRelation(@RequestParam("ministriesId")Integer ministriesId,@RequestParam("roleIds")String roleIds) {
		JSONObject rtnObj=new JSONObject();
		try {
			Ministries ministries=ministriesService.getById(ministriesId);
			List<RoleMinistriesRelation> roleMinistriesRelations=new ArrayList<>();
			JSONArray menuArrays=JSONObject.parseArray(roleIds);
			menuArrays.forEach(role->{
				RoleMinistriesRelation roleMinistriesRelation=new RoleMinistriesRelation()
						.setRoleId((((JSONObject)role).getInteger("value")))
						.setRoleName((((JSONObject)role).getString("title")))
						.setMinistriesId(ministriesId)
						.setMinistriesName(ministries.getName());
				roleMinistriesRelations.add(roleMinistriesRelation);
			});
			QueryWrapper<RoleMinistriesRelation> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("ministries_id_", ministriesId);
			roleMinistriesRelationService.remove(queryWrapper);
			roleMinistriesRelationService.saveBatch(roleMinistriesRelations);
	    	rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), e.getMessage());
		}
    	
        return rtnObj;
	}
}
