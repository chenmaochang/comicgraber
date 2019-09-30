package org.cmc.comicgrab.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FileUtils {
	public static String saveFile(String src, String targetPath){
		try {
			URL imgUrl = new URL(URLEncoder.encode(src, "UTF-8").replace("%2F", "/").replace("%3A", ":").replace("%3F", "?").replace("%3D", "=").replace("%2C", ","));
			String suffix = src.contains(".jpg")?".jpg":(src.contains(".png")?".png":((src.contains(".jpeg")?".jpeg":(src.contains(".bmp")?".bmp":""))));
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
			return targetPath + suffix;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }
}
