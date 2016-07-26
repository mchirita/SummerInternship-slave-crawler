package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.Event;
import org.iqu.persistence.entities.NewsArticle;
import org.iqu.persistence.entities.ParsedData;
import org.iqu.persistence.entities.Source;

public class EntityManagerImp implements EntityManager {

	private DAOFactory factory = new DAOFactoryImp();
	private List<NewsArticle> databaseNews;
	private List<Event> databaseEvents;
	private NewsDAO newsDataAccess = factory.getNewsDAO();
	private EventDAO eventsDataAccess = factory.getEventDAO();

	@Override
	public void retrieveData(ParsedData parsedData) {
		Source source = parsedData.getSource();
		if (parsedData.getNews() == null) {
			eventsDataAccess.addSource(source);
			retrieveEvents(parsedData.getEvents(), source);
		} else {
			newsDataAccess.addSource(source);
			retrieveNews(parsedData.getNews(), source);
		}
	}

	private void retrieveNews(List<NewsArticle> crawlerNews, Source source) {
		databaseNews = newsDataAccess.findAllBySource(source);

		for (NewsArticle newsArticle : crawlerNews) {
			if (databaseNews.contains(newsArticle)) {
				if (newsArticle.getDate() > databaseNews.get(databaseNews.indexOf(newsArticle)).getDate()) {
					newsDataAccess.update(newsArticle);
				}
			} else {
				newsDataAccess.create(newsArticle);
			}
		}
	}

	private void retrieveEvents(List<Event> crawlerEvents, Source source) {
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
