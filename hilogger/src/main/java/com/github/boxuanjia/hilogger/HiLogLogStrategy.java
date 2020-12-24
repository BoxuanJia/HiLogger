package com.github.boxuanjia.hilogger;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import static com.github.boxuanjia.hilogger.HiLogger.*;
import static com.github.boxuanjia.hilogger.Utils.checkNotNull;

/**
 * HiLog implementation for {@link LogStrategy}
 * <p>
 * This simply prints out all logs to HiLog by using standard {@link HiLog} class.
 */
public class HiLogLogStrategy implements LogStrategy {

    private static final String DEFAULT_TAG = "NO_TAG";

    @Override
    public void log(int priority, int domain, String tag, String message) {
        checkNotNull(message);
        if (tag == null) {
            tag = DEFAULT_TAG;
        }
        HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, domain, tag);
        switch (priority) {
            case DEBUG:
                HiLog.debug(label, message);
                break;
            case INFO:
                HiLog.info(label, message);
                break;
            case WARN:
                HiLog.warn(label, message);
                break;
            case ERROR:
                HiLog.error(label, message);
                break;
            case FATAL:
                HiLog.fatal(label, message);
                break;
        }
    }
}
