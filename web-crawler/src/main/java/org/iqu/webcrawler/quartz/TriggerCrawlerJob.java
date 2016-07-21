package org.iqu.webcrawler.quartz;

import org.iqu.crawler.Crawler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 * Triggers the crawler when called by a Quartz Scheduler.
 * 
 * @author Cristi Badoi
 */
public class TriggerCrawlerJob implements Job {

  @Override
  public void execute(JobExecutionContext arg0) throws JobExecutionException {

    try {
      Crawler crawler = (Crawler) arg0.getScheduler().getContext().get("crawlerInstance");

    } catch (SchedulerException e) {
      e.printStackTrace();
    }

  }

}
