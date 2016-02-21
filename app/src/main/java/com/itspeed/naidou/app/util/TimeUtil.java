package com.itspeed.naidou.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jafir on 15/11/18.
 * 时间工具类  毫秒到 date时间
 * 用时间戳转化为date时间
 */
public class TimeUtil {
    public static String msToDate(String ms){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //这里要乘以1000
        String hms = formatter.format(new Date(Long.valueOf(ms)* 1000));
        return hms;
    }
    public static String currentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String hms = formatter.format(new Date(System.currentTimeMillis()));
        return hms;
    }
}
