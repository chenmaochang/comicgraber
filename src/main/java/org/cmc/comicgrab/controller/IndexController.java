package org.cmc.comicgrab.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.cmc.comicgrab.entity.ManagerUser;
import org.cmc.comicgrab.service.IMenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
	@Resource
	private IMenuService menuService;

	@RequestMapping("/")
	public ModelAndView index() {
		ManagerUser user = (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		if (user == null) {
			return new ModelAndView("indexLogin");
		}
		return new ModelAndView("home").addObject("menus", menuService.getMenuTree(0));
	}
}
