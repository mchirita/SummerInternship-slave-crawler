package org.iqu.persistence.entities;

public class SourceDTO {
  private int sourceId;
  private String displayName;
  private String description;
  private String image;

  public SourceDTO(int sourceId, String displayName, String description, String image) {
    this.sourceId = sourceId;
    this.displayName = displayName;
    this.description = description;
    this.image = image;
  }

  public SourceDTO() {

  }

  public int getId() {
    return sourceId;
  }

  public void setId(int sourceId) {
    this.sourceId = sourceId;
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

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
    result = prime * result + ((image == null) ? 0 : image.hashCode());
    result = prime * result + sourceId;
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
    SourceDTO other = (SourceDTO) obj;
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
    if (image == null) {
      if (other.image != null)
        return false;
    } else if (!image.equals(other.image))
      return false;
    if (sourceId != other.sourceId)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SourceDTO [sourceId=" + sourceId + ", displayName=" + displayName + ", description=" + description + "]";
  }

}