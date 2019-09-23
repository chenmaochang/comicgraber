package org.cmc.comicgrab.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.common.LayuiPage;
import org.cmc.comicgrab.common.LayuiPageResult;
import org.cmc.comicgrab.entity.Role;
import org.cmc.comicgrab.entity.RolePermissionRelation;
import org.cmc.comicgrab.service.IRolePermissionRelationService;
import org.cmc.comicgrab.service.IRoleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {
	public static final String MODUAL = "role/";
	@Resource
	IRoleService roleService;
	@Resource
	IRolePermissionRelationService rolePermissionRelationService;

	@PostMapping("list")
	public LayuiPageResult<Role> list(@ModelAttribute("page")LayuiPage layuiPage,@ModelAttribute("role")Role role) {
		QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq(StringUtils.isNotBlank(role.getRoleName()),"role_name_", role.getRoleName());
		Page<Role> page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
		return new LayuiPageResult<>(roleService.page(page, queryWrapper));
	}
	
	@GetMapping("roleList")
	public ModelAndView roleList() {
		ModelAndView modelAndView=new ModelAndView(MODUAL+"roleList");
		return modelAndView;
	}
	
	/**添加角色
	 *2019年8月28日 下午4:45:50
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param role
	 * @return
	 */
	@PostMapping()
	public JSONObject add(@ModelAttribute("role")Role role) {
		JSONObject rtnObj=new JSONObject();
		try {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), roleService.save(role));
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), e.getMessage());
		}
		return rtnObj;
	}
	
	/**删除角色
	 *2019年8月28日 下午5:00:48
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	public JSONObject delete(@PathVariable(name="id")Integer id) {
		JSONObject rtnObj=new JSONObject();
		try {
			roleService.removeById(id);
			QueryWrapper<RolePermissionRelation> rolePermissionRelationQueryWrapper=new QueryWrapper<>();
			rolePermissionRelationQueryWrapper.eq("role_id_", id);
			rolePermissionRelationService.remove(rolePermissionRelationQueryWrapper);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), e.getMessage());
		}
		return rtnObj;
	}

}
