package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.NewsArticle;

public interface NewsDAO extends DaoService<NewsArticle> {

	public List<String> retrieveCategories();

}
