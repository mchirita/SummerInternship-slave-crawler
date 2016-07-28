package org.iqu.webcrawler.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Source {
  private int id;
  private String displayName;
  private String description;
  private String image;

  public Source() {
  }

  public Source(int id, String displayName, String description, String image) {
    this.id = id;
    this.displayName = displayName;
    this.description = description;
    this.image = image;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
    result = prime * result + id;
    result = prime * result + ((image == null) ? 0 : image.hashCode());
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
    Source other = (Source) obj;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (displayName == null) {
      if (other.displayName != null)
        return false;
    } else if (!displayName.equals(other.displayName))
      return false;
    if (id != other.id)
      return false;
    if (image == null) {
      if (other.image != null)
        return false;
    } else if (!image.equals(other.image))
      return false;
    return true;
  }

}