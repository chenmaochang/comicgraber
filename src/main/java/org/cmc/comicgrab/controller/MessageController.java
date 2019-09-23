package org.cmc.comicgrab.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.common.LayuiPage;
import org.cmc.comicgrab.common.LayuiPageResult;
import org.cmc.comicgrab.entity.ManagerUser;
import org.cmc.comicgrab.entity.Message;
import org.cmc.comicgrab.service.IManagerUserService;
import org.cmc.comicgrab.service.IMessageService;
import org.cmc.comicgrab.service.IUserMinistriesService;
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
 * 消息表 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {
	public static final String MODUAL = "message/";
	@Resource
	private IMessageService messageService;
	@Resource
	private IManagerUserService managerUserService;
	@Resource
	private IUserMinistriesService userMinistriesService;

	@GetMapping("messageList")
	public ModelAndView messageList() {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "messageList");
		return modelAndView;
	}

	@GetMapping("messageEdit/{id}")
	public ModelAndView messageEdit(@PathVariable(name = "id", required = false) Integer id) {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "messageEdit");
		modelAndView.addObject("message", (id == null || id == 0) ? new Message() : messageService.getById(id));
		modelAndView.addObject("users", userMinistriesService.list());
		return modelAndView;
	}

	@PostMapping("list")
	public LayuiPageResult<Message> list(@ModelAttribute("page") LayuiPage layuiPage, @ModelAttribute("message") Message message) {
		ManagerUser user=(ManagerUser) SecurityUtils.getSubject().getPrincipal();
		QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
		queryWrapper.likeLeft(StringUtils.isNotBlank(message.getTitle()),"title_", message.getTitle())
		.eq("sys_status_", Constants.SystemConstants.NORMAL.getValue())
		.eq("create_id_", user.getId())
		.or()
		.eq("receiver_id_", user.getId())
		.orderByDesc("create_time_");
		Page<Message> page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
		return new LayuiPageResult<>(messageService.page(page, queryWrapper));
	}

	@PostMapping()
	public JSONObject saveMessage(@ModelAttribute("message") Message message) {
		JSONObject rtnObj = new JSONObject();
		ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		try {
			ManagerUser receiver = managerUserService.getById(message.getReceiverId());
			message.setCreateId(user.getId()).setReceiverName(receiver.getName()).setCreateName(user.getName());
			messageService.saveOrUpdate(message);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			log.error("消息保存失败" + message);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
		}
		return rtnObj;
	}

	@GetMapping("messageGet/{id}")
	public ModelAndView messageGet(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "messageGet");
		Message message = messageService.getById(id);
		ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		if (user.getId().equals(message.getReceiverId())) {// 如果不是接收者的话,不记录已读
			message.setReadStatus(Constants.SystemConstants.NORMAL.getValue());
			messageService.updateById(message);
		}
		modelAndView.addObject("message", message);
		return modelAndView;
	}

	@GetMapping("nextUnRead")
	public JSONObject nextUnRead() {
		JSONObject rtnObj=new JSONObject();
		ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("receiver_id_", user.getId())
		.eq("sys_status_", Constants.SystemConstants.NORMAL.getValue())
		.eq("read_status_", Constants.SystemConstants.FORBIDDEN.getValue());
		List<Message> list = messageService.list(queryWrapper);
		Integer size=list.size();
		rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), size>0);
		rtnObj.put("id", size>0?list.get(0).getId():0);
		rtnObj.put("size", size);
		rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), size>0?"跳转成功":"没有下一条未读消息");
		return rtnObj;
	}

}
