package com.zjcds.common.base.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/** 
* CompressUtils Tester. 
* 
* @author <Authors name> 
* @since <pre>ʮһ�� 26, 2015</pre> 
* @version 1.0 
*/ 
public class CompressUtilsTest { 

    /**
    *
    * Method: compressByGzip(InputStream in, OutputStream out)
    *
    */
    @Test
    public void testCompressByGzip() throws Exception {
        String text = "测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试";
        byte[] src = text.getBytes("UTF-8");
        System.out.println("原始大小："+src.length);
        ByteArrayInputStream is = new ByteArrayInputStream(src);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CompressUtils.compressByGzip(is,out);
        byte[] dest = out.toByteArray();
        System.out.println("压缩后大小：" + dest.length);

        //解压
        ByteArrayInputStream deis = new ByteArrayInputStream(dest);
        ByteArrayOutputStream deout = new ByteArrayOutputStream();
        CompressUtils.deCompressByGzip(deis, deout);
        Assert.assertEquals(text,new String(deout.toByteArray(),"UTF-8"));
    }

    @Test
    public void testTextCompressByGzip() throws Exception {
        String text = "测试测262626试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试26262测65656试测试测试6426426测试测试测试测g44646试测试测6565试测试测试测试n4n443w6n74测试测试854nj4n4n测试测试测试测测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试测试测试n4n4n74测试测试854nj4n4n测试测试测试测试n4n74n4n测试测65656试测试测试测试测试测试测试测试测6565试测试";
        System.out.println("原始大小："+text.length());
        String retText = CompressUtils.compressByGzip(text);
        System.out.println("压缩后大小：" + retText.length());

        //解压
        Assert.assertEquals(text,CompressUtils.deCompressByGzip(retText));
    }



} 
