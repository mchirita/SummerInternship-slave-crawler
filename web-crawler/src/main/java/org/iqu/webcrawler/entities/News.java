package org.iqu.webcrawler.entities;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class News {
  private Set<SingleNews> news = new HashSet<SingleNews>();

  public News() {
  }

  public News(Set<SingleNews> news) {
    super();
    this.news = news;
  }

  @XmlElement
  public Set<SingleNews> getNews() {
    return news;
  }

  public void setNews(Set<SingleNews> news) {
    this.news = news;
  }

  public void add(SingleNews singleNews1) {
    news.add(singleNews1);

  }

}
