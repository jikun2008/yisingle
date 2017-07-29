package com.yisingle.webapp.utils;

import com.sun.jmx.snmp.Timestamp;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by jikun on 17/7/29.
 */
public class TimeUtils {

    public static Long getTodayStartTime() {
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数

        return new Timestamp(zero).getSysUpTime();
    }

    public static Long getTodayEndTime() {
        long current = System.currentTimeMillis();//当前时间毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数

        return new Timestamp(twelve).getSysUpTime();
    }

}
