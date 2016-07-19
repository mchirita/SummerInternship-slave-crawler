package org.iqu.crawler.configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Beniamin Savu
 *
 */
public class CrawlerChangeNotifier implements ConfigChangeNotifier {

	private List<ConfigChangeListener> listeners = new ArrayList<ConfigChangeListener>(); 
	
	@Override
	public void register(ConfigChangeListener listener) {
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
	public void notify(List<CrawlerProperty> properties) {
		for (ConfigChangeListener listener : listeners) {
			listener.onConfigChange(properties);
		}

	}

}
