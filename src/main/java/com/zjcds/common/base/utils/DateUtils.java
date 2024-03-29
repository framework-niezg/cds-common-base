package com.zjcds.common.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * created dateStr：2017-09-02
 * @author niezhegang
 */
public abstract class DateUtils {

    private static String DefaultDateTimePattern = "yyyy-MM-dd HH:mm:ss";

    private static String DefaultDatePattern = "yyyy-MM-dd";


    /**
     * 使用默认pattern解析日期格式
     * @param dateStr
     * @return
     */
    public static Date parse(String dateStr) {
       return parse(dateStr,DefaultDateTimePattern,DefaultDatePattern);
    }

    /**
     * 根据传入的pattern解析出日期对象
     * @param dateStr
     * @param datePatterns
     * @return
     */
    public static Date parse(String dateStr,String... datePatterns ) {
        try {
            if(StringUtils.isBlank(dateStr))
                return null;
            else {
                if(ArrayUtils.isEmpty(datePatterns)) {
                    datePatterns = new String[]{DefaultDateTimePattern,DefaultDatePattern};
                }
                Date date = null;
                dateStr = StringUtils.trim(dateStr);
                date = org.apache.commons.lang3.time.DateUtils.parseDate(dateStr,datePatterns);
                return date;
            }
        }
        catch (ParseException e){
            throw new IllegalArgumentException("传入的日期值格式不正确"+ dateStr,e);
        }
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
