package com.zjcds.common.base.utils;

import org.junit.Test;

import java.util.Date;

/**
 * created date：2017-09-03
 *
 * @author niezhegang
 */
public class DateUtilsTest {
    @Test
    public void testName() throws Exception {
        String dateStr = "2017-8-1";
        Date date = DateUtils.parse(dateStr);
        System.out.println(date);
    }
}
