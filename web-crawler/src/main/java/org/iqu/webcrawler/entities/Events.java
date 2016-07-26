package org.iqu.webcrawler.entities;

import java.util.HashSet;
import java.util.Set;

public class Events {

  private Set<Event> events = new HashSet<Event>();

  public Events(Set<Event> events) {
    super();
    this.events = events;
  }

  public void addEvent(Event event) {
    events.add(event);
  }

  public void removeEvent(Event event) {
    events.remove(event);
  }

  public Set<Event> getEvents() {
    return events;
  }

  public void setEvents(Set<Event> events) {
    this.events = events;
  }

}
