package org.cmc.comicgrab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.cmc.comicgrab.mapper")
public class ComicGrabApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComicGrabApplication.class, args);
	}

}
