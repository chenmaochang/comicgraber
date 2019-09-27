package org.cmc.comicgrab.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.cmc.comicgrab.entity.Book;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import lombok.Data;

/**芝士豪八Crawler
 * @author admin
 *
 */
@Data
public class ZhishihaobaCrawler extends BreadthCrawler {
	private Book book;
	private List<org.cmc.comicgrab.entity.Page> pages;
	private String bookStorePath;
	
	public ZhishihaobaCrawler(String crawlPath, boolean autoParse, String startPageUrl,String dataStorePath) {
		super(crawlPath, autoParse);
		this.addSeedAndReturn(startPageUrl).type("list");
		this.bookStorePath=dataStorePath;
		setThreads(5);
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		if (page.matchType("list")) {//每本
			Elements episodes = page.select(".blog-shortcode-post-title");
			String title = page.selectText(".fusion-post-title");
			String coverPictureUrl = page.select(".alignnone", 0).absUrl("src");
			String pathName = bookStorePath+"/" + title + "/";
			File comicFolder = new File(pathName);
			if (!comicFolder.exists()) {
				comicFolder.mkdir();
			}
			String coverSrc = FileUtils.saveFile(coverPictureUrl, pathName + title);
			String coverSuffix = coverSrc.substring(coverSrc.lastIndexOf("."), coverSrc.length());
			Book book=new Book();
			book.setAuthor(page.selectText("td", 1)).setBookName(title).setCoverPicture(title + coverSuffix).setCoverUrl(coverPictureUrl).setCoverSrc(coverSrc).setSourceWebsite(page.url());
			this.setBook(book);
			episodes.forEach(episode -> {
				Element entrance = episode.selectFirst("a");
				String chapter = entrance.text();
				File chapterFoler = new File(pathName + chapter);
				if (!chapterFoler.exists()) {
					chapterFoler.mkdir();
				}
				CrawlDatums crawlDatums = new CrawlDatums();
				crawlDatums.addAndReturn(entrance.absUrl("href"));
				crawlDatums.type("comic");
				crawlDatums.meta("title", title).meta("chapter", chapter).meta("parentPath", pathName + chapter + "/");
				next.add(crawlDatums);
			});
		} else if (page.matchType("comic")) {//每集
			Elements elements = page.select(".gallery-item");
			for (int i = 0; i < elements.size(); i++) {
				Element element = elements.get(i);
				String pageUrl = element.selectFirst("a").absUrl("href");
				String pageSrc = FileUtils.saveFile(pageUrl, page.meta("parentPath") + i);
				org.cmc.comicgrab.entity.Page bookPage = new org.cmc.comicgrab.entity.Page();
				bookPage.setPageIndex(i).setPageSrc(pageSrc).setPageUrl(pageUrl).setEpisode(page.meta("chapter"));
				List<org.cmc.comicgrab.entity.Page> list=this.getPages();
				if(list==null) {
					list=new ArrayList<>();
				}
				list.add(bookPage);
				this.setPages(list);
			}	
		}
	}

}
