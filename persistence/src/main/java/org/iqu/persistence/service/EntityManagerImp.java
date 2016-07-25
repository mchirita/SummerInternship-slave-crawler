package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.Event;
import org.iqu.persistence.entities.News;

public class EntityManagerImp implements EntityManager {

	private DAOFactory factory = new DAOFactoryImp();
	private List<News> databaseNews;
	private List<Event> databaseEvents;
	private NewsDAO newsDataAccess = factory.getNewsDAO();
	private EventDAO eventsDataAccess = factory.getEventDAO();

	public void retrieveData(Par as) {

	}

	@Override
	public void retrieveNews(List<News> crawlerNews) {
		databaseNews = newsDataAccess.findAllBySource(source);

		for (News news : crawlerNews) {
			if (databaseNews.contains(news)) {
				newsDataAccess.update(news);
			} else {
				newsDataAccess.create(news);
			}
		}
	}

	@Override
	public void retrieveEvents(List<Event> crawlerEvents) {
		databaseEvents = eventsDataAccess.findAll();

		for (Event event : crawlerEvents) {
			if (databaseEvents.contains(event)) {
				eventsDataAccess.update(event);
			} else {
				eventsDataAccess.create(event);
			}
		}

	}

}
