package com.zjcds.common.base.utils;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * PortScanUtils Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 13, 2015</pre>
 */
public class PortScannerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: scanPort(String networkSection, int startIp, int endIp)
     */
//    @Test
//    public void testScanPort() throws Exception {
//        List<Integer> ret = new PortScanner().scanPort("192.168.1.3");
//        Object[] arrs = ret.toArray();
//        Arrays.sort(arrs);
//        System.out.println(Arrays.toString(arrs));
//    }

    @Test
    public void testDate() throws Exception {
        Date date = new Date(1448508404929L);

        System.out.println(date);


    }


}
