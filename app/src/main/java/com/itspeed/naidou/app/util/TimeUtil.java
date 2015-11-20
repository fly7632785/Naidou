package com.itspeed.naidou.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jafir on 15/11/18.
 */
public class TimeUtil {
    public static String msToDate(String ms){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String hms = formatter.format(new Date(Long.valueOf(ms )* 1000));
        return hms;
    }
}
