package org.cmc.comicgrab.utils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;  
  
/** 
 * 用WebCollector爬虫爬取整站图片 
 */  
public class PicCrawler extends BreadthCrawler{  
  
    public PicCrawler(String crawlPath, boolean autoParse) {
    	super(crawlPath, autoParse);
        // add 5 start pages and set their type to "list"
        //"list" is not a reserved word, you can use other string instead
        this.addSeedAndReturn("https://manhua.zsh8.com/pg/");
        this.addRegex("+https://manhua.zsh8.com/pg/*");
        setThreads(10);
        getConf().setTopN(100);
	}

	/*用一个整数，不断自增，来作为下载的图片的文件名*/  
    AtomicInteger id=new AtomicInteger(0);  
  
 
  
    public static void main(String[] args) throws Exception {  
        PicCrawler crawler=new PicCrawler("d:/test12",true);  
        crawler.start(3);
         
    }

	@Override
	public void visit(Page page, CrawlDatums next) {
		/*不处理非jpg的网页/文件*/  
		System.out.println("弄他");
		System.out.println(page.url());
        if(!Pattern.matches(".*jpg$",page.url())){  
            return;  
        }  
        /*将图片内容保存到文件，page.getContent()获取的是文件的byte数组*/  
        try {  
        	System.out.println(111);
            FileUtils.write("d:/download"+id.incrementAndGet()+".jpg",page.content());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}  
}  