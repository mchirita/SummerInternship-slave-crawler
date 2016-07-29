package org.iqu.webcrawler.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity that represents an event.
 * 
 * @author iQuest
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Event {
  private long startDate;
  private long endDate;
  private long id;
  private String title;
  private String subtitle;
  private String description;
  private String type;
  private List<String> subtypes;
  private String source;
  private String body;
  private List<String> image_id;
  private String thumbnail_id;
  private String external_url;
  private List<String> author;

  public Event() {
    // TODO Auto-generated constructor stub
  }

  public Event(long startDate, long endDate, long id, String title, String subtitle, String description, String type,
      List<String> subtypes, String source, String body, List<String> image_id, String thumbnail_id,
      String external_url, List<String> author) {
    super();
    this.startDate = startDate;
    this.endDate = endDate;
    this.id = id;
    this.title = title;
    this.subtitle = subtitle;
    this.description = description;
    this.type = type;
    this.subtypes = subtypes;
    this.source = source;
    this.body = body;
    this.image_id = image_id;
    this.thumbnail_id = thumbnail_id;
    this.external_url = external_url;
    this.author = author;
  }

  public long getStartDate() {
    return startDate;
  }

  public void setStartDate(long startDate) {
    this.startDate = startDate;
  }

  public long getEndDate() {
    return endDate;
  }

  public void setEndDate(long endDate) {
    this.endDate = endDate;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<String> getSubtypes() {
    return subtypes;
  }

  public void setSubtypes(List<String> subtypes) {
    this.subtypes = subtypes;
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

  public List<String> getAuthor() {
    return author;
  }

  public void setAuthor(List<String> author) {
    this.author = author;
  }

}
