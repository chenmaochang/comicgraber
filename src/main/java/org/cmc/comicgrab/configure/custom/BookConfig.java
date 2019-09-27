package org.cmc.comicgrab.configure.custom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class BookConfig {
	@Value("${book.config.webCollector.storePath}")
	private String webControllerStorePath;
	@Value("${book.config.bookStorePath}")
	private String bookStorePath;
	
}
