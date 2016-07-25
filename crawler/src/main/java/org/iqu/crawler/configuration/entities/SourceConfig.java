package org.iqu.crawler.configuration.entities;

import org.iqu.crawler.entities.ParserDataType;

/**
 * 
 * @author BeniaminSavu
 * 
 *         Entity that stores the crawler properties
 *
 */
public class SourceConfig {

  private String parserClassName;
  private String source;
  private ParserDataType dataType;

  public SourceConfig(String parserName, String source, String dataType) {
    this.parserClassName = parserName;
    this.source = source;
    this.dataType = ParserDataType.valueOf(dataType);
  }

  public String getParserName() {
    return parserClassName;
  }

  public String getSource() {
    return source;
  }

  public String getParserClassName() {
    return parserClassName;
  }

  public void setParserClassName(String parserClassName) {
    this.parserClassName = parserClassName;
  }

  public ParserDataType getDataType() {
    return dataType;
  }

  public void setDataType(ParserDataType dataType) {
    this.dataType = dataType;
  }

  public void setSource(String source) {
    this.source = source;
  }

}
