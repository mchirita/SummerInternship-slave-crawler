package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.EventDTO;
import org.iqu.persistence.entities.Type;

public interface EventDAO extends DaoService<EventDTO> {

  public List<Type> retrieveTypesAndSubtypes();
}
