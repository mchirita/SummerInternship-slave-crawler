package org.iqu.persistence.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining a news article.
 * 
 * @author Cristi Badoi
 */
public class NewsArticleDTO {
  private long date;
  private long id;
  private String guid;
  private String title;
  private String subtitle;
  private String description;
  private List<String> authors;
  private List<String> categories;
  private String source;
  private String body;
  private List<String> images;
  private String thumbnail_id;
  private String external_url;
  private String enclosure;

  public NewsArticleDTO() {
    authors = new ArrayList<String>();
    categories = new ArrayList<String>();
    images = new ArrayList<String>();
    guid = "";
    title = "";
    subtitle = "";
    description = "";
    source = "";
    body = "";
    thumbnail_id = "";
    external_url = "";
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public String getGuid() {
    return guid;
  }

  public void setGuid(String id) {
    this.guid = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getAuthors() {
    return authors;
  }

  public void setAuthors(List<String> authors) {
    this.authors = authors;
  }

  public List<String> getCategories() {
    return categories;
  }

  public void setCategories(List<String> categories) {
    this.categories = categories;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public String getThumbnail_id() {
    return thumbnail_id;
  }

  public void setThumbnail_id(String thumbnail_id) {
    this.thumbnail_id = thumbnail_id;
  }

  public String getExternal_url() {
    return external_url;
  }

  public void setExternal_url(String external_url) {
    this.external_url = external_url;
  }

  public String getEnclosure() {
    return enclosure;
  }

  public void setEnclosure(String enclosure) {
    this.enclosure = enclosure;
  }

  @Override
  public String toString() {
    return "NewsArticle [date=" + date + ", id=" + guid + ", title=" + title + ", subtitle=" + subtitle
        + ", description=" + description + ", authors=" + authors + ", categories=" + categories + ", source=" + source
        + ", body=" + body + ", images=" + images + ", thumbnail_id=" + thumbnail_id + ", external_url=" + external_url
        + "]";
  }

  /**
   * Based solely on the id field;
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((guid == null) ? 0 : guid.hashCode());
    return result;
  }

  /**
   * Based solely on the id field;
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    NewsArticleDTO other = (NewsArticleDTO) obj;
    if (guid == null) {
      if (other.guid != null)
        return false;
    } else if (!guid.equals(other.guid))
      return false;
    return true;
  }

}
