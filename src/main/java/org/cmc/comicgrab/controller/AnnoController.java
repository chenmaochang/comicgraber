package org.cmc.comicgrab.controller;

import org.cmc.comicgrab.common.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/anno")
public class AnnoController {
	public static final String MODUAL = "anno/";
	@GetMapping("guest")
	public ModelAndView guest(){
		ModelAndView modelAndView=new ModelAndView(MODUAL+"guest");
		return modelAndView;
	}
	@PostMapping()
	public JSONObject search(@RequestParam(name="value")String value){
		JSONObject rtnObj=new JSONObject();
		rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), value);
		return rtnObj;
	}
	
}
