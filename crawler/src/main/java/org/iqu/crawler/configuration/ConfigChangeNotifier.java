package org.iqu.crawler.configuration;

import java.util.List;

public interface ConfigChangeNotifier {
	public void register(ConfigChangeListener listener);
	
	public void remove(ConfigChangeListener listener);
	
	public void removeAll();
	
	public void notify(List<CrawlerProperty> properties);
}
