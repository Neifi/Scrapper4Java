package es.neifi.model.news;

import es.neifi.listeners.EventManager;
import es.neifi.model.Author;

public class NewsBuildier {

	private String title;
	private String title2;
	private String subtitle;
	private String category;
	private String content;
	private String date;
	private String lastUpdate;
	private String url;
	private Author author;

	public NewsBuildier() {

	}

	public NewsBuildier title(String title) {
		this.title = title;
		return this;
	}

	public NewsBuildier title2(String title2) {
		this.title2 = title2;
		return this;
	}

	public NewsBuildier subtitle(String subtitle) {
		this.subtitle = subtitle;
		return this;
	}

	public NewsBuildier category(String category) {
		this.category = category;
		return this;
	}

	public NewsBuildier content(String content) {
		this.content = content;
		return this;
	}

	public NewsBuildier date(String date) {
		this.date = date;
		return this;
	}

	public NewsBuildier lastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
		return this;
	}

	public NewsBuildier url(String url) {
		this.url = url;
		return this;
	}

	public NewsBuildier author(Author author) {
		this.author = author;
		return this;
	}

	public News build() {
		return new News(title, title2, subtitle, category, content, date, lastUpdate, url, author);

	}

}
