package org.cmc.comicgrab.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.edu.hfut.dmic.webcollector.model.Page;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubWriter;

public class epubUtils {
	
	private static InputStream getResource(String path) throws FileNotFoundException {
		File file = new File(path);
		return new FileInputStream(file);
	}

	private static Resource getResource(String path, String href) throws IOException {
		return new Resource(getResource(path), href);
	}

	public static Book makeHeads(String title, String author, String coverUrl) {
		Book book = new Book();
		try {
			Metadata metadata = book.getMetadata();
			metadata.addTitle(title);
			metadata.addAuthor(new Author(author));
			book.setCoverImage(getResource("d:/cover.png", "cover.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	public static void makeBook(org.cmc.comicgrab.entity.Book customBook) throws IOException {
		Book book = new Book();
		Metadata metadata = book.getMetadata();
		metadata.addTitle(customBook.getBookName());
		metadata.addAuthor(new Author(customBook.getAuthor()));
		book.setCoverImage(getResource(customBook.getCoverSrc(), customBook.getCoverPicture()));
		List<org.cmc.comicgrab.entity.Page> pages=customBook.getPages();
		for (int i = 0; i < pages.size(); i++) {
			book.addSection(customBook.getBookName() + "-" + i, getResource("d:/normalPage.html", i + "normalPage.html"));
			book.getResources().add(getResource("d:/火星丧尸/第001话/0.jpg", "page.jpg"));
		}
		EpubWriter epubWriter = new EpubWriter();
		epubWriter.write(book, new FileOutputStream("d:/my.epub"));
	}

	public static void main(String[] args) {

		try {
			// Create new Book
			Book book = new Book();
			Metadata metadata = book.getMetadata();

			// Set the title
			metadata.addTitle("Epublib test book 1");

			// Add an Author
			metadata.addAuthor(new Author("Joe", "Tester"));

			// Set cover image
			book.setCoverImage(getResource("d:/cover.png", "cover.png"));

			// Add Chapter 1
			book.addSection("Introduction", getResource("d:/cover.html", "cover.html"));

			// Add css file
			book.getResources().add(getResource("d:/book1.css", "book1.css"));

			// Add Chapter 2
			TOCReference chapter2 = book.addSection("Second Chapter", getResource("d:/chapter2.html", "chapter2.html"));

			// Add image used by Chapter 2
			book.getResources().add(getResource("d:/flowers.jpg", "flowers.jpg"));

			// Add Chapter2, Section 1
			book.addSection(chapter2, "Chapter 2, section 1", getResource("d:/chapter2_1.html", "chapter2_1.html"));

			// Add Chapter 3
			book.addSection("Conclusion", getResource("d:/chapter3.html", "chapter3.html"));

			// Create EpubWriter
			EpubWriter epubWriter = new EpubWriter();

			// Write the Book as Epub
			epubWriter.write(book, new FileOutputStream("d:/test1_book1.epub"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}