package es.neifi.webScrapping;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import es.neifi.fileService.FileService;
import es.neifi.model.news.News;
import es.neifi.page.Huffingtonpost;

public class AppTest {
	private final String FILES_PATH = "resources/";
	
	@Test
	public void isTxtFileCreated() {
		File file = createFile();
		assertTrue(file.exists());
	}
	
	@Test
	public void statusConnectionReturn200IfIsValid() {
		Huffingtonpost huffingtonpost_scrap = new Huffingtonpost();
		String url = "https://www.huffingtonpost.es/";

		assertEquals(200, huffingtonpost_scrap.getStatusConnectionCode(url));

	}
	
	@Test
	public void statusConnectionReturnNotFound() {
		Huffingtonpost huffingtonpost_scrap = new Huffingtonpost();
		String url = "https://www.huffingtonpost.es/test";

		assertEquals(404, huffingtonpost_scrap.getStatusConnectionCode(url));

	}

	private File createFile() {
		Huffingtonpost huffingtonpost_scrap = new Huffingtonpost();
		ArrayList<String> urls = huffingtonpost_scrap.getPageUrl("politica", 1);

		String url = urls.get(0);

		FileService fileService = new FileService();

		News news = huffingtonpost_scrap.createNewsFromUrlData(url);
		
		
		
		String filePath = FILES_PATH + fileService.normalizeName(news) + ".txt";
		File file = new File(filePath);
		return file;
	}

}
