package org.iqu.webcrawler.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Client {

  private String host;
  private String location;
  private int port;
  private boolean active;

  public Client(String host, int port, String location, boolean active) {
    super();
    this.host = host;
    this.port = port;
    this.location = location;
    this.active = active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getHost() {
    return this.host;
  }

}
