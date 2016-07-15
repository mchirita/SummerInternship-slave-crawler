package org.iqu.persistence.news;

import java.util.List;

import org.iqu.persistence.entities.Source;

public interface SourceDao {
	public List<Source> getAllStudents();

	public Source getStudent(int rollNo);

	public void updateStudent(Source source);

	public void deleteStudent(Source source);
}
