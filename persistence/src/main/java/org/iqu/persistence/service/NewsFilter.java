package org.iqu.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.iqu.persistence.entities.NewsArticleDTO;

public class NewsFilter implements Filter<NewsArticleDTO> {

  @Override
  public List<NewsArticleDTO> checkForAuthors(List<NewsArticleDTO> entities, String authors) {
    List<NewsArticleDTO> resultList = new ArrayList<NewsArticleDTO>();
    String[] authorsArray = authors.split(";");
    for (NewsArticleDTO article : entities) {
      for (int i = 0; i < authorsArray.length; i++) {
        if (article.getAuthors().contains(authorsArray[i])) {
          resultList.add(article);
          break;
        }
      }
    }
    return resultList;
  }

  @Override
  public List<NewsArticleDTO> checkForSubtypes(List<NewsArticleDTO> entities, String subtypes) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<NewsArticleDTO> checkForCategories(List<NewsArticleDTO> entities, String categories) {
    List<NewsArticleDTO> resultList = new ArrayList<NewsArticleDTO>();
    String[] categoriesArray = categories.split(";");
    for (NewsArticleDTO article : entities) {
      for (int i = 0; i < categoriesArray.length; i++) {
        if (article.getCategories().contains(categoriesArray[i])) {
          resultList.add(article);
          break;
        }
      }
    }
    return resultList;
  }

  @Override
  public List<NewsArticleDTO> checkForAbout(List<NewsArticleDTO> entities, String about) {
    List<NewsArticleDTO> resultList = new ArrayList<NewsArticleDTO>();
    String[] aboutsArray = about.split(";");

    for (NewsArticleDTO article : entities) {
      boolean ok = true;
      for (int i = 0; i < aboutsArray.length; i++) {
        if (!(article.getBody().contains(aboutsArray[i]) || article.getTitle().contains(aboutsArray[i])
            || article.getSubtitle().contains(aboutsArray[i]))) {
          ok = false;
        }
      }
      if (ok) {
        resultList.add(article);
      }
    }
    return null;
  }

}
