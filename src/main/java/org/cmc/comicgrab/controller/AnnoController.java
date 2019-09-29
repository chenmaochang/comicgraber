package org.cmc.comicgrab.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import org.cmc.comicgrab.common.Constants;
import org.cmc.comicgrab.configure.custom.BookConfig;
import org.cmc.comicgrab.entity.Book;
import org.cmc.comicgrab.entity.Episode;
import org.cmc.comicgrab.entity.Page;
import org.cmc.comicgrab.service.IBookService;
import org.cmc.comicgrab.service.IPageService;
import org.cmc.comicgrab.utils.FileUtils;
import org.cmc.comicgrab.utils.ZhishihaobaCrawler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;

import ch.qos.logback.core.util.FileUtil;

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
	public ModelAndView guest() {
		ModelAndView modelAndView = new ModelAndView(MODUAL + "guest");
		return modelAndView;
	}

	@PostMapping()
	public JSONObject search(@RequestParam(name = "value") String value) {
		JSONObject rtnObj = new JSONObject();
		rtnObj.put(Constants.SystemConstants.SUCCESS.getValue(), true);
		rtnObj.put(Constants.SystemConstants.MESSAGE.getValue(), "请求已经提交,请等候执行");
		
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> test(value));
		//future.whenCompleteAsync(()->{});
		return rtnObj;
	}

	/**
	 * "https://manhua.zsh8.com/pg/hxssfm/82345.html" 2019年9月27日 下午5:58:40 作者
	 * 陈茂昌 email:chenmc@createw.com
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public void test(String url) {
		// url="https://manhua.zsh8.com/pg/zmdhgffm/94591.html";
		/*
		 * List<String> pics=new ArrayList<>(); pics.add("d:/火星丧尸/第001话/2.jpg");
		 * epubUtils.makeBook("一本漫画", "某人", pics);
		 */
		/*
		 * Map<String, Object> map=new HashMap<>(); map.put("singlePage",
		 * "123123.jpg"); FreeMarkerUtils.createHtml("nomalPage.ftl",
		 * "d:/asdasd.html", map);
		 */

		ZhishihaobaCrawler zhishihaoba = new ZhishihaobaCrawler(
				bookConfig.getWebControllerStorePath() + "/" + UUID.randomUUID().toString(), false, url,
				bookConfig.getBookStorePath());
		try {
			zhishihaoba.start(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Book book = zhishihaoba.getBook();
		List<Episode> episodes=zhishihaoba.getBookEpisodes();
		System.out.println(book);
		System.out.println(episodes);
		/*//List<Page> pages = zhishihaoba.getPages();
		bookService.save(book);
		pages.forEach(page -> page.setBookId(book.getId()));
		pageService.saveBatch(pages);
		book.setPages(pages);*/
	}
	/*
	 * private void makeBookHTML(Book book){
	 * FileUtil.createMissingParentDirectories(file) }
	 */
}
