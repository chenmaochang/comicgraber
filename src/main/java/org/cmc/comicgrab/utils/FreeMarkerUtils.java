package org.cmc.comicgrab.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

@Component
public class FreeMarkerUtils {
	@Resource
	private FreeMarkerConfigurer configurer;  
	private static FreeMarkerUtils freeMarkerUtils;
	 @PostConstruct 
	    public void init() {  
		 freeMarkerUtils = this;
		 freeMarkerUtils.configurer=this.configurer;
	    } 
	 
	public static void createHtml(String templateName,String targetFileName,Map<String, Object> map) throws Exception{
		Template template = freeMarkerUtils.configurer.getConfiguration().getTemplate(templateName);
		Writer file = new FileWriter(new File(targetFileName));
		template.process(map, file);
		file.flush();
		file.close();
    }
}
