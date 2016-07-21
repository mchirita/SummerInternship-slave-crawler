package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.News;

public interface NewsService {

	public void create(News news);

	public void update(News news);

	public News find(News news);

	public List<News> findAll();

	public void delete(News news);
}
