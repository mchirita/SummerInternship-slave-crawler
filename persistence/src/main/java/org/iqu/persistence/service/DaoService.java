package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.SourceDTO;

public interface DaoService<E> {

  public void create(E entity);

  public void update(E entity);

  public E find(E entity);

  public List<E> findAll();

  public void delete(E entity);

  public List<String> retrieveAuthors();

  public List<SourceDTO> retrieveSources();

  public List<E> findAllBySource(SourceDTO source);

  public void addSource(SourceDTO source);
}
