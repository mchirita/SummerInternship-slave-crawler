package org.iqu.parsers.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Class defining a news article.
 * 
 * @author Cristi Badoi
 */
public class NewsArticle {
  private long date;
  private String id;
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

  public NewsArticle() {
    authors = new ArrayList<String>();
    categories = new ArrayList<String>();
    images = new ArrayList<String>();
    id = "";
    title = "";
    subtitle = "";
    description = "";
    source = "";
    body = "";
    thumbnail_id = "";
    external_url = "";
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
    return "NewsArticle [date=" + date + ", id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", description="
        + description + ", authors=" + authors + ", categories=" + categories + ", source=" + source + ", body=" + body
        + ", images=" + images + ", thumbnail_id=" + thumbnail_id + ", external_url=" + external_url + "]";
  }

  /**
   * Based solely on the id field;
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    NewsArticle other = (NewsArticle) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
