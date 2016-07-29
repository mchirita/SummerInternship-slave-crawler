package org.iqu.persistence.service;

public class Main {
  public static void main(String[] args) {
    NewsDAO dao = DAOFactory.getNewsDAO();
    dao.retrieveNews(1469777576, 0, "Stiri", null, 0, null);
  }
}
