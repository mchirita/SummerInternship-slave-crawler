package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.Event;
import org.iqu.persistence.entities.News;

public interface EntityManager {

	public void retrieveNews(List<News> news);

	public void retrieveEvents(List<Event> events);
}
