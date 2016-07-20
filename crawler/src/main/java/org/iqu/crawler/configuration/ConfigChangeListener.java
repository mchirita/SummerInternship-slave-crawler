package org.iqu.crawler.configuration;

import java.util.List;

import org.iqu.crawler.configuration.entities.SourceConfig;

/**
 * 
 * @author Beniamin Savu
 * 
 *         General listener for an item and performs some operations if
 *         something changes.
 *
 */
public interface ConfigChangeListener {

	/**
	 * Perform operations when the specific properties change
	 * 
	 * @param properties
	 *          the changed properties
	 */
	public void onConfigChange(List<SourceConfig> properties);
}
