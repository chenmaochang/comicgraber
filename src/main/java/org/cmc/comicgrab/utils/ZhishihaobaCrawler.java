package org.cmc.comicgrab.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cmc.comicgrab.entity.Book;
import org.cmc.comicgrab.entity.Episode;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

/**芝士豪八Crawler
 * @author admin
 *
 */
public class ZhishihaobaCrawler extends BreadthCrawler {
	private Book book;
	private String bookStorePath;
	private List<Episode> bookEpisodes = new ArrayList<>();

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}


	public String getBookStorePath() {
		return bookStorePath;
	}

	public void setBookStorePath(String bookStorePath) {
		this.bookStorePath = bookStorePath;
	}

	public List<Episode> getBookEpisodes() {
		return bookEpisodes;
	}

	public void setBookEpisodes(List<Episode> bookEpisodes) {
		this.bookEpisodes = bookEpisodes;
	}

	public ZhishihaobaCrawler(String crawlPath, boolean autoParse, String startPageUrl, String dataStorePath) {
		super(crawlPath, autoParse);
		this.addSeedAndReturn(startPageUrl).type("list");
		this.bookStorePath = dataStorePath;
		setThreads(3);
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		if (page.matchType("list")) {// 每本
			Elements episodes = page.select(".blog-shortcode-post-title");
			String title = page.selectText(".fusion-post-title");
			String coverPictureUrl = (page.select(".fusion-text", 0)).selectFirst("div").selectFirst("div").selectFirst("img").absUrl("src");
			String pathName = bookStorePath + "/" + title + "/";
			File comicFolder = new File(pathName);
			if (!comicFolder.exists()) {
				comicFolder.mkdir();
			}
			String coverSrc = FileUtils.saveFile(coverPictureUrl, pathName + title);
			String coverSuffix = coverSrc.substring(coverSrc.lastIndexOf("."), coverSrc.length());
			Book book = new Book();
			book.setAuthor(page.selectText("td", 1)).setBookName(title).setCoverPicture(title + coverSuffix).setCoverUrl(coverPictureUrl).setCoverSrc(coverSrc).setSourceWebsite(page.url());
			this.setBook(book);
			episodes.forEach(episode -> {
				Element entrance = episode.selectFirst("a");
				String chapter = entrance.text();
				File chapterFoler = new File(pathName + chapter);
				if (!chapterFoler.exists()) {
					chapterFoler.mkdir();
				}
				Episode singleEpisode = new Episode();
				singleEpisode.setBookName(title).setIndexName(chapter);
				this.bookEpisodes.add(singleEpisode);
				CrawlDatums crawlDatums = new CrawlDatums();
				crawlDatums.addAndReturn(entrance.absUrl("href"));
				crawlDatums.type("comic");
				crawlDatums.meta("title", title).meta("chapter", chapter).meta("parentPath", pathName + chapter + "/");
				next.add(crawlDatums);
			});
		} else if (page.matchType("comic")) {// 每集
			Elements elements = page.select(".gallery-item");
			for (int i = 0; i < elements.size(); i++) {
				Element element = elements.get(i);
				String pageUrl = element.selectFirst("a").selectFirst("img").absUrl("src");
				String pageFileName=page.meta("parentPath") + i;
				String pageSrc = FileUtils.saveFile(pageUrl, pageFileName);
				Map<String, Object> map=new HashMap<>(); 
				map.put("singlePage",pageSrc);
				FreeMarkerUtils.createHtml("nomalPage.ftl",pageFileName+".html", map);
				org.cmc.comicgrab.entity.Page bookPage = new org.cmc.comicgrab.entity.Page();
				bookPage.setPageIndex(i).setPageContent(pageFileName+".html").setPageSrc(pageSrc).setPageUrl(pageUrl).setEpisode(page.meta("chapter"));
				Episode myEpisode = this.bookEpisodes.stream().filter(bookEpisode -> bookEpisode.getIndexName().equals(page.meta("chapter"))).findFirst().get();
				myEpisode.getPages().add(bookPage);
			}
		}
	}
	
}
