package org.iqu.persistence.service;

import java.util.List;

import org.iqu.persistence.entities.Author;
import org.iqu.persistence.entities.Source;

public interface DaoService<E> {

	public void create(E entity);

	public void update(E entity);

	public E find(E entity);

	public List<E> findAll();

	public void delete(E entity);

	public List<Author> retrieveAuthors();

	public List<Source> retrieveSources();
}
