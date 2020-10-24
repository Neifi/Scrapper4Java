package es.neifi.model.news;

import es.neifi.model.Author;

public class News {
	
	private String title;
	private String title2;
	private String subtitle;
	private String category;
	private String content;
	private String date;
	private String lastUpdate;
	private String url;
	private Author author;

	public News() {
		
	}

	public News(String title, String title2, String subtitle, String category, String content, String date,
			String lastUpdate, Author author) {
		super();
		this.title = title;
		this.title2 = title2;
		this.subtitle = subtitle;
		this.category = category;
		this.content = content;
		this.date = date;
		this.lastUpdate = lastUpdate;
		this.author = author;
	}

	public News(String title, String title2, String subtitle, String category, String content, String date,
			String lastUpdate, String url, Author author) {
		super();
		this.title = title;
		this.title2 = title2;
		this.subtitle = subtitle;
		this.category = category;
		this.content = content;
		this.date = date;
		this.lastUpdate = lastUpdate;
		this.url = url;
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	
	
}
