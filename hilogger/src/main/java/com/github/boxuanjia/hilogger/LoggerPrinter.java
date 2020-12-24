package com.github.boxuanjia.hilogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.boxuanjia.hilogger.HiLogger.*;
import static com.github.boxuanjia.hilogger.Utils.checkNotNull;

class LoggerPrinter implements Printer {

    /**
     * It is used for org.json pretty print
     */
    private static final int JSON_INDENT = 2;

    /**
     * Provides one-time used domain and tag for the log message
     */
    private final ThreadLocal<Map<Integer, String>> localTag = new ThreadLocal<>();

    private final List<LogAdapter> logAdapters = new ArrayList<>();

    @Override
    public Printer t(int domain, String tag) {
        if (tag != null) {
            Map<Integer, String> map = new HashMap<>();
            map.put(domain, tag);
            localTag.set(map);
        }
        return this;
    }

    @Override
    public void d(String message, Object... args) {
        log(DEBUG, null, message, args);
    }

    @Override
    public void d(Object object) {
        log(DEBUG, null, Utils.toString(object));
    }

    @Override
    public void e(String message, Object... args) {
        e(null, message, args);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        log(ERROR, throwable, message, args);
    }

    @Override
    public void w(String message, Object... args) {
        log(WARN, null, message, args);
    }

    @Override
    public void i(String message, Object... args) {
        log(INFO, null, message, args);
    }

    @Override
    public void wtf(String message, Object... args) {
        log(FATAL, null, message, args);
    }

    @Override
    public void json(String json) {
        if (Utils.isEmpty(json)) {
            d("Empty/Null org.json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
                return;
            }
            e("Invalid Json");
        } catch (JSONException e) {
            e("Invalid Json");
        }
    }

    @Override
    public synchronized void log(int priority,
                                 int domain,
                                 String tag,
                                 String message,
                                 Throwable throwable) {
        if (throwable != null && message != null) {
            message += " : " + Utils.getStackTraceString(throwable);
        }
        if (throwable != null && message == null) {
            message = Utils.getStackTraceString(throwable);
        }
        if (Utils.isEmpty(message)) {
            message = "Empty/NULL log message";
        }

        for (LogAdapter adapter : logAdapters) {
            if (adapter.isLoggable(priority, domain, tag)) {
                adapter.log(priority, domain, tag, message);
            }
        }
    }

    @Override
    public void clearLogAdapters() {
        logAdapters.clear();
    }

    @Override
    public void addAdapter(LogAdapter adapter) {
        logAdapters.add(checkNotNull(adapter));
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void log(int priority,
                                  Throwable throwable,
                                  String msg,
                                  Object... args) {
        checkNotNull(msg);

        Map<Integer, String> map = get();
        int domain = 0;
        String tag = "";
        if (map != null) {
            for (Integer integer : map.keySet()) {
                domain = integer;
                tag = map.get(domain);
            }
        }
        String message = createMessage(msg, args);
        log(priority, domain, tag, message, throwable);
    }

    /**
     * @return the appropriate tag based on local or global
     */
    private Map<Integer, String> get() {
        Map<Integer, String> tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return null;
    }

    private String createMessage(String message, Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }
}
