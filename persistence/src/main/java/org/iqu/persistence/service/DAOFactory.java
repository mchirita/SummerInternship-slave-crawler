package org.iqu.persistence.service;

public interface DAOFactory {

	public NewsDAO getNewsDAO();

	public EventDAO getEventDAO();
}
