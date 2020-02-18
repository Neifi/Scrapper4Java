package es.neifi.webScrapping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.plutext.jaxb.svg11.Title;

import es.neifi.fileService.DocxCreator;
import es.neifi.fileService.FileService;
import es.neifi.model.Author;
import es.neifi.model.News;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.jsoup.Connection.Response;

public class Scrapp {

	private static String url;
	private static ArrayList<String> urls = new ArrayList<String>();
	/**
	 * Petición http
	 */
	public static int getStatusConnectionCode(String url) {

		Response response = null;
		try {
			String userAgent = (String) Jsoup.connect(url).request().header("User-Agent");
			response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(1000000).ignoreHttpErrors(true).execute();
		} catch (IOException e) {
			System.out.println(response.statusMessage());
			e.printStackTrace();
			return response.statusCode();
		}

		return response.statusCode();

	}

	public static Document getHtmlDoc(String url) {
		Document document = null;
		try {
			String userAgent = (String) Jsoup.connect(url).request().header("User-Agent");
			document = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(10000000).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public static void main(String[] args) throws IOException {
		getPageUrl("politica");
		getDataFromUrl(urls);
	}

	/**
	 * Obtiene url de las noticias de la primera pagina de la categoria.
	 * 
	 * @param categoria categoria de la noticia
	 * @author Neifi
	 * 
	 */
	public static void getPageUrl(String categoria) {
		String urlCategoriaPolitica = "https://www.huffingtonpost.es/" + categoria;
		String urlGeneral = "https://www.huffingtonpost.es";

		

		if (getStatusConnectionCode(urlCategoriaPolitica) == 200) {

			Document doc = getHtmlDoc(urlCategoriaPolitica);
			Elements pageContent = doc.select("h3.card__headline");
			getPagination(doc);
			for (Element element : pageContent) {
				url = urlGeneral + element.getElementsByAttribute("href").attr("href");
				urls.add(url);
				System.out.println("Obteniendo datos...");

			}
		}
	}

	/**
	 * Obtiene url de las noticias de todas las paginas de la categoria.
	 * 
	 * @param categoria categoria de la noticia
	 * @author Neifi
	 */
	public static void getAllPageUrl(String categoria) {
		String urlCategoriaPolitica = "https://www.huffingtonpost.es/" + categoria;
		String urlGeneral = "https://www.huffingtonpost.es";
		String index = "";
		int temporalIndex = 2;
		

		for (int i = 0; i < temporalIndex; i++) {
			if (getStatusConnectionCode(urlCategoriaPolitica + index) == 200) {
				Document doc = getHtmlDoc(urlCategoriaPolitica + index);
				temporalIndex = getPagination(doc);
				Elements pageContent = doc.select("h3.card__headline");

				for (Element element : pageContent) {
					url = urlGeneral + element.getElementsByAttribute("href").attr("href");
					urls.add(url);
					System.out.println(url);

				}
				int aux = i + 1;
				index = "/" + aux + "/";

				
				System.out.println("*********************CAMBIO DE PAGINA**************************");
				System.out.println(urlCategoriaPolitica + index);
				System.out.println("***************************************************************");
				System.out.println("Numero de noticias: "+urls.size());
			} else {
				throw new RuntimeException("No se pudo conectar con el servidor.");
			}
		}
	}

	public static int getPagination(Document doc) {
		int lastPaginationNumber = Integer.parseInt(doc.getElementsByClass("pagination__link").last().text());
		return lastPaginationNumber;
	}

	public static ArrayList<News> getDataFromUrl(ArrayList<String> urls) throws IOException {
		String title;
		String title2;
		String subtitle;
		String category;
		String content = "";
		String date;
		String lastUpdate;
		String newsLink;
		Author author;
		ArrayList<News>newsList = new ArrayList<News>();
		News news;
		int counterForLogCurrnt = 1;
		for (String link : urls) {
			Document doc = getHtmlDoc(link);
			newsLink = link;
			date = doc.getElementsByClass("timestamp__date").text();
			title = doc.getElementsByClass("headline__title").text();
			subtitle = doc.getElementsByClass("headline__subtitle").text();
			category = doc.getElementsByClass("entry-eyebrow").text();
			
			Elements contentText = doc.select("div.content-list-component");
			for (Element elementTxt : contentText) {
				content += elementTxt.getElementsByTag("p").text();
				
			}
			
			news = new News();
			news.setDate(date);
			news.setCategory(category);
			news.setTitle(title);
			news.setSubtitle(subtitle);
			news.setContent(content);
			news.setUrl(newsLink);
			newsList.add(news);
			//Logs
			int counterForLogLast = urls.size();
			System.out.println("Creando archivo... "+counterForLogCurrnt +"/"+counterForLogLast);
			counterForLogCurrnt +=1;
			//FileService.createTxt(newsList);
			try {
				DocxCreator.create(news);
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Docx4JException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			content = "";
			
		}
		return newsList;
	}
}
