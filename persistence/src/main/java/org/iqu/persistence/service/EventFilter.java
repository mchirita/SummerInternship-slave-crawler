package org.iqu.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.iqu.persistence.entities.EventDTO;

public class EventFilter implements Filter<EventDTO> {

  @Override
  public List<EventDTO> checkForAuthors(List<EventDTO> entities, String authors) {
    List<EventDTO> resultList = new ArrayList<EventDTO>();
    String[] authorsArray = authors.split(";");
    for (EventDTO event : entities) {
      for (int i = 0; i < authorsArray.length; i++) {
        if (event.getAuthors().contains(authorsArray[i])) {
          resultList.add(event);
          break;
        }
      }
    }
    return resultList;
  }

  @Override
  public List<EventDTO> checkForSubtypes(List<EventDTO> entities, String subtypes) {
    List<EventDTO> resultList = new ArrayList<EventDTO>();
    String[] subtypesArray = subtypes.split(";");
    for (EventDTO event : entities) {
      for (int i = 0; i < subtypesArray.length; i++) {
        if (event.getSubtypes().contains(subtypesArray[i])) {
          resultList.add(event);
          break;
        }
      }
    }
    return resultList;

  }

  @Override
  public List<EventDTO> checkForCategories(List<EventDTO> entities, String categories) {
    return null;

  }

  @Override
  public List<EventDTO> checkForAbout(List<EventDTO> entities, String about) {
    // TODO Auto-generated method stub
    return null;
  }

}
