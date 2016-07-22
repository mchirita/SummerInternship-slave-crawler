package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.Category;
import org.iqu.persistence.entities.News;

public interface NewsDAO extends DaoService<News> {

	public List<Category> retrieveCategories();
}
