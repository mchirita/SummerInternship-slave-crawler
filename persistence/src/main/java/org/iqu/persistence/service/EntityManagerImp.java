package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.EventDTO;
import org.iqu.persistence.entities.NewsArticleDTO;
import org.iqu.persistence.entities.ParsedDataDTO;
import org.iqu.persistence.entities.SourceDTO;

public class EntityManagerImp implements EntityManager {

  private List<NewsArticleDTO> databaseNews;
  private List<EventDTO> databaseEvents;
  private NewsDAO newsDataAccess = DAOFactory.getNewsDAO();
  private EventDAO eventsDataAccess = DAOFactory.getEventDAO();

  @Override
  public void retrieveData(ParsedDataDTO parsedData) {
    SourceDTO source = parsedData.getSource();
    if (parsedData.getNews() == null) {
      eventsDataAccess.addSource(source);
      retrieveEvents(parsedData.getEvents(), source);
    } else {
      newsDataAccess.addSource(source);
      retrieveNews(parsedData.getNews(), source);
    }
  }

  private void retrieveNews(List<NewsArticleDTO> crawlerNews, SourceDTO source) {
    databaseNews = newsDataAccess.findAllBySource(source);

    for (NewsArticleDTO newsArticle : crawlerNews) {
      if (databaseNews.contains(newsArticle)) {
        if (newsArticle.getDate() > databaseNews.get(databaseNews.indexOf(newsArticle)).getDate()) {
          newsDataAccess.update(newsArticle);
        }
      } else {
        newsDataAccess.create(newsArticle);
      }
    }
  }

  private void retrieveEvents(List<EventDTO> crawlerEvents, SourceDTO source) {
    databaseEvents = eventsDataAccess.findAll();

    for (EventDTO event : crawlerEvents) {
      if (databaseEvents.contains(event)) {
        eventsDataAccess.update(event);
      } else {
        eventsDataAccess.create(event);
      }
    }

  }

}
