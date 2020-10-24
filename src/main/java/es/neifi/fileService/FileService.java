package es.neifi.fileService;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.Normalizer;

import es.neifi.model.news.News;

public class FileService {

	public void createTxtOfNews(News news) throws IOException {

		String newName = Normalizer.normalize(news.getTitle(), Normalizer.Form.NFD);
		newName = newName.replaceAll("[\\p{InCombiningDiacriticalMarks}|:|\"|?|¿|/]", "").replace(" ", "_")
				.toLowerCase();

		PrintWriter printWriter = new PrintWriter("resources/" + newName + ".txt");
		printWriter.println(news.getDate());
		printWriter.println(news.getTitle());
		printWriter.println(news.getSubtitle());
		printWriter.println(news.getContent());
		printWriter.println(news.getUrl());
		printWriter.close();

	}

}
