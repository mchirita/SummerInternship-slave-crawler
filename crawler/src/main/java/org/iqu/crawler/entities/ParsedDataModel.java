package org.iqu.crawler.entities;

import java.util.List;

import org.iqu.parsers.entities.EventModel;
import org.iqu.parsers.entities.NewsArticleModel;
import org.iqu.parsers.entities.SourceModel;

/**
 * Wrapper class for information provided by a parser.
 * 
 * @author Cristi Badoi
 */
public class ParsedDataModel {

  private List<NewsArticleModel> news;
  private List<EventModel> events;
  private SourceModel source;

  public List<NewsArticleModel> getNews() {
    return news;
  }

  public void setNews(List<NewsArticleModel> news) {
    this.news = news;
  }

  public List<EventModel> getEvents() {
    return events;
  }

  public void setEvents(List<EventModel> events) {
    this.events = events;
  }

  public SourceModel getSource() {
    return source;
  }

  public void setSource(SourceModel source) {
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
    ParsedDataModel other = (ParsedDataModel) obj;
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
