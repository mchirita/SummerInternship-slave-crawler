package org.iqu.persistence.news;

import java.util.List;

import org.iqu.persistence.entities.Event;

public interface EventDao {
	
	public List<Event> findAll();

	public Event find(int rollNo);

	public void update(Event event);

	public void delete(Event event);
}
