package org.iqu.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.iqu.crawler.configuration.CrawlerConfiguration;
import org.iqu.crawler.configuration.entities.SourceConfig;
import org.iqu.parsers.Parser;

public class Crawler<T> {

  private static final Logger LOGGER = Logger.getLogger(Crawler.class);

  private CrawlerConfiguration configuration;

  public Crawler(CrawlerConfiguration configuration) {
    this.configuration = configuration;
  }

  public List<T> runParsers() {
    List<T> result = new ArrayList<T>();
    for (SourceConfig sourceConfig : configuration.getConfigs()) {
      if (sourceConfig.getType().getClass() == )
      try {
        Parser<T> parser = (Parser<T>) Class.forName(sourceConfig.getParserName()).newInstance();
        
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    return result;
  }

}
