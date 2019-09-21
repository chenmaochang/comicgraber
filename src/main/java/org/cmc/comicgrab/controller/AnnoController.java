package org.cmc.comicgrab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/anno")
public class AnnoController {
	public static final String MODUAL = "anno/";
	@GetMapping("guest")
	public ModelAndView guest(){
		ModelAndView modelAndView=new ModelAndView(MODUAL+"guest");
		return modelAndView;
	}
	
}
