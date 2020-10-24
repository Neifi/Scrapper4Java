package es.neifi.page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.sun.star.uno.RuntimeException;

import es.neifi.fileService.FileService;
import es.neifi.listeners.EventManager;
import es.neifi.model.news.News;
import es.neifi.model.news.NewsBuildier;
import es.neifi.model.news.NewsOperation;
import es.neifi.model.news.behaviour.OnCreateNews;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Connection.Response;

public class Huffingtonpost {

	private final int TIME_OUT = 30000;
	private final String MOZILLA_USER_AGENT = "Mozilla/5.0";

	private String categoria;
	private int pages;
	private ArrayList<String> urls;
	private ArrayList<News> news;

	public EventManager events;

	public Huffingtonpost(String categoria, int pages) {
		this.events = new EventManager(NewsOperation.CREATE_NEW);
		events.subscribe(NewsOperation.CREATE_NEW, new OnCreateNews());

		this.categoria = categoria;
		this.pages = pages;
		this.urls = getPageUrl(categoria, pages);

	}

	public Huffingtonpost() {
		this.events = new EventManager(NewsOperation.CREATE_NEW);
		events.subscribe(NewsOperation.CREATE_NEW, new OnCreateNews());
	}

	public int getStatusConnectionCode(String url) {

		Response response = null;
		try {
			response = Jsoup.connect(url).userAgent(MOZILLA_USER_AGENT).timeout(TIME_OUT).ignoreHttpErrors(true)
					.execute();
			return response.statusCode();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;

	}

	public ArrayList<String> getPageUrl(String categoria, int pages) {
		String urlCategoriaPolitica = "https://www.huffingtonpost.es/" + categoria;
		String urlGeneral = "https://www.huffingtonpost.es";
		String index = "";
		String url;

		ArrayList<String> urls = new ArrayList<String>();

		for (int i = 0; i < pages; i++) {
			if (getStatusConnectionCode(urlCategoriaPolitica + index) == 200) {
				Document doc = getHtmlDocument(urlCategoriaPolitica + index);

				if (!isPageValid(doc, pages)) {
					throw new RuntimeException("paginación incorrecta");
				}

				Elements pageContent = doc.select("h3.card__headline");

				for (Element element : pageContent) {
					url = urlGeneral + element.getElementsByAttribute("href").attr("href");
					
					urls.add(url);
					int aux = i + 1;
					index = "/" + aux + "/";
				}

			}
		}
		return urls;
	}

	private boolean isPageValid(Document doc, int pages) {
		int lastPaginationNumber = Integer.parseInt(doc.getElementsByClass("pagination__link").last().text());
		if (pages > lastPaginationNumber) {
			return false;
		}
		return true;
	}

	private Document getHtmlDocument(String url) {
		Document document = null;
		try {
			document = Jsoup.connect(url).userAgent(MOZILLA_USER_AGENT).timeout(TIME_OUT).get();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

	public News createNewsFromUrlData(String url) {
		String title;
		String subtitle;
		String category;
		String content = "";
		String date;
		String newsLink;

		Document doc = getHtmlDocument(url);
		newsLink = url;
		date = doc.getElementsByClass("timestamp__date").text();
		title = doc.getElementsByClass("headline__title").text();
		subtitle = doc.getElementsByClass("headline__subtitle").text();
		category = doc.getElementsByClass("entry-eyebrow").text();

		Elements contentText = doc.select("div.content-list-component");

		for (Element elementTxt : contentText) {
			content += "\n" + elementTxt.getElementsByTag("p").text();
		}

		content = "";
		News newsItem = createNewsItem(title, subtitle, category, content, date, newsLink);
		return createNewsTxtFile(newsItem);


	}

	private News createNewsTxtFile(News newsItem) {
		events.notify(NewsOperation.CREATE_NEW, newsItem);
		return newsItem;
	}

	private News createNewsItem(String title, String subtitle, String category, String content, String date,
			String newsLink) {
		News newsItem = new NewsBuildier().date(date).category(category).title(title).subtitle(subtitle)
				.content(content).url(newsLink).build();
		return newsItem;
	}

}
