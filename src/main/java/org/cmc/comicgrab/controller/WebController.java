package org.cmc.comicgrab.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.entity.ManagerUser;
import org.cmc.comicgrab.service.IMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/web")
@Slf4j
public class WebController {
	@Resource
	private IMenuService menuService;
	@GetMapping("/index")
	public ModelAndView index() {
		ManagerUser user= (ManagerUser) SecurityUtils.getSubject().getPrincipal();
		System.out.println(user);
		return new ModelAndView("indexLogin");
	}
	
	@RequestMapping("home")
	public ModelAndView home() {
		return new ModelAndView("home").addObject("menus", menuService.getMenuTree(0));
	}
	
	@GetMapping("notLogin")
    public ModelAndView notLogin() {
    	ModelAndView modelAndView=new ModelAndView("index");
        return modelAndView;
    }

    @RequestMapping("notRole")
    public JSONObject notRole() {
    	JSONObject rtnObj=new JSONObject();
    	rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), false);
    	rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), "您没有权限！");
        return rtnObj;
    }
    
	@GetMapping("logout")
    public ModelAndView logout() {
    	ModelAndView modelAndView=new ModelAndView();
        try {
        	Subject subject = SecurityUtils.getSubject();
            subject.logout();//注销
            modelAndView.setViewName("index");
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("index");
		}
        return modelAndView;
    }
	
	@PostMapping("login")
    public JSONObject login(String username, String password,@RequestParam(value="rememberMe",required=false)Boolean rememberMe) {
    	JSONObject rtnObj=new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
        // 执行认证登陆
        try {
        	subject.login(token);
        	rtnObj.put("success", true);
		} catch (Exception e) {
			rtnObj.put("success", false);
			rtnObj.put("msg", "登录失败");
		}
        return rtnObj;
    }
	
	
}
