package com.don.easy2readyoedge.core.self;

import android.util.Log;

/**
 * Created by Administrator on 2016/9/1.
 */
public final class SelfLog {
    public static int LOG_LEVEL = 6;
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static Boolean isWrite2ExternalStorage = Boolean.valueOf(false);

    public SelfLog() {
    }

    public static void v(String tag, String msg) {
        if(LOG_LEVEL >= 1) {
            Log.v(tag, msg);
        }

    }

    public static void d(String tag, String msg) {
        if(LOG_LEVEL >= 2) {
            Log.d(tag, msg);
        }

    }

    public static void i(String tag, String msg) {
        if(LOG_LEVEL >= 3) {
            Log.i(tag, msg);
        }

    }

    public static void w(String tag, String msg) {
        if(LOG_LEVEL >= 4) {
            Log.w(tag, msg);
        }

    }

    public static void e(String tag, String msg) {
        if(LOG_LEVEL >= 5) {
            Log.e(tag, msg);
        }

    }

    public static void close() {
        LOG_LEVEL = 0;
    }

    public static boolean isClose() {
        boolean isClose = true;
        if(LOG_LEVEL != 0) {
            isClose = false;
        }

        return isClose;
    }
}
