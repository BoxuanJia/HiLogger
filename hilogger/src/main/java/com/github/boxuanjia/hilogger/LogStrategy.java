package com.github.boxuanjia.hilogger;

/**
 * Determines destination target for the logs such as Disk, HiLog etc.
 *
 * @see HiLogLogStrategy
 * @see DiskLogStrategy
 */
public interface LogStrategy {

    /**
     * This is invoked by HiLogger each time a log message is processed.
     * Interpret this method as last destination of the log in whole pipeline.
     *
     * @param priority is the log level e.g. DEBUG, WARNING
     * @param domain   is the given domain for the log domain.
     * @param tag      is the given tag for the log message.
     * @param message  is the given message for the log message.
     */
    void log(int priority, int domain, String tag, String message);
}
