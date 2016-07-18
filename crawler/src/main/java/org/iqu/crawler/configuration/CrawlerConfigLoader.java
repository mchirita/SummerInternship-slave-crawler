package org.iqu.crawler.configuration;

public class CrawlerConfigLoader implements ConfigLoader {

	private String path;
	private ConfigChangeNotifier notifier;
	
	public CrawlerConfigLoader(ConfigChangeNotifier notifier, String path){
		this.notifier = notifier;
		this.path = path;
	}
	
	@Override
	public void loadProperties() throws ConfigLoaderException {
		

	}

}
