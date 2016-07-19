package org.iqu.crawler.configuration;

import java.util.List;

import org.iqu.crawler.configuration.entities.SourceConfig;

public interface ConfigChangeListener {

	public void onConfigChange(List<SourceConfig> properties);
}
