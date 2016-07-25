package org.iqu.crawler.entities;

import java.util.List;

import org.iqu.parsers.entities.Event;
import org.iqu.parsers.entities.NewsArticle;
import org.iqu.parsers.entities.Source;

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

  public Source getSource() {
    return source;
  }

  public void setSource(Source source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return "ParsedData [news=" + news + ", events=" + events + ", source=" + source + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((events == null) ? 0 : events.hashCode());
    result = prime * result + ((news == null) ? 0 : news.hashCode());
    result = prime * result + ((source == null) ? 0 : source.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ParsedData other = (ParsedData) obj;
    if (events == null) {
      if (other.events != null)
        return false;
    } else if (!events.equals(other.events))
      return false;
    if (news == null) {
      if (other.news != null)
        return false;
    } else if (!news.equals(other.news))
      return false;
    if (source == null) {
      if (other.source != null)
        return false;
    } else if (!source.equals(other.source))
      return false;
    return true;
  }

}
