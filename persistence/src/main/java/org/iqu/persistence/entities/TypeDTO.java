package org.iqu.persistence.entities;

import java.util.List;

public class TypeDTO {
  private long id;
  private String type;
  private List<String> subtypes;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  @Override
  public String toString() {
    return "TypeDTO [type=" + type + ", subtypes=" + subtypes + "]";
  }

}
