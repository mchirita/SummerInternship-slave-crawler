package org.iqu.crawler.configuration;

import java.util.List;

import org.iqu.crawler.configuration.entities.SourceConfig;

/**
 * 
 * @author Beniamin Savu
 * 
 *         General loader for a configuration file. Loads and offers the
 *         properties.
 *
 */
public interface ConfigLoader extends Runnable {

	/**
	 * Loads the properties from a configuration file
	 */
	public void loadProperties();

	public List<SourceConfig> getProperties();

}
