package org.cmc.comicgrab.utils;

import java.io.File;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

public class Demo extends BreadthCrawler {

	public Demo(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		this.addSeedAndReturn("https://manhua.zsh8.com/pg/hxssfm/82345.html").type("list");
		setThreads(5);
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		if (page.matchType("list")) {
			Elements episodes = page.select(".blog-shortcode-post-title");
			String title = page.selectText(".fusion-post-title");
			String pathName="d:/" + title + "/";
			File comicFolder = new File(pathName);
			if (!comicFolder.exists()) {
				comicFolder.mkdir();
			}
			episodes.forEach(episode -> {
				Element entrance = episode.selectFirst("a");
				String chapter=entrance.text();
				File chapterFoler=new File(pathName+chapter);
				if (!chapterFoler.exists()) {
					chapterFoler.mkdir();
				}
				System.out.println(title+chapter+"/");
				//next.meta("title", title).meta("chapter",chapter).meta("parentPath",pathName+chapter+"/");
				//next.add(entrance.absUrl("href"), "comic");
				CrawlDatums crawlDatums=new CrawlDatums();
				crawlDatums.addAndReturn(entrance.absUrl("href"));
				crawlDatums.type("comic");
				crawlDatums.meta("title", title).meta("chapter",chapter).meta("parentPath",pathName+chapter+"/");
				next.add(crawlDatums);
			});
		} else if (page.matchType("comic")) {
			Elements elements = page.select(".gallery-item");
			for(int i=0;i<elements.size();i++) {
				Element element=elements.get(i);
				try {
					FileUtils.saveFile(element.selectFirst("a").absUrl("href"), page.meta("parentPath")+i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Demo asdfgadfg = new Demo("d:/test", false);
		// asdfgadfg.setResumable(true);
		asdfgadfg.start(2);
	}
}
