package org.cmc.comicgrab.controller;

import java.util.List;

import javax.annotation.Resource;

import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.entity.Menu;
import org.cmc.comicgrab.entity.RolePermissionRelation;
import org.cmc.comicgrab.service.IMenuService;
import org.cmc.comicgrab.service.IRolePermissionRelationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * <p>
 * 资源权限 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
	public static final String MODUAL = "permission/";
	@Resource
	private IRolePermissionRelationService rolePermissionRelationService;
	@Resource
	private IMenuService menuService;

	@GetMapping("permissionConfig/{roleId}")
	public ModelAndView permissionConfig(@PathVariable("roleId")Integer roleId) {
		ModelAndView modelAndView=new ModelAndView(MODUAL+"permissionConfig");
		
		QueryWrapper<RolePermissionRelation> rolePermissionRelationQueryWrapper=new QueryWrapper<>();
		rolePermissionRelationQueryWrapper.eq("role_id_", roleId).eq("sys_status_", 1);
		List<RolePermissionRelation> rolePermissionRelations=rolePermissionRelationService.list(rolePermissionRelationQueryWrapper);
		QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
		menuQueryWrapper.eq("sys_status_", Constants.SystemConstants.NORMAL.getValue());
		List<Menu> menus = menuService.list(menuQueryWrapper);
		modelAndView.addObject("roleId", roleId).addObject("rolePermissionRelations", rolePermissionRelations).addObject("menus", menus);
		return modelAndView;
	}
	
}
