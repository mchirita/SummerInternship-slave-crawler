package org.iqu.crawler.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.ConfigurationException;


/**
 * 
 * @author Beniamin Savu
 *
 */
public class CrawlerConfigLoader implements ConfigLoader{

	private String path;
	private ConfigChangeNotifier notifier;
	private Properties properties = new Properties();
	private List<CrawlerProperty> crawlerProperties = new ArrayList<CrawlerProperty>();

	public CrawlerConfigLoader(ConfigChangeNotifier notifier, String path) {
		this.notifier = notifier;
		this.path = path;
	}

	@Override
	public void loadProperties() throws ConfigLoaderException, IOException {
		init();
		if (crawlerProperties.size() == 0) {
			readProperties(crawlerProperties);
		} else {
			checkForNewProperties();
		}

		System.out.println(crawlerProperties);
	}

	private void init() throws IOException {
		notifier.removeAll();

		try (InputStream input = new FileInputStream(path)) {
			properties.load(input);
		}
	}

	private void readProperties(List<CrawlerProperty> crawlerProperties) throws ConfigLoaderException {
		String numberOfParsers = properties.getProperty("length");
		if(numberOfParsers==null || numberOfParsers.equals("")){
			throw new ConfigLoaderException("No properties");
		}
		int length = Integer.parseInt(numberOfParsers);
		for (int i = 1; i <= length; i++) {
			String parserName = properties.getProperty("parser" + i);
			String source = properties.getProperty("source" + i);
			
			if(parserName==null || parserName.equals("") || source==null || source.equals("")){
				throw new ConfigLoaderException("Properties values are null or empty");
			}
			
			CrawlerProperty crawlerProperty = new CrawlerProperty(parserName, source);
			crawlerProperties.add(crawlerProperty);
			
		}

	}

	private void checkForNewProperties() throws ConfigLoaderException {
		boolean newPropertyFound = false;
		List<CrawlerProperty> newCrawlerProperties = new ArrayList<CrawlerProperty>();

		readProperties(newCrawlerProperties);
		for (CrawlerProperty newCrawlerProperty : newCrawlerProperties) {
			if (crawlerProperties.contains(newCrawlerProperty)) {
				newPropertyFound = true;
			}
		}

		if (newPropertyFound) {
			updateProperties(newCrawlerProperties);
			notifier.notify(crawlerProperties);
		}

	}

	private void updateProperties(List<CrawlerProperty> newCrawlerProperties) {
		crawlerProperties.clear();
		crawlerProperties.addAll(newCrawlerProperties);

	}
}
