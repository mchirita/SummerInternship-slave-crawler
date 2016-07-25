package org.iqu.webcrawler.quartz;

import org.apache.log4j.Logger;
import org.iqu.crawler.Crawler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 * Triggers the crawler to parse feeds when called by a Quartz Scheduler.
 * 
 * @author Cristi Badoi
 */
public class CrawlerJob implements Job {

  private static final Logger LOGGER = Logger.getLogger(CrawlerJob.class);

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {

    try {

      Crawler crawler = (Crawler) arg0.getScheduler().getContext().get("crawlerInstance");
      crawler.runParsers();

    } catch (SchedulerException e) {
      LOGGER.error("Cannot retrieve Crawler instance from Scheduler context", e);
    }

  }

}
