package org.iqu.crawler.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class ConfigurationManager {
	
	private String path = "/config/iqu/crawler-app-config/crawler.properties";
	private ConfigChangeNotifier notifier = new CrawlerChangeNotifier(); 
	private ConfigLoader loader = new CrawlerConfigLoader(notifier, path);
	private Properties prop = new Properties();
	private InputStream input = null;
	private List<SourceConfiguration> properties = new ArrayList<SourceConfiguration>();

	public void loadFile() {
		try {
			input = new FileInputStream("/");
			prop.load(input);
		} catch (IOException ex) {
			
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				
			}
		}
	}

	public List<SourceConfiguration> getProperties() {
		
		int length = Integer.parseInt(prop.getProperty("length"));
		for (int i = 1; i <= length; i++) {
			String parserName = prop.getProperty("parser" + i);
			String source = prop.getProperty("source" + i);
			properties.add(new SourceConfiguration(parserName, source));
		}

		return properties;
	}
}
