package com.databps.bigdaf.chiwen.audit;

import com.databps.bigdaf.chiwen.util.StringUtil;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * BaseAuditProvider
 *
 * @author lgc
 * @create 2017-07-25 下午4:42
 */
public abstract class BaseAuditProvider implements AuditProvider {

  private static final Log LOG = LogFactory.getLog(BaseAuditProvider.class);


  private int mLogFailureReportMinIntervalInMs = 60 * 1000;
  private static final String AUDIT_LOG_FAILURE_REPORT_MIN_INTERVAL_PROP = "xasecure.audit.log.failure.report.min.interval.ms";

  private AtomicLong mFailedLogLastReportTime = new AtomicLong(0);
  private AtomicLong mFailedLogCountSinceLastReport = new AtomicLong(0);
  private AtomicLong mFailedLogCountLifeTime = new AtomicLong(0);

  public void logFailedEvent(AuthzAuditEvent event) {
    logFailedEvent(event, null);
  }

  public void logFailedEvent(AuthzAuditEvent event, Throwable excp) {
    long now = System.currentTimeMillis();

    long timeSinceLastReport = now - mFailedLogLastReportTime.get();
    long countSinceLastReport = mFailedLogCountSinceLastReport.incrementAndGet();
    long countLifeTime = mFailedLogCountLifeTime.incrementAndGet();

    if (timeSinceLastReport >= mLogFailureReportMinIntervalInMs) {
      mFailedLogLastReportTime.set(now);
      mFailedLogCountSinceLastReport.set(0);

      if (excp != null) {
        LOG.warn("failed to log audit event: " + StringUtil.logToJsonString(event), excp);
      } else {
        LOG.warn("failed to log audit event: " + StringUtil.logToJsonString(event));
      }

      if (countLifeTime > 1) { // no stats to print for the 1st failure
        LOG.warn("Log failure count: " + countSinceLastReport + " in past " + formatIntervalForLog(
            timeSinceLastReport) + "; " + countLifeTime + " during process lifetime");
      }
    }
  }

  @Override
  public void init(Properties props) {
    LOG.info("BaseAuditProvider.init()");

    mLogFailureReportMinIntervalInMs = getIntProperty(props,
        AUDIT_LOG_FAILURE_REPORT_MIN_INTERVAL_PROP, 60 * 1000);
  }

  public static int getIntProperty(Properties props, String propName, int defValue) {
    int ret = defValue;

    if (props != null && propName != null) {
      String val = props.getProperty(propName);

      if (val != null) {
        try {
          ret = Integer.parseInt(val);
        } catch (NumberFormatException excp) {
          ret = defValue;
        }
      }
    }

    return ret;
  }

  public String formatIntervalForLog(long timeInMs) {
    long hours = timeInMs / (60 * 60 * 1000);
    long minutes = (timeInMs / (60 * 1000)) % 60;
    long seconds = (timeInMs % (60 * 1000)) / 1000;
    long mSeconds = (timeInMs % (1000));

    if (hours > 0) {
      return String.format("%02d:%02d:%02d.%03d hours", hours, minutes,
          seconds, mSeconds);
    } else if (minutes > 0) {
      return String.format("%02d:%02d.%03d minutes", minutes, seconds,
          mSeconds);
    } else if (seconds > 0) {
      return String.format("%02d.%03d seconds", seconds, mSeconds);
    } else {
      return String.format("%03d milli-seconds", mSeconds);
    }
  }
}