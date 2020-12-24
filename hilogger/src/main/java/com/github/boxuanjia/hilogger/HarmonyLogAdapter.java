package com.github.boxuanjia.hilogger;

import static com.github.boxuanjia.hilogger.Utils.checkNotNull;

/**
 * Harmony terminal log output implementation for {@link LogAdapter}.
 * <p>
 * Prints output to HiLog with pretty borders.
 *
 * <pre>
 *  ┌──────────────────────────
 *  │ Method stack history
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Log message
 *  └──────────────────────────
 * </pre>
 */
public class HarmonyLogAdapter implements LogAdapter {

    private final FormatStrategy formatStrategy;

    public HarmonyLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    public HarmonyLogAdapter(FormatStrategy formatStrategy) {
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
