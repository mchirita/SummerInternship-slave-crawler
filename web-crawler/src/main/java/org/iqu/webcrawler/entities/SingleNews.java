package org.iqu.webcrawler.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * News - Entity that represents a news.
 * 
 * @author Alex Dragomir
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SingleNews {

  private long date;
  private long id;
  private String title;
  private String subtitle;
  private String description;
  private List<String> authors;
  private List<String> categories;
  private String source;
  private String body;
  private List<String> image_id;
  private String thumbnail_id;
  private String external_url;

  public SingleNews() {
  }

  public SingleNews(long date, long id, String title, String subtitle, String description, List<String> authors,
      List<String> categories, String source, String body, List<String> image_id, String thumbnail_id,
      String external_url) {
    super();
    this.date = date;
    this.id = id;
    this.title = title;
    this.subtitle = subtitle;
    this.description = description;
    this.authors = authors;
    this.categories = categories;
    this.source = source;
    this.body = body;
    this.image_id = image_id;
    this.thumbnail_id = thumbnail_id;
    this.external_url = external_url;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public List<String> getImage_id() {
    return image_id;
  }

  public void setImage_id(List<String> image_id) {
    this.image_id = image_id;
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

}
