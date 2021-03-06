package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.NewsArticleDTO;

public interface NewsDAO extends DaoService<NewsArticleDTO> {

  public List<String> retrieveCategories();

  public List<NewsArticleDTO> retrieveNews(long startDate, long endDate, String categories, String about, int sourceId,
      String authors);

}
