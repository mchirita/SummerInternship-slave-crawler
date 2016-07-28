package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.EventDTO;
import org.iqu.persistence.entities.TypeDTO;

public interface EventDAO extends DaoService<EventDTO> {

  public List<TypeDTO> retrieveTypesAndSubtypes();
}
