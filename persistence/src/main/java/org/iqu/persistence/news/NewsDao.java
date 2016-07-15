package org.iqu.persistence.news;

import java.util.List;

import org.iqu.persistence.entities.News;

public interface NewsDao {
	
	public List<News> getAllStudents();

	public News getStudent(int rollNo);

	public void updateStudent(News news);

	public void deleteStudent(News news);
}
