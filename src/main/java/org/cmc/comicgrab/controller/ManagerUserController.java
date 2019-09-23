package org.cmc.comicgrab.controller;


import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.common.LayuiPage;
import org.cmc.comicgrab.common.LayuiPageResult;
import org.cmc.comicgrab.entity.CommonRelation;
import org.cmc.comicgrab.entity.ManagerUser;
import org.cmc.comicgrab.entity.Ministries;
import org.cmc.comicgrab.entity.UserMinistries;
import org.cmc.comicgrab.service.ICommonRelationService;
import org.cmc.comicgrab.service.IManagerUserService;
import org.cmc.comicgrab.service.IMinistriesService;
import org.cmc.comicgrab.service.IUserMinistriesService;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
 * 管理员表 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-08-22
 */
@RestController
@RequestMapping("/manager-user")
@Slf4j
public class ManagerUserController {
	public static final String MODUAL = "manager-user/";
	@Resource
	private IManagerUserService managerUserService;
	@Resource
	private IMinistriesService ministriesService;
	@Resource
	private IUserMinistriesService userMinistriesService;
	@Resource
	private ICommonRelationService commonRelationService;
	
	/**修改密码
	 *2019年8月28日 下午3:13:21
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@PutMapping("/password")
	@RequiresAuthentication
	public JSONObject changePassword(@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword) {
		ManagerUser user= (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		JSONObject rtnObj=new JSONObject();
		ManagerUser managerUser=managerUserService.selectByAccount(user.getAccount());
		try {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), managerUserService.changePassword(managerUser, oldPassword, newPassword));
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(),e.getMessage());
		}
		return rtnObj;
	}
	
	@GetMapping("/editPassword")
	public ModelAndView editPassword() {
		ModelAndView modelAndView=new ModelAndView("manager-user/editPassword");
        return modelAndView;
	}	
	@GetMapping("/editUserInfo")
	public ModelAndView editUserInfo() {
		ModelAndView modelAndView=new ModelAndView("manager-user/editUserInfo");
		ManagerUser user= (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		Ministries ministries=ministriesService.getMinistriesByUserId(user.getId());
		modelAndView.addObject("ministriesId", ministries.getId());
        return modelAndView.addObject("user", user);
	}
	
	@GetMapping("/editUser")
	public ModelAndView editUser(@RequestParam(name="userId",required=false)Integer userId) {
		ModelAndView modelAndView=new ModelAndView("manager-user/editUser");
		ManagerUser managerUser=new ManagerUser();
		if(userId!=null) {
			QueryWrapper<ManagerUser> userQueryWrapper=new QueryWrapper<>();
			userQueryWrapper.eq("id_", userId);
			managerUser=managerUserService.getOne(userQueryWrapper);
			Ministries ministries=ministriesService.getMinistriesByUserId(managerUser.getId());
			modelAndView.addObject("ministriesId", ministries.getId());
		}else {
			modelAndView.addObject("ministriesId", 0);
		}
        return modelAndView.addObject("user", managerUser);
	}
	
	/**修改个人信息
	 *2019年8月28日 上午11:42:43
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param managerUser
	 * @return
	 */
	@PutMapping()
	public JSONObject updateUserInfo(@ModelAttribute("managerUser")ManagerUser managerUser,@RequestParam("ministriesId")Integer ministriesId) {
		JSONObject rtnObj=new JSONObject();
		try {
			ManagerUser user= (ManagerUser) SecurityUtils.getSubject().getPrincipal();
			managerUser.setId(user.getId());
			managerUserService.updateById(managerUser);
			dealWithUserMinistries(ministriesId, managerUser);
			user.setName(managerUser.getName());
			user.setPhone(managerUser.getPhone());
			user.setIdentity(managerUser.getIdentity());
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			log.error(e.getMessage());
		}
		return rtnObj;
	}
	
	@PostMapping()
	public JSONObject registUser(@ModelAttribute("managerUser")ManagerUser managerUser,@RequestParam("ministriesId")Integer ministriesId) {
		JSONObject rtnObj=new JSONObject();
		try {
			ManagerUser user= (ManagerUser) SecurityUtils.getSubject().getPrincipal();
			managerUser.setCreateId(user.getId());
			managerUser.setPassword(new String(DigestUtils.md5DigestAsHex(managerUser.getPassword().getBytes())));
			managerUserService.save(managerUser);
			dealWithUserMinistries(ministriesId, managerUser);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			log.error(e.getMessage());
		}
		return rtnObj;
	}

