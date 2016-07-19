package org.iqu.crawler.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.iqu.crawler.configuration.entities.SourceConfig;
import org.iqu.crawler.configuration.exception.ConfigLoaderException;

/**
 * 
 * @author Beniamin Savu
 *
 */
public class CrawlerConfigLoader implements ConfigLoader {

	private String path;
	private ConfigChangeNotifier notifier;
	private Properties properties = new Properties();
	private List<SourceConfig> crawlerProperties = new ArrayList<SourceConfig>();
	private File file;
	private Long lastModified;

	public CrawlerConfigLoader(ConfigChangeNotifier notifier, String path) {
		this.notifier = notifier;
		this.path = path;
		file = new File(path);
	}

	@Override
	public void run() {
		if (fileIsValid()) {
			if (lastModified == null) {
				readProperties(crawlerProperties);
			} else if (lastModified < file.lastModified()) {
				checkForNewProperties();
			}
		} else {
			throw new ConfigLoaderException("File corrupted");
		}

	}

	private boolean fileIsValid() {
		boolean valid = false;
		if (file.exists() && file.isFile()) {
			valid = true;
		}

		return valid;
	}

	private void loadProperties() {

		try (InputStream input = new FileInputStream(file)) {
			properties.load(input);
		} catch (IOException e) {
			throw new ConfigLoaderException("Could not load content from file");
		}
	}

	private void readProperties(List<SourceConfig> crawlerProperties) {
		loadProperties();

		String numberOfParsers = properties.getProperty(CrawlerConfigParameters.NUMBER_OF_PARSERS);
		if (numberOfParsers == null || numberOfParsers.equals("")) {
			throw new ConfigLoaderException("No properties");
		}
		int length = Integer.parseInt(numberOfParsers);

		for (int i = 1; i <= length; i++) {
			String parserName = properties.getProperty(CrawlerConfigParameters.PARSER_PREFIX + i);
			String source = properties.getProperty(CrawlerConfigParameters.SOURCE_PREFIX + i);
			if (parserName == null || parserName.equals("") || source == null || source.equals("")) {
				throw new ConfigLoaderException("Properties values are null or empty");
			}

			SourceConfig crawlerProperty = new SourceConfig(parserName, source);
			crawlerProperties.add(crawlerProperty);

		}

		lastModified = file.lastModified();
	}

	private void checkForNewProperties() {
		boolean newPropertyFound = false;
		List<SourceConfig> newCrawlerProperties = new ArrayList<SourceConfig>();

		readProperties(newCrawlerProperties);
		for (SourceConfig newCrawlerProperty : newCrawlerProperties) {
			if (crawlerProperties.contains(newCrawlerProperty)) {
				newPropertyFound = true;
			}
		}

		if (newPropertyFound) {
			updateProperties(newCrawlerProperties);
			notifier.notify(crawlerProperties);
		}

	}

	private void updateProperties(List<SourceConfig> newCrawlerProperties) {
		crawlerProperties.clear();
		crawlerProperties.addAll(newCrawlerProperties);

	}
}
