package org.cmc.comicgrab.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.common.LayuiPage;
import org.cmc.comicgrab.common.LayuiPageResult;
import org.cmc.comicgrab.entity.CommonRelation;
import org.cmc.comicgrab.entity.ManagerUser;
import org.cmc.comicgrab.entity.Ministries;
import org.cmc.comicgrab.entity.Role;
import org.cmc.comicgrab.entity.RoleMinistriesRelation;
import org.cmc.comicgrab.entity.UserMinistries;
import org.cmc.comicgrab.service.ICommonRelationService;
import org.cmc.comicgrab.service.IManagerUserService;
import org.cmc.comicgrab.service.IMinistriesService;
import org.cmc.comicgrab.service.IRoleMinistriesRelationService;
import org.cmc.comicgrab.service.IRoleService;
import org.cmc.comicgrab.service.IUserMinistriesService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 主管部门 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
@RestController
@RequestMapping("/ministries")
@Slf4j
public class MinistriesController {
	public static final String MODUAL = "ministries/";
	@Resource
	private IMinistriesService ministriesService;
	@Resource
	private IRoleMinistriesRelationService roleMinistriesRelationService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IUserMinistriesService userMinistriesService;
	@Resource
	private IManagerUserService managerUserService;
	@Resource
	private ICommonRelationService commonRelationService;

