package com.github.boxuanjia.hilogger;

import ohos.app.Context;

import static com.github.boxuanjia.hilogger.Utils.checkNotNull;

/**
 * This is used to saves log messages to the disk.
 * By default it uses {@link CsvFormatStrategy} to translates text message into CSV format.
 */
public class DiskLogAdapter implements LogAdapter {

    private final FormatStrategy formatStrategy;

    public DiskLogAdapter(Context context) {
        formatStrategy = CsvFormatStrategy.newBuilder(context).build();
    }

    public DiskLogAdapter(FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, int domain, String tag) {
        return true;
    }

    @Override
    public void log(int priority, int domain, String tag, String message) {
        formatStrategy.log(priority, domain, tag, message);
    }
}
