package org.iqu.crawler.configuration.exception;

/**
 * 
 * @author Beniamin Savu
 * 
 *         Signals that a loading exception of some sort has occurred. This
 *         class is the general class of exceptions produced by failed or
 *         interrupted loading operations.
 *
 */
public class ConfigLoaderException extends RuntimeException {

  public ConfigLoaderException(String message) {
    super(message);
  }

  public ConfigLoaderException(String message, Throwable e) {
    super(message, e);
  }

}
