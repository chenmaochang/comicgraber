package org.cmc.comicgrab.controller;

import java.util.List;

import javax.annotation.Resource;

import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.configure.custom.BookConfig;
import org.cmc.comicgrab.entity.Book;
import org.cmc.comicgrab.entity.Page;
import org.cmc.comicgrab.service.IBookService;
import org.cmc.comicgrab.service.IPageService;
import org.cmc.comicgrab.utils.ZhishihaobaCrawler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;

@RestController
@RequestMapping("/anno")
public class AnnoController {
	public static final String MODUAL = "anno/";
	@Resource
	private IBookService bookService;
	@Resource
	private IPageService pageService;
	@Resource
	private BookConfig bookConfig;
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
		/*Map<String, Object> map=new HashMap<>();
		map.put("singlePage", "123123.jpg");
		FreeMarkerUtils.createHtml("nomalPage.ftl", "d:/asdasd.html", map);*/
		ZhishihaobaCrawler zhishihaoba = new ZhishihaobaCrawler(bookConfig.getWebControllerStorePath(), false, "https://manhua.zsh8.com/pg/hxssfm/82345.html",bookConfig.getBookStorePath());
		zhishihaoba.start(2);
		Book book=zhishihaoba.getBook();
		List<Page> pages=zhishihaoba.getPages();
		bookService.save(book);
		pages.forEach(page->page.setBookId(book.getId()));
		pageService.saveBatch(pages);
		return null;
	}
	
}
