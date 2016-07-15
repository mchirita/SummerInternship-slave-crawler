package org.iqu.persistence.news;

import java.util.List;

import org.iqu.persistence.entities.Event;

public interface EventDao {
	
	public List<Event> getAllStudents();

	public Event getStudent(int rollNo);

	public void updateStudent(Event event);

	public void deleteStudent(Event event);
}
