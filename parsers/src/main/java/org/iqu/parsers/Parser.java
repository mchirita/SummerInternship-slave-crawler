package org.iqu.parsers;

import java.util.List;

/**
 * Defines an information parser.
 * 
 * @author Cristi Badoi
 */
public interface Parser<T> {

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
  public List<T> readFeed(String sourceURL, String encoding);

}