	/**处理用户部门关系:先删除,再生产
	 *2019年9月2日 下午5:11:08
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param ministriesId
	 * @param user
	 */
	private void dealWithUserMinistries(Integer ministriesId, ManagerUser user) {
		QueryWrapper<UserMinistries> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_id_", user.getId());
		userMinistriesService.remove(queryWrapper);//先物理删除用户部门关系
		if(ministriesId!=null&&ministriesId!=0) {
			Ministries ministries=ministriesService.getById(ministriesId);
			UserMinistries userMinistries=new UserMinistries()
					.setUserId(user.getId())
					.setUserName(user.getName())
					.setMinistriesName(ministries.getName())
					.setMinistriesId(ministries.getId());
			userMinistriesService.save(userMinistries);
			QueryWrapper<CommonRelation> commonRelationWrapper=new QueryWrapper<>();
			commonRelationWrapper.eq("relation_type_", Constants.RelationType.VICE_MINISTRIES.getValue()).eq("master_id_", ministriesId).eq("slave_id_", user.getId());
			commonRelationService.remove(commonRelationWrapper);
		}
	}
	
	@GetMapping("userList")
	public ModelAndView userList() {
		ModelAndView modelAndView=new ModelAndView(MODUAL+"userList");
		return modelAndView;
	}
	
	@GetMapping("userGet/{id}")
	public ModelAndView userGet(@PathVariable("id")Integer id) {
		ModelAndView modelAndView=new ModelAndView(MODUAL+"userGet");
		ManagerUser managerUser=managerUserService.getById(id);
		Ministries ministries=ministriesService.getMinistriesByUserId(id);
		modelAndView.addObject("user", managerUser);
		modelAndView.addObject("ministries", ministries);
		return modelAndView;
	}
	
	/**用户列表数据
	 *2019年9月3日 上午11:57:05
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param layuiPage
	 * @param managerUser
	 * @return
	 */
	@PostMapping("list")
	public LayuiPageResult<ManagerUser> list(@ModelAttribute("page") LayuiPage layuiPage, @ModelAttribute("user") ManagerUser managerUser,@RequestParam(name="ministriesId",required=false)Integer ministriesId) {
		ManagerUser user=(ManagerUser) SecurityUtils.getSubject().getPrincipal();
		Ministries mainMinistry=ministriesService.getMinistriesByUserId(user.getId());
		
		QueryWrapper<ManagerUser> queryWrapper=new QueryWrapper<>();
		queryWrapper.likeLeft(StringUtils.isNotBlank(managerUser.getName()),"name_", managerUser.getName())
		.likeLeft(StringUtils.isNotBlank(managerUser.getAccount()),"account_", managerUser.getAccount())
		.inSql(ministriesId!=null,"id_", "select user_id_ from user_ministries_ where ministries_id_ ="+ministriesId)
		.inSql(!mainMinistry.getName().equals("管理中心"),"id_", "select user_id_ from user_ministries_ where ministries_id_ in (SELECT id_ FROM ministries_ WHERE path_ LIKE '"+mainMinistry.getPath()+"%')")//如果不是管理中心的话只能看自己以及子部门的员工
		.eq("sys_status_", Constants.SystemConstants.NORMAL.getValue());
		
		Page<ManagerUser> page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
		LayuiPageResult<ManagerUser> result = new LayuiPageResult<>(managerUserService.page(page, queryWrapper));
		return result;
	}
	
	/**逻辑删除用户
	 *2019年9月3日 下午4:21:34
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	public JSONObject removeUser(@PathVariable(name="id")Integer id) {
		JSONObject rtnObj=new JSONObject();
		try {
			QueryWrapper<ManagerUser> userQueryWrapper=new QueryWrapper<>();
			userQueryWrapper.eq("id_", id).eq("sys_status_", Constants.SystemConstants.NORMAL.getValue());
			ManagerUser managerUser=managerUserService.getOne(userQueryWrapper);
			managerUser.setSysStatus(Constants.SystemConstants.FORBIDDEN.getValue());
			managerUserService.updateById(managerUser);
			QueryWrapper<UserMinistries> userMinistriesQueryWrapper=new QueryWrapper<>();
			userMinistriesQueryWrapper.eq("user_id_", id);
			userMinistriesService.remove(userMinistriesQueryWrapper);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
			rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), e.getCause());
		}
		return rtnObj;
	}
}
