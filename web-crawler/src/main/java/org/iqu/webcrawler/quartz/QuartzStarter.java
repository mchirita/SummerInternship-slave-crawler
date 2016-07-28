package org.iqu.webcrawler.quartz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.iqu.crawler.Crawler;
import org.iqu.crawler.configuration.CrawlerConfiguration;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Creates a Crawler instance and starts a Quartz Scheduler on servlet
 * initialization that periodically triggers the crawler to parse the feeds and
 * forward the information. Shuts down the Scheduler on servlet destruction.
 * 
 * @author Cristi Badoi
 */
@WebListener
public class QuartzStarter implements ServletContextListener {

  private static final Logger LOGGER = Logger.getLogger(QuartzStarter.class);

  Scheduler scheduler;
  private static final int MINUTE_INTERVAL = 10;

  @Override
  public void contextInitialized(ServletContextEvent arg0) {
    try {
      Crawler crawler = new Crawler(new CrawlerConfiguration());

      scheduler = new StdSchedulerFactory().getScheduler();
      scheduler.getContext().put("crawlerInstance", crawler);

      JobDetail job = JobBuilder.newJob(CrawlerJob.class).withIdentity("crawler-job", "slave-app").build();
      Trigger trigger = TriggerBuilder.newTrigger().withIdentity("crawler-trigger", "slave-app")
          .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(MINUTE_INTERVAL)).build();

      scheduler.scheduleJob(job, trigger);
      scheduler.start();

    } catch (SchedulerException e) {
      LOGGER.error("Scheduler error!", e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent arg0) {
    try {

      scheduler.shutdown();

    } catch (SchedulerException e) {
      LOGGER.error("Scheduler error!", e);
    }
  }

}
