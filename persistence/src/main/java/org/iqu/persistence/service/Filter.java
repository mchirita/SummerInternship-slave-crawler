package org.iqu.persistence.service;

import java.util.List;

public interface Filter<E> {
  public List<E> checkForAuthors(List<E> entities, String authors);

  public List<E> checkForSubtypes(List<E> entities, String subtypes);

  public List<E> checkForCategories(List<E> entities, String categories);

  public List<E> checkForAbout(List<E> entities, String about);
}
