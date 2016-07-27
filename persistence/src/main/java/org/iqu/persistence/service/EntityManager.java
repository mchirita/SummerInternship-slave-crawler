package org.iqu.persistence.service;

import org.iqu.persistence.entities.ParsedDataDTO;

public interface EntityManager {

  public void retrieveData(ParsedDataDTO parsedData);
}
