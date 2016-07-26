package org.iqu.webcrawler.entities;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.iqu.parsers.entities.NewsArticle;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class News {
  private Set<NewsArticle> news = new HashSet<NewsArticle>();

  public News() {
  }

  public News(Set<NewsArticle> news) {
    super();
    this.news = news;
  }

  @XmlElement
  public Set<NewsArticle> getNews() {
    return news;
  }

  public void setNews(Set<NewsArticle> news) {
    this.news = news;
  }

  public void add(NewsArticle singleNews) {
    news.add(singleNews);
  }

}
