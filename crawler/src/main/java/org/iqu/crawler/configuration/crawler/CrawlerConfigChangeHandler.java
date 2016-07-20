package org.iqu.crawler.configuration.crawler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.iqu.crawler.configuration.ConfigChangeHandler;
import org.iqu.crawler.configuration.ConfigChangeListener;
import org.iqu.crawler.configuration.entities.SourceConfig;

/**
 * 
 * @author Beniamin Savu
 * 
 *         This class implements the ConfigChangeHandler interface. This class
 *         offers basic operation(add, remove) and notifies the listeners if
 *         changes appear
 */
public class CrawlerConfigChangeHandler implements ConfigChangeHandler {

	private Set<ConfigChangeListener> listeners = new HashSet<ConfigChangeListener>();

	public CrawlerConfigChangeHandler() {
	}

	@Override
	public void addListener(ConfigChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void remove(ConfigChangeListener listener) {
		listeners.remove(listener);

	}

	@Override
	public void removeAll() {
		listeners.clear();
	}

	@Override
	public void notify(List<SourceConfig> properties) {
		for (ConfigChangeListener listener : listeners) {
			listener.onConfigChange(properties);
		}

	}

}
