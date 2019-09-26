package org.cmc.comicgrab.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FileUtils {
	public static void saveFile(String src, String targetPath) throws IOException {
		URL imgUrl = new URL(URLEncoder.encode(src, "UTF-8").replace("%2F", "/").replace("%3A", ":"));
		String suffix = src.substring(src.lastIndexOf("."), src.length());
		URLConnection connection = imgUrl.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36)");
		// 获取输入流
		InputStream inputStream = connection.getInputStream();
		// 将输入流信息放入缓冲流提升读写速度
		BufferedInputStream bis = new BufferedInputStream(inputStream);
		// 读取字节娄
		byte[] buf = new byte[1024];
		// 生成文件
		FileOutputStream outputStream = new FileOutputStream(targetPath + suffix);
		int size = 0;
		// 边读边写
		while ((size = bis.read(buf)) != -1) {
			outputStream.write(buf, 0, size);
		}
		// 刷新文件流
		outputStream.flush();
		outputStream.close();
	}
}
