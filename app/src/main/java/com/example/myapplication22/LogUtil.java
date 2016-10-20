package com.example.myapplication22;

/**
 * Created by 51099 on 2016/10/20.
 */

import android.util.Log;

/**
 * 自定义日志类
 * 功能简介：在项目中自由控制日志打印，可以指定日志打印级别，可以开关日志打印
 */
public class LogUtil {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int LEVEL = VERBOSE;

    public static void v(String tag, String msg)
    {
        if(LEVEL <= VERBOSE)
        {
            Log.v(tag,msg);
        }
    }

    public static void d(String tag, String msg)
    {
        if(LEVEL <= DEBUG)
        {
            Log.d(tag,msg);
        }
    }

    public static void i(String tag, String msg)
    {
        if(LEVEL <= INFO)
        {
            Log.i(tag,msg);
        }
    }

    public static void w(String tag, String msg)
    {
        if(LEVEL <= WARN)
        {
            Log.w(tag,msg);
        }
    }

    public static void e(String tag, String msg)
    {
        if(LEVEL <= ERROR)
        {
            Log.e(tag,msg);
        }
    }
}
