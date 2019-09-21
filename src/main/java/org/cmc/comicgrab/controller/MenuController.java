package org.cmc.comicgrab.controller;

import java.util.List;

import javax.annotation.Resource;

import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.entity.Menu;
import org.cmc.comicgrab.service.IMenuService;
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

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 菜单-三共平台 前端控制器
 * </p>
 *
 * @author cmc
 * @since 2019-08-23
 */
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {
	public static final String MODUAL = "menu/";
	@Resource
	private IMenuService menuService;

	@PostMapping("getMyMenus")
	public List<Menu> getMyMenus() {
		Integer parentId = 0;
		return menuService.getMenuTree(parentId);
	}

	/**
	 * 获取菜单列表 2019年8月26日 下午4:36:25 作者 陈茂昌 email:chenmc@createw.com
	 * 
	 * @return
	 */
	@GetMapping("menuList")
	public ModelAndView menuList() {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "menuList");
		QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("sys_status_", Constants.SystemConstants.NORMAL.getValue());
		List<Menu> menus = menuService.list(queryWrapper);
		modelAndView.addObject("menus", menus);
		return modelAndView;
	}

	/**
	 * 编辑或者新增菜单 2019年8月26日 下午4:45:16 作者 陈茂昌 email:chenmc@createw.com
	 * 
	 * @param parentId
	 * @param id
	 * @return
	 */
	@GetMapping("menuEdit")
	public ModelAndView editMenu(@RequestParam(value = "parentId", required = false) Integer parentId, @RequestParam(value = "id", required = false) Integer id) {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "menuEdit");
		Menu menu;
		if (id != null) {
			menu = menuService.getById(id);
		} else {
			menu = new Menu();
			menu.setParentId(parentId);
		}
		modelAndView.addObject("menu", menu);
		return modelAndView;
	}

	/**保存或者更新菜单
	 *2019年8月27日 下午2:03:53
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param menu
	 * @return
	 */
	@PostMapping
	public JSONObject menuSave(@ModelAttribute("menu") Menu menu) {
		JSONObject rtnObj = new JSONObject();
		try {
			menuService.saveOrUpdate(menu);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		} catch (Exception e) {
			log.error("菜单保存失败" + menu);
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
		}
		return rtnObj;
	}

	/**逻辑删除菜单以及级联子菜单
	 *2019年8月27日 下午2:04:08
	 * 作者 陈茂昌
	 * email:chenmc@createw.com
	 * @param id
	 * @return
	 */
	@DeleteMapping("{id}")
	public JSONObject menuDelete(@PathVariable(name = "id") Integer id) {
		JSONObject rtnObj = new JSONObject();
		try {
			List<Menu> menus = menuService.getMenuListFromTree(id);
			menus.add(menuService.getById(id));
			menus.forEach(menu->menu.setSysStatus(Constants.SystemConstants.FORBIDDEN.getValue()));
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), menuService.updateBatchById(menus));
		} catch (Exception e) {
			log.error("批量删除失败:"+e.getMessage());
			rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
		}
		return rtnObj;
	}
}
