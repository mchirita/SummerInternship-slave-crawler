package org.iqu.crawler.configuration;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.iqu.crawler.configuration.ConfigChangeListener;
import org.iqu.crawler.configuration.ConfigurationManager;
import org.iqu.crawler.configuration.entities.SourceConfig;

public class CrawlerConfiguration implements ConfigChangeListener {

  private List<SourceConfig> configs;
  private ReadLock readLock;
  private WriteLock writeLock;

  public CrawlerConfiguration() {
    configs = ConfigurationManager.getInstance().getProperties();
    ConfigurationManager.getInstance().register(this);
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    readLock = lock.readLock();
    writeLock = lock.writeLock();
  }

  @Override
  public void onConfigChange(List<SourceConfig> properties) {
    writeLock.lock();
    try {
      configs = properties;
    } finally {
      writeLock.unlock();
    }
  }

  public List<SourceConfig> getConfigs() {
    readLock.lock();
    try {
      return configs;
    } finally {
      readLock.unlock();
    }
  }

}
