package org.iqu.crawler.configuration;

import java.util.List;

public interface ConfigChangeListener {
	public void onConfigChenage(List<SourceConfiguration> properties);
}
