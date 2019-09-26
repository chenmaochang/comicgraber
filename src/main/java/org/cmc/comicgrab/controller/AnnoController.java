package org.cmc.comicgrab.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.utils.FreeMarkerUtils;
import org.cmc.comicgrab.utils.epubUtils;
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
	@GetMapping("test")
	public JSONObject test() throws Exception{
		/*List<String> pics=new ArrayList<>();
		pics.add("d:/火星丧尸/第001话/2.jpg");
		epubUtils.makeBook("一本漫画", "某人", pics);*/
		Map<String, Object> map=new HashMap<>();
		map.put("singlePage", "123123.jpg");
		FreeMarkerUtils.createHtml("nomalPage.ftl", "d:/asdasd.html", map);
		return null;
	}
	
}
