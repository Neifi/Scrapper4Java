package es.neifi.model.news.behaviour;

import java.io.IOException;

import es.neifi.fileService.FileService;
import es.neifi.listeners.EventListener;
import es.neifi.model.news.News;

public class OnCreateNews implements EventListener{

	public OnCreateNews() {
		
	}
	@Override
	public void update(String eventType, News news) {
		FileService fileService = new FileService();
		try {
			System.out.println("creating txt...");
			fileService.createTxtOfNews(news);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
