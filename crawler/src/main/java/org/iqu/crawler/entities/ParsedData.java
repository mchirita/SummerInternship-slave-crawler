package org.iqu.crawler.entities;

import java.util.List;

import javax.xml.transform.Source;

import org.iqu.parsers.entities.Event;
import org.iqu.parsers.entities.NewsArticle;

public class ParsedData {

  private List<NewsArticle> news;
  private List<Event> events;
  private Source source;

  public List<NewsArticle> getNews() {
    return news;
  }

  public void setNews(List<NewsArticle> news) {
    this.news = news;
  }

  public List<Event> getEvents() {
    return events;
  }

  public void setEvents(List<Event> events) {
    this.events = events;
  }

}
