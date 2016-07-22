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
	 * objects of type T.
	 * 
	 * @param sourceURL
	 *            the link to the RSS feed
	 * @param encoding
	 *            the encoding of the source material
	 * @return parsed material as a List of objects
	 */
	public List<T> readFeed(String sourceURL, String encoding);

}