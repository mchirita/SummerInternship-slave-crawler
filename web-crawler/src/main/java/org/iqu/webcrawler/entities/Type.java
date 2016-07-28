package org.iqu.webcrawler.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Implements a type of event
 * 
 * @author Razvan Rosu
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Type {

  private String type;
  private List<String> subtypes = new ArrayList<String>();

  public Type() {

  }

  public Type(String nameOfType, List<String> subtypes) {
    super();
    this.type = nameOfType;
    this.subtypes = subtypes;
  }

  public String getType() {
    return type;
  }

  public void setNameOfType(String nameOfType) {
    this.type = nameOfType;
  }

  public List<String> getSubTypes() {
    return subtypes;
  }

  public void setSubTypes(List<String> subtypes) {
    this.subtypes = subtypes;
  }

}
