package org.iqu.persistence.entities;

import java.util.List;

public class ParsedDataDTO {

  private List<NewsArticleDTO> news;
  private List<EventDTO> events;
  private SourceDTO source;

  public List<NewsArticleDTO> getNews() {
    return news;
  }

  public void setNews(List<NewsArticleDTO> news) {
    this.news = news;
  }

  public List<EventDTO> getEvents() {
    return events;
  }

  public void setEvents(List<EventDTO> events) {
    this.events = events;
  }

  public SourceDTO getSource() {
    return source;
  }

  public void setSource(SourceDTO source) {
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
    ParsedDataDTO other = (ParsedDataDTO) obj;
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
