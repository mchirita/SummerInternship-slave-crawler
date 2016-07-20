package org.iqu.crawler.configuration;

import java.util.List;

import org.iqu.crawler.configuration.entities.SourceConfig;

/**
 * 
 * @author Beniamin Savu
 * 
 *         General handler for an observable item. Adding, removing and
 *         notifying listeners
 *
 */
public interface ConfigChangeHandler {

	/**
	 * Add the specific listener
	 * 
	 * @param listener
	 *          element to be added
	 */
	public void addListener(ConfigChangeListener listener);

	/**
	 * Remove the specific listener
	 * 
	 * @param listener
	 */
	public void remove(ConfigChangeListener listener);

	/**
	 * Removes all listeners
	 */
	public void removeAll();

	/**
	 * Notifies all listeners if the file has changed, sending the new properties
	 * 
	 * @param properties
	 *          the new properties
	 */
	public void notify(List<SourceConfig> properties);
}
