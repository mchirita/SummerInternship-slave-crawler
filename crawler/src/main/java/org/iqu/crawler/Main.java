package org.iqu.crawler;

import org.iqu.crawler.configuration.CrawlerConfiguration;

public class Main {

  public static void main(String[] args) {
    Crawler crawler = new Crawler(new CrawlerConfiguration());
    crawler.runParsers();
  }

}
