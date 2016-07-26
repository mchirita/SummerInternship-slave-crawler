package org.iqu.webcrawler.entities;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Sources {
  private Set<Source> sources = new HashSet<Source>();

  public Sources() {
  }

  public Sources(Set<Source> sources) {
    super();
    this.sources = sources;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((sources == null) ? 0 : sources.hashCode());
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
    Sources other = (Sources) obj;
    if (sources == null) {
      if (other.sources != null)
        return false;
    } else if (!sources.equals(other.sources))
      return false;
    return true;
  }

  @XmlElement
  public Set<Source> getSources() {
    return sources;
  }

  public void setSources(Set<Source> sources) {
    this.sources = sources;
  }

  public boolean addSource(Source source) {
    return sources.add(source);
  }

  public boolean isEmpty() {
    return sources.isEmpty();
  }

  public int size() {
    return sources.size();
  }

  public void add(Source source) {
    sources.add(source);

  }

}
