package com.databps.bigdaf.chiwen.audit;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AsyncAuditProvider
 *
 * @author lgc
 * @create 2017-07-26 上午11:39
 */
public class AsyncAuditProvider extends MultiDestAuditProvider implements Runnable {

  private static final Log LOG = LogFactory.getLog(AsyncAuditProvider.class);

  private static int sThreadCount = 0;

  private BlockingQueue<AuthzAuditEvent> mQueue = null;
  private Thread mThread = null;
  private String mName = null;
  private int mMaxQueueSize = 10 * 1024;
  private int mMaxFlushInterval = 5000; // 5 seconds
  // Summary of logs handled
  private AtomicLong lifeTimeInLogCount = new AtomicLong(0);
  private AtomicLong lifeTimeOutLogCount = new AtomicLong(0);
  private AtomicLong lifeTimeDropCount = new AtomicLong(0);
  private AtomicLong intervalInLogCount = new AtomicLong(0);
  private AtomicLong intervalOutLogCount = new AtomicLong(0);
  private AtomicLong intervalDropCount = new AtomicLong(0);
  private long lastIntervalLogTime = System.currentTimeMillis();
  private int intervalLogDurationMS = 60000;
  private long lastFlushTime = System.currentTimeMillis();

  public AsyncAuditProvider(String name, int maxQueueSize, int maxFlushInterval) {
    LOG.info("AsyncAuditProvider(" + name + "): creating..");

    if (maxQueueSize < 1) {
      LOG.warn("AsyncAuditProvider(" + name + "): invalid maxQueueSize=" + maxQueueSize
          + ". will use default " + mMaxQueueSize);

      maxQueueSize = mMaxQueueSize;
    }

    mName = name;
    mMaxQueueSize = maxQueueSize;
    mMaxFlushInterval = maxFlushInterval;

    mQueue = new ArrayBlockingQueue<AuthzAuditEvent>(mMaxQueueSize);
  }

  @Override
  public void init(Properties props) {
    LOG.info("AsyncAuditProvider(" + mName + ").init()");

    super.init(props);
  }

  @Override
  public void start() {

    mThread = new Thread(this, "AsyncAuditProvider" + (++sThreadCount));

    mThread.setDaemon(true);
    mThread.start();

    super.start();
  }

  @Override
  public void run() {
    LOG.info("==> AsyncAuditProvider.run()");
    while (true) {
      AuthzAuditEvent event = null;
      try {
        event = dequeueEvent();

        if (event != null) {
          super.log(event);
        } else {
          lastFlushTime = System.currentTimeMillis();
          flush();
        }
      } catch (InterruptedException excp) {
        LOG.info("==> AsyncAuditProvider.run():exiting");
        break;
      } catch (Exception excp) {
        logFailedEvent(event, excp);
      }
    }

    try {
      flush();
    } catch (Exception excp) {
      LOG.error("AsyncAuditProvider.run()", excp);
    }

//    LOG.info("<== AsyncAuditProvider.run() end");
  }

  public AsyncAuditProvider(String name, int maxQueueSize, int maxFlushInterval,
      AuditProvider provider) {

    this(name, maxQueueSize, maxFlushInterval);

    addAuditProvider(provider);
  }

  @Override
  public boolean log(AuthzAuditEvent event) {
    LOG.debug("AsyncAuditProvider.logEvent(AuthzAuditEvent)");

    queueEvent(event);
    return true;
  }

  private void queueEvent(AuthzAuditEvent event) {
    // Increase counts
    lifeTimeInLogCount.incrementAndGet();
    intervalInLogCount.incrementAndGet();

    if (!mQueue.offer(event)) {
      lifeTimeDropCount.incrementAndGet();
      intervalDropCount.incrementAndGet();
    }
  }

  private AuthzAuditEvent dequeueEvent() throws InterruptedException {
    AuthzAuditEvent ret = mQueue.poll();

    while(ret == null) {
      logSummaryIfRequired();

      if (mMaxFlushInterval > 0 ) {
        long timeTillNextFlush = getTimeTillNextFlush();

        if (timeTillNextFlush <= 0) {
          break; // force flush
        }

        ret = mQueue.poll(timeTillNextFlush, TimeUnit.MILLISECONDS);
      } else {
        // Let's wake up for summary logging
        long waitTime = intervalLogDurationMS - (System.currentTimeMillis() - lastIntervalLogTime);
        waitTime = waitTime <= 0 ? intervalLogDurationMS : waitTime;

        ret = mQueue.poll(waitTime, TimeUnit.MILLISECONDS);
      }
    }

    if(ret != null) {
      lifeTimeOutLogCount.incrementAndGet();
      intervalOutLogCount.incrementAndGet();
    }

    logSummaryIfRequired();

    return ret;
  }

  private void logSummaryIfRequired() {
    long intervalSinceLastLog = System.currentTimeMillis() - lastIntervalLogTime;

    if (intervalSinceLastLog > intervalLogDurationMS) {
      if (intervalInLogCount.get() > 0 || intervalOutLogCount.get() > 0 ) {
        long queueSize = mQueue.size();

        LOG.info("AsyncAuditProvider-stats:" + mName + ": past " + formatIntervalForLog(intervalSinceLastLog)
            + ": inLogs=" + intervalInLogCount.get()
            + ", outLogs=" + intervalOutLogCount.get()
            + ", dropped=" + intervalDropCount.get()
            + ", currentQueueSize=" + queueSize);

        LOG.info("AsyncAuditProvider-stats:" + mName + ": process lifetime"
            + ": inLogs=" + lifeTimeInLogCount.get()
            + ", outLogs=" + lifeTimeOutLogCount.get()
            + ", dropped=" + lifeTimeDropCount.get());
      }

      lastIntervalLogTime = System.currentTimeMillis();
      intervalInLogCount.set(0);
      intervalOutLogCount.set(0);
      intervalDropCount.set(0);
    }
  }

  public int getIntervalLogDurationMS() {
    return intervalLogDurationMS;
  }

  public void setIntervalLogDurationMS(int intervalLogDurationMS) {
    this.intervalLogDurationMS = intervalLogDurationMS;
  }

  private long getTimeTillNextFlush() {
    long timeTillNextFlush = mMaxFlushInterval;

    if (mMaxFlushInterval > 0) {

      if (lastFlushTime != 0) {
        long timeSinceLastFlush = System.currentTimeMillis()
            - lastFlushTime;

        if (timeSinceLastFlush >= mMaxFlushInterval) {
          timeTillNextFlush = 0;
        } else {
          timeTillNextFlush = mMaxFlushInterval - timeSinceLastFlush;
        }
      }
    }

    return timeTillNextFlush;
  }
}