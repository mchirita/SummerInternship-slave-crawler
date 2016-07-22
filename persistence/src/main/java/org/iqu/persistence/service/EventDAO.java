package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.Event;
import org.iqu.persistence.entities.Type;

public interface EventDAO extends DaoService<Event> {
	public List<Type> retrieveTypesAndSubtypes();
}
