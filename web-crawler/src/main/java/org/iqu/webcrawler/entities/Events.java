package org.iqu.webcrawler.entities;

import java.util.HashSet;
import java.util.Set;

<<<<<<< HEAD
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.iqu.parsers.entities.Event;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Events {

  private Set<Event> events = new HashSet<Event>();

  public Events(Set<Event> events) {
    super();
    this.events = events;
  }

  public Events() {
    // TODO Auto-generated constructor stub
=======
import org.iqu.parsers.entities.Event;

public class Events {

  private Set<Event> events = new HashSet<Event>();

  public Events(Set<Event> events) {
    super();
    this.events = events;
>>>>>>> master
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public void removeEvent(Event event) {
    events.remove(event);
  }

<<<<<<< HEAD
  @XmlElement
=======
>>>>>>> master
  public Set<Event> getEvents() {
    return events;
  }

  public void setEvents(Set<Event> events) {
    this.events = events;
  }

}
