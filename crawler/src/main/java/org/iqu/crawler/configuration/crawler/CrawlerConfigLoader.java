package org.iqu.crawler.configuration.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.iqu.crawler.configuration.ConfigChangeHandler;
import org.iqu.crawler.configuration.ConfigLoader;
import org.iqu.crawler.configuration.entities.SourceConfig;
import org.iqu.crawler.configuration.exception.ConfigLoaderException;
import org.iqu.crawler.configuration.exception.LoaderExceptionConstants;

/**
 * 
 * @author Beniamin Savu
 *
 *         This class implements the ConfigLoader interface. This class loads
 *         the crawler properties
 */
public class CrawlerConfigLoader implements ConfigLoader {

	private String path;
	private ConfigChangeHandler notifier;
	private Properties properties = new Properties();
	private List<SourceConfig> crawlerProperties = new ArrayList<SourceConfig>();
	private File file;
	private Long lastModified;

	public CrawlerConfigLoader(ConfigChangeHandler notifier, String path) {
		this.notifier = notifier;
		this.path = path;
		file = new File(path);
	}

	@Override
	public void loadProperties() {
		if (fileIsValid()) {
			if (lastModified == null) {
				readProperties(crawlerProperties);
			} else if (lastModified < file.lastModified()) {
				checkForNewProperties();
			}
		} else {
			throw new ConfigLoaderException(LoaderExceptionConstants.FILE_CORRUPTED);
		}
	}

	@Override
	public List<SourceConfig> getProperties() {
		return crawlerProperties;
	}

	@Override
	public void run() {
		loadProperties();
	}

	private boolean fileIsValid() {
		boolean valid = false;
		if (file.exists() && file.isFile()) {
			valid = true;
		}

		return valid;
	}

	private void loadFile() {

		try (InputStream input = new FileInputStream(file)) {
			properties.load(input);
		} catch (IOException e) {
			throw new ConfigLoaderException(LoaderExceptionConstants.FILE_UNLOADED, e);
		}
	}

	private void readProperties(List<SourceConfig> crawlerProperties) {
		loadFile();

		String numberOfParsers = properties.getProperty(CrawlerConfigConstants.NUMBER_OF_PARSERS_KEY);
		if (numberOfParsers == null || numberOfParsers.equals("")) {
			throw new ConfigLoaderException(LoaderExceptionConstants.PROPERTIES_NOT_FOUND);
		}
		int length = Integer.parseInt(numberOfParsers);

		for (int i = 1; i <= length; i++) {
			String parserName = properties.getProperty(CrawlerConfigConstants.PARSER_KEY_PREFIX + i);
			String source = properties.getProperty(CrawlerConfigConstants.SOURCE_KEY_PREFIX + i);
			if (parserName == null || parserName.equals("") || source == null || source.equals("")) {
				throw new ConfigLoaderException(LoaderExceptionConstants.NULL_PROPERTIES_VALUES);
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
