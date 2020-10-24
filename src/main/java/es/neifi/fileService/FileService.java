package es.neifi.fileService;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.Normalizer;

import org.apache.commons.io.output.WriterOutputStream;

import es.neifi.model.news.News;

public class FileService {
	private String fileName;
	
	public void createTxtOfNews(News news) throws IOException {

		this.fileName = normalizeName(news);

		PrintWriter printWriter = new PrintWriter("resources/" + fileName + ".txt");
		printWriter.println(news.getDate());
		printWriter.println(news.getTitle());
		printWriter.println(news.getSubtitle());
		printWriter.println(news.getContent());
		printWriter.println(news.getUrl());
		printWriter.close();
		
		

	}

	public String normalizeName(News news) {
		String newName = Normalizer.normalize(news.getTitle(), Normalizer.Form.NFD);
		newName = newName.replaceAll("[\\p{InCombiningDiacriticalMarks}|:|\"|?|¿|/]", "").replace(" ", "_")
				.toLowerCase();
		return newName;
	}
	
	public String getFileName() {
		return fileName;
	}

}
