package org.iqu.parsers;

import java.util.List;

import org.iqu.parsers.entities.NewsArticle;

/**
 * Defines a NewsParser.
 * 
 * @author Cristi Badoi
 */
public interface NewsParser {

  /**
   * Parses the RSS at the given URL and returns the content as a List of
   * NewsArticle objects.
   * 
   * @param sourceURL
   *          the link to the RSS feed
   * @param encoding
   *          the encoding of the source material
   * @return parsed material as a List of NewsArticle objects
   */
  public List<NewsArticle> readFeed(String sourceURL, String encoding);

}