	/**
	 * 部门管理列表页面 
	 * 2019年9月2日 上午11:31:14 
	 * 作者 陈茂昌 email:chenmc@createw.com
	 * 
	 * @return
	 */
	@GetMapping("/ministriesList")
	public ModelAndView ministriesList() {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "ministriesList");
		return modelAndView;
	}

	/**
	 * 部门数据表格
	 * 2019年9月2日 上午11:30:59 
	 * 作者 陈茂昌 
	 * email:chenmc@createw.com
	 * 
	 * @param layuiPage
	 * @param ministries
	 * @return
	 */
	@PostMapping("list")
	public LayuiPageResult<Ministries> list(@ModelAttribute("page") LayuiPage layuiPage, @ModelAttribute("ministries") Ministries ministries) {
		ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		Ministries ministry = ministriesService.getMinistriesByUserId(user.getId());
		QueryWrapper<Ministries> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(ministries.getName())) {
			queryWrapper.eq("name_", ministries.getName());
		}
		queryWrapper.likeRight("path_", ministry.getPath())// 权限控制,只能查自己主部门及以下的部门
				.eq("sys_status_", Constants.SystemConstants.NORMAL.getValue())
				.orderByAsc("path_");

		Page<Ministries> page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
		return new LayuiPageResult<>(ministriesService.page(page, queryWrapper));
	}

	/**
	 * 部门编辑/创建 页面 
	 * 2019年9月2日 上午11:30:45 
	 * 作者 陈茂昌 
	 * email:chenmc@createw.com
	 * 
	 * @param parentId
	 * @param id
	 * @return
	 */
	@GetMapping("ministriesEdit")
	public ModelAndView ministriesEdit(@RequestParam(value = "parentId", required = false) Integer parentId, @RequestParam(value = "id", required = false) Integer id) {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "ministriesEdit");
		Ministries ministries = new Ministries();
		boolean ruleTogethere = false;
		if (id != null) {
			ministries = ministriesService.getById(id);
			ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
			QueryWrapper<CommonRelation> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("relation_type_", Constants.RelationType.MANAGE.getValue()).eq("slave_id_", id).inSql("master_id_", "select ministries_id_ from user_ministries_ where user_id_=" + user.getId());
			List<CommonRelation> commonRelations = commonRelationService.list(queryWrapper);
			ruleTogethere = commonRelations.size() > 0 ? true : false;
		} else {
			ministries.setParentId(parentId);
		}
		modelAndView.addObject("ministries", ministries);
		modelAndView.addObject("ruleTogethere", ruleTogethere);
		return modelAndView;
	}
	

	/**
	 * 根据父部门id获取树形部门列表 2019年9月2日 上午11:30:21 作者 陈茂昌 email:chenmc@createw.com
	 * 
	 * @param parentId
	 * @return
	 */
	@PostMapping(value = { "tree" })
	public List<Ministries> tree() {
		ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		Ministries ministries = ministriesService.getMinistriesByUserId(user.getId());
		Integer parentId = ministries.getName().equals("管理中心") ? 0 : ministries.getParentId();// 最高权限则给0否则给自己级别以下的
		return ministriesService.getMinistriesListFromTree(parentId);
	}

	/**
	 * 保存部门 2019年9月2日 上午11:29:51 作者 陈茂昌 email:chenmc@createw.com
	 * 
	 * @param ministries
	 * @return
	 */
	@PostMapping()
	public JSONObject saveMinistries(@ModelAttribute("ministries") Ministries ministries, @RequestParam(name = "ruleTogethere", required = false) boolean ruleTogethere) {
		JSONObject rtnObj = new JSONObject();
		try {
			ministriesService.saveOrUpdate(ministries);
			ministriesService.generatePath(ministries);// 设置路径
			ministriesService.updateById(ministries);// 再次保存
			initAdmin(ministries);
			if (ruleTogethere) {// 级联更新部门关系
				setCommonRelation(ministries);
			} else {
				ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
				QueryWrapper<CommonRelation> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("relation_type_", Constants.RelationType.MANAGE.getValue()).eq("slave_id_", ministries.getId()).inSql("master_id_", "select ministries_id_ from user_ministries_ where user_id_=" + user.getId());
				commonRelationService.remove(queryWrapper);
			}
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			log.error("部门保存失败" + ministries);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			e.printStackTrace();
		}
		return rtnObj;
	}

	/**初始化部门管理员
	 *2019年9月19日 下午1:51:04
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param ministries
	 */
	private void initAdmin(Ministries ministries) {
		QueryWrapper<ManagerUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.inSql("id_", "select user_id_ from user_ministries_ where ministries_id_ = " + ministries.getId());
		queryWrapper.eq("sys_status_", Constants.SystemConstants.NORMAL.getValue());
		List<ManagerUser> managerUsers = managerUserService.list(queryWrapper);
		if (managerUsers.size() <= 0) {
			ManagerUser managerUser = new ManagerUser().setAccount(ministries.getName() + "管理员").setName(ministries.getName() + "管理员").setPassword("e10adc3949ba59abbe56e057f20f883e");// 123456
			managerUserService.save(managerUser);
			UserMinistries userMinistries = new UserMinistries().setMinistriesId(ministries.getId()).setMinistriesName(ministries.getName()).setUserId(managerUser.getId()).setUserName(managerUser.getName());
			userMinistriesService.save(userMinistries);
		}
	}

	/**
	 * 2019年9月12日 下午3:49:41 
	 * 作者 陈茂昌 
	 * email:chenmc@createw.com
	 * 
	 * @param ministries被管理的部门
	 */
	private void setCommonRelation(Ministries ministries) {
		ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		Integer ministriesId = ministries.getId();
		Integer userId = user.getId();

		Ministries userMainMinistries = ministriesService.getMinistriesByUserId(userId);//用户主部门

		QueryWrapper<CommonRelation> commonRelationQueryWrapper = new QueryWrapper<>();
		commonRelationQueryWrapper.eq("relation_type_", Constants.RelationType.MANAGE.getValue()).eq("slave_id_", ministriesId).eq("master_id_", userMainMinistries.getId());
		List<CommonRelation> commonRelations = commonRelationService.list(commonRelationQueryWrapper);
		if (commonRelations.size() == 0) {
			CommonRelation commonRelation = new CommonRelation();
			commonRelation.setMasterId(userMainMinistries.getId()).setSlaveId(ministriesId).setCreateId(userId).setRelationType("MANAGE");
			commonRelationService.save(commonRelation);
		}
	}

	/**
	 * 级联逻辑删除部门,物理删除角色关系 2019年9月2日 下午3:02:05 作者 陈茂昌 email:chenmc@createw.com
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	public JSONObject deleteMinistries(@PathVariable(name = "id") Integer id) {
		JSONObject rtnObj = new JSONObject();
		try {
			List<Ministries> ministries = ministriesService.getMinistriesListFromTree(id);
			ministries.add(ministriesService.getById(id));
			List<Integer> ministriesIdList = new ArrayList<>();
			ministries.forEach(ministry -> {
				ministry.setSysStatus(Constants.SystemConstants.FORBIDDEN.getValue());
				ministriesIdList.add(ministry.getId());
			});
			ministriesService.updateBatchById(ministries);// 批量逻辑删除
			if (!ministriesIdList.isEmpty()) {
				removeRoleMinistriesRElation(ministriesIdList);
				removeUserMinistries(ministriesIdList);
			}
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			log.error("部门删除失败:" + e.getMessage());
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
		}
		return rtnObj;
	}

	/**
	 * 根据部门id list物理删除用户部门关系 
	 * 2019年9月3日 上午11:14:23 
	 * 作者 陈茂昌 
	 * email:chenmc@createw.com
	 * 
	 * @param ministriesIdList
	 */
	private void removeUserMinistries(List<Integer> ministriesIdList) {
		QueryWrapper<UserMinistries> UserMinistriesQueryWrapper = new QueryWrapper<>();
		UserMinistriesQueryWrapper.in("ministries_id_", ministriesIdList);
		userMinistriesService.remove(UserMinistriesQueryWrapper);
	}

	/**
	 * 根据部门id list物理删除角色部门关系 
	 * 2019年9月3日 上午11:13:45 
	 * 作者 陈茂昌 
	 * email:chenmc@createw.com
	 * 
	 * @param ministriesIdList
	 */
	private void removeRoleMinistriesRElation(List<Integer> ministriesIdList) {
		QueryWrapper<RoleMinistriesRelation> RoleMinistriesRelationQueryWrapper = new QueryWrapper<>();
		RoleMinistriesRelationQueryWrapper.in("ministries_id_", ministriesIdList);
		roleMinistriesRelationService.remove(RoleMinistriesRelationQueryWrapper);
	}

	/**
	 * 部门配置角色 
	 * 2019年9月18日 下午3:10:03 
	 * 作者 陈茂昌 
	 * email:chenmc@createw.com
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("roleConfig/{id}")
	public ModelAndView roleConfig(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "roleConfig");
		QueryWrapper<RoleMinistriesRelation> roleMinistriesRelationQueryWrapper = new QueryWrapper<>();
		roleMinistriesRelationQueryWrapper.eq("ministries_id_", id);
		List<RoleMinistriesRelation> roleMinistriesRelations = roleMinistriesRelationService.list(roleMinistriesRelationQueryWrapper);
		QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
		roleQueryWrapper.eq("sys_status_", Constants.SystemConstants.NORMAL.getValue());
		List<Role> roles = roleService.list(roleQueryWrapper);
		modelAndView.addObject("ministriesId", id).addObject("roleMinistriesRelations", roleMinistriesRelations).addObject("roles", roles);
		return modelAndView;
	}
	
	@GetMapping("viceMinistries/{id}")
	public ModelAndView viceMinistries(@PathVariable("id")Integer id) {
		Ministries ministry = ministriesService.getMinistriesByUserId(id);
		
		QueryWrapper<Ministries> ministriesWrapper=new QueryWrapper<>();
		ministriesWrapper.ne("id_", ministry.getId()).eq("sys_status_", Constants.SystemConstants.NORMAL.getValue());
		List<Ministries> ministries=ministriesService.list(ministriesWrapper);//除了自己主部门之外的部门
		
		QueryWrapper<CommonRelation> commonRelationWrapper=new QueryWrapper<>();
		commonRelationWrapper.eq("relation_type_", Constants.RelationType.VICE_MINISTRIES.getValue()).eq("slave_id_", id);
		List<CommonRelation> commonRelations=commonRelationService.list(commonRelationWrapper);
		
		ModelAndView modelAndView = new ModelAndView(MODUAL + "viceMinistries");
		modelAndView.addObject("ministries", ministries).addObject("commonRelations", commonRelations).addObject("userId", id);
		return modelAndView;
	}

}
