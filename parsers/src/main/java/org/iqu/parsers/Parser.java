package org.iqu.parsers;

import java.util.List;

import org.iqu.parsers.entities.SourceModel;

/**
 * Defines an information parser.
 * 
 * @author Cristi Badoi
 */
public interface Parser<T> {

  /**
   * Parses the RSS at the given URL and returns the content as a List of
   * objects of type T.
   * 
   * @param sourceURL
   *          the link to the RSS feed
   * @param encoding
   *          the encoding of the source material
   * @return parsed material as a List of objects
   */
  List<T> readFeed(String sourceURL, String encoding);

  /**
   * Provides a Source object containing the details of the last parsed source.
   * 
   * @return object containing details of last read source
   */
  SourceModel getSource();

}