package org.iqu.crawler;

import org.apache.log4j.Logger;
import org.iqu.crawler.configuration.CrawlerConfiguration;
import org.iqu.crawler.configuration.entities.SourceConfig;
import org.iqu.crawler.entities.DataType;
import org.iqu.crawler.entities.ParsedData;
import org.iqu.parsers.Parser;
import org.iqu.parsers.entities.EventDTO;
import org.iqu.parsers.entities.NewsArticleDTO;

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
   * corresponding parser. Wraps all parsed data into a ParsedData object and
   * forwards it to a DAO Manager.
   */
  public void runParsers() {
    for (SourceConfig sourceConfig : configuration.getConfigs()) {
      ParsedData parsedData = new ParsedData();
      try {

        if (sourceConfig.getDataType() == DataType.NEWS) {
          Parser<NewsArticleDTO> parser = (Parser<NewsArticleDTO>) Class.forName(sourceConfig.getParserName()).newInstance();
          parsedData.setNews(parser.readFeed(sourceConfig.getSource(), "UTF-8"));
          parsedData.setSource(parser.getSource());

        } else if (sourceConfig.getDataType() == DataType.EVENTS) {

          Parser<EventDTO> parser = (Parser<EventDTO>) Class.forName(sourceConfig.getParserName()).newInstance();
          parsedData.setEvents(parser.readFeed(sourceConfig.getSource(), "UTF-8"));
          parsedData.setSource(parser.getSource());

        } else {
          LOGGER.info("Unable to handle " + sourceConfig.getDataType() + " data type.");
        }

        // TODO: forward the ParsedData object to the DAO Manager

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
