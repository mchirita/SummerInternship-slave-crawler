package org.iqu.crawler.configuration;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.iqu.crawler.configuration.crawler.CrawlerConfigChangeHandler;
import org.iqu.crawler.configuration.crawler.CrawlerConfigLoader;
import org.iqu.crawler.configuration.entities.SourceConfig;

/**
 * 
 * @author Beniamin Savu
 *
 *         Manages the initialization, loads the initial properties and starts a
 *         thread that will run periodically at 10 minutes and checks for new
 *         changes, register listeners to a notifier and returns the properties
 *         from the configuration file.
 * 
 */
public class ConfigurationManager {

	private final static String PATH = "/iqu/crawler-app-config/crawler.properties";
	private static volatile ConfigurationManager instance = null;
	private ConfigChangeHandler notifier;
	private ConfigLoader loader;
	private ScheduledExecutorService executor;

	private ConfigurationManager() {
		notifier = new CrawlerConfigChangeHandler();
		loader = new CrawlerConfigLoader(notifier, PATH);
		loader.loadProperties();
		init();
	}

	/**
	 * Perform a double check so that only one instance is created at a time
	 * 
	 * @return the instance of the class
	 */
	public static synchronized ConfigurationManager getInstance() {
		if (instance == null) {
			synchronized (ConfigurationManager.class) {
				if (instance == null) {
					instance = new ConfigurationManager();
				}
			}
		}
		return instance;

	}

	/**
	 * Initiates the thread shutdown and clears all listeners
	 */
	public void destroy() {
		executor.shutdown();
		notifier.removeAll();
	}

	/**
	 * Adds the specified element to the notifier if it is not already present
	 * 
	 * @param listener
	 *          element to be added to the notifier
	 */
	public void register(ConfigChangeListener listener) {
		notifier.addListener(listener);
	}

	/**
	 * Retrieves the configuration properties
	 * 
	 * @return configuration properties
	 */
	public List<SourceConfig> getProperties() {
		return loader.getProperties();
	}

	private void init() {
		if (executor == null) {
			executor = Executors.newScheduledThreadPool(1);
			executor.scheduleWithFixedDelay(loader, 0, 10, TimeUnit.SECONDS);
		}

	}
}
