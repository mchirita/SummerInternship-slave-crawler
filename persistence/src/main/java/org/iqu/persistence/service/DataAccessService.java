package org.iqu.persistence.service;

import java.util.List;

public interface DataAccessService<T> {

	public List<T> findAll();

	public T find(int rollNo);

	public void update(T type);

	public void delete(T type);

}
