package org.iqu.crawler.configuration;

import java.util.List;

import org.iqu.crawler.configuration.entities.SourceConfig;

public interface ConfigChangeNotifier {
	public void register(ConfigChangeListener listener);

	public void remove(ConfigChangeListener listener);

	public void removeAll();

	public void notify(List<SourceConfig> properties);
}
