package org.iqu.crawler.configuration;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 
 * @author Beniamin Savu
 *
 */
public class ConfigurationManager implements Runnable {
	
	private String path = "../config/iqu/crawler-app-config/crawler.properties";
	private ConfigChangeNotifier notifier = new CrawlerChangeNotifier(); 
	private ConfigLoader loader = new CrawlerConfigLoader(notifier, path);
	private ScheduledExecutorService executor;

	public void init(){
		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleWithFixedDelay(new ConfigurationManager(), 0, 10, TimeUnit.MINUTES);
	}
	

	@Override
	public void run() {
		try {
			loader.loadProperties();
		} catch (ConfigLoaderException e) {

		} catch (IOException e) {

		}
		
	}
}
