package es.neifi.listeners;

import es.neifi.model.news.News;

public interface EventListener {

	public void update(String eventType, News file);

}
