package org.iqu.crawler.configuration;

import java.io.IOException;

public interface ConfigLoader {
	public void loadProperties() throws ConfigLoaderException, IOException;
}
