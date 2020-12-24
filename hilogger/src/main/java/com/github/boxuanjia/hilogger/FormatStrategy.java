package com.github.boxuanjia.hilogger;

/**
 * Used to determine how messages should be printed or saved.
 *
 * @see PrettyFormatStrategy
 * @see CsvFormatStrategy
 */
public interface FormatStrategy {

    void log(int priority, int domain, String tag, String message);
}
