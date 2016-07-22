package org.iqu.crawler;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.log4j.Logger;
import org.iqu.crawler.configuration.CrawlerConfiguration;
import org.iqu.crawler.configuration.entities.SourceConfig;
import org.iqu.parsers.Parser;
import org.iqu.parsers.entities.Event;
import org.iqu.parsers.entities.NewsArticle;

/**
 * Parses online news and event information and forwards it.
 * 
 * @author Cristi Badoi
 */
public class Crawler {

  private static final Logger LOGGER = Logger.getLogger(Crawler.class);
  private CrawlerConfiguration configuration;

  public Crawler(CrawlerConfiguration configuration) {
    this.configuration = configuration;
  }

  /**
   * Iterates through every source configuration and extracts the sourceURL and
   * corresponding parser. Extracts the proper data type to hold the parsed
   * information (e.g. NewsArticle, Events) from the parser's class information
   * using reflection.
   */
  public void runParsers() {

    for (SourceConfig sourceConfig : configuration.getConfigs()) {
      try {

        Class<?> parserClass = Class.forName(sourceConfig.getParserName());
        ParameterizedType type = (ParameterizedType) parserClass.getGenericInterfaces()[0];
        Class<?> dataClass = (Class<?>) type.getActualTypeArguments()[0];

        if (dataClass == NewsArticle.class) {

          Parser<NewsArticle> parser = (Parser<NewsArticle>) Class.forName(sourceConfig.getParserName()).newInstance();
          List<NewsArticle> news = parser.readFeed(sourceConfig.getSource(), "UTF-8");
          // TODO: pass News to DAO Manager (unimplemented at this moment)

        } else if (dataClass == Event.class) {

          Parser<Event> parser = (Parser<Event>) Class.forName(sourceConfig.getParserName()).newInstance();
          List<Event> news = parser.readFeed(sourceConfig.getSource(), "UTF-8");
          // TODO: pass Events to DAO Manager (unimplemented at this moment)

        } else {
          LOGGER.info("Not programmed to handle " + dataClass + " data type.");
        }

      } catch (ClassNotFoundException e) {
        LOGGER.error("Can't find the parser class!", e);
      } catch (InstantiationException e) {
        LOGGER.error("Parser class cannot be instantiated!", e);
      } catch (IllegalAccessException e) {
        LOGGER.error("Can't access parser class constructor!", e);
      }
    }
  }

}
