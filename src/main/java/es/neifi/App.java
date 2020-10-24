package es.neifi;

import java.io.IOException;
import java.util.ArrayList;

import es.neifi.fileService.FileService;
import es.neifi.model.news.News;
import es.neifi.model.news.NewsOperation;
import es.neifi.model.news.behaviour.OnCreateNews;
import es.neifi.webScrapping.Scrap;
import es.neifi.webScrapping.Huffingtonpost;

public class App {
	public static void main(String[] args) throws IOException {
	
		Huffingtonpost huffingtonpost_scrap = new Huffingtonpost("politica", 1);
		
	}
}
