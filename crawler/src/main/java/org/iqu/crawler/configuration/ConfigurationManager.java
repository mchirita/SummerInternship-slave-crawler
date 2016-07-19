package org.iqu.crawler.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Beniamin Savu
 *
 */
public class ConfigurationManager {

	private final static String PATH = "/iqu/crawler-app-config/crawler.properties";
	private static volatile ConfigurationManager instance = null;
	private ConfigChangeNotifier notifier;
	private ConfigLoader loader;
	private ScheduledExecutorService executor;

	private ConfigurationManager() {
		notifier = new CrawlerChangeNotifier();
		loader = new CrawlerConfigLoader(notifier, PATH);

	}

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

	public void init() {
		if (executor == null) {
			executor = Executors.newScheduledThreadPool(1);
			executor.scheduleWithFixedDelay(loader, 0, 10, TimeUnit.MINUTES);
		}

	}

	public void destroy() {
		executor.shutdown();
		notifier.removeAll();
	}

	public void register(ConfigChangeListener listener) {
		notifier.register(listener);
	}

}
