package org.iqu.crawler.configuration.entities;

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
  private String type;

  public SourceConfig(String parserName, String source, String type) {
    this.parserClassName = parserName;
    this.source = source;
    this.setType(type);
  }

  public String getParserName() {
    return parserClassName;
  }

  public String getSource() {
    return source;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "SourceConfiguration [parserName=" + parserClassName + ", source=" + source + "]\n";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((parserClassName == null) ? 0 : parserClassName.hashCode());
    result = prime * result + ((source == null) ? 0 : source.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SourceConfig other = (SourceConfig) obj;
    if (parserClassName == null) {
      if (other.parserClassName != null)
        return false;
    } else if (!parserClassName.equals(other.parserClassName))
      return false;
    if (source == null) {
      if (other.source != null)
        return false;
    } else if (!source.equals(other.source))
      return false;
    return true;
  }

}
