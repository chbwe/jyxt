package com.ald.finance.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by liang3.zhang on 2018/5/5.
 */
public class MyDateUtils {
    
    /**
     * 将Date转换成字符串格式
     *
     * @param date
     * @return
     */
    public static String toString(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate().toString();
    }
    
    // 判定日期是否在今天之前
    public static boolean isAfterNow(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
    
    public static java.sql.Time strToTime(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        java.util.Date d = null;
        try {
            d = format.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new java.sql.Time(d.getTime());
    }
    
    public static Date toDate(String date) {
        try {
            return DateUtils.parseDate(date, "yyyy-MM-dd");
        } catch (Exception e) {
            return null;
        }
    }
    
    public static LocalDate date2LocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }
    
    public static LocalDateTime time2LocalDateTime(Timestamp time) {
        return LocalDateTime.ofInstant(time.toInstant(), ZoneId.systemDefault());
    }
    
    public static Date localDate2Date(LocalDate date) {
        ZonedDateTime zdt = date.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }
    
    /**
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean gte(LocalTime start, LocalTime end) {
        return end.until(start, ChronoUnit.MINUTES) >= 0 ? true : false;
    }
}
