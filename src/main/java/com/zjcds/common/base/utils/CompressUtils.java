package com.zjcds.common.base.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * @author zhegang.nie
 * @date 2015/11/26 0026
 */
public class CompressUtils {
    private final static String TextCharSet = "UTF-8";
    /**
     * 使用gzip压缩
     * @param in 要压缩的流数据
     * @param out 压缩后的输出流
     */
    public static void compressByGzip(InputStream in,OutputStream out) throws IOException{
        GzipCompressorOutputStream gzOut = null;
        try {
            gzOut = new GzipCompressorOutputStream(out);
            IOUtils.copy(in, gzOut);
        }
        finally{
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(gzOut);
        }
    }

    /**
     * @param srcText 压缩前的文本
     * @return 压缩后的文本
     * @throws IOException
     */
    public static String compressByGzip(String srcText) throws IOException {
        if(StringUtils.isBlank(srcText))
            return srcText;
        ByteArrayInputStream in = new ByteArrayInputStream(srcText.getBytes(TextCharSet));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        compressByGzip(in,out);
        byte[] bytes = out.toByteArray();
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 使用gzip解压
     * @param in gzip格式的压缩流
     * @param out 解压后的输出流
     */
    public static void deCompressByGzip(InputStream in,OutputStream out) throws IOException{
        GzipCompressorInputStream gin = null;
        try {
            gin = new GzipCompressorInputStream(in);
            IOUtils.copy(gin, out);
        }
        finally {
            IOUtils.closeQuietly(gin);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 需要与 compressByGzip配合使用
     * @param srcCompressText 原始压缩文本
     * @return  解压后的文本
     * @throws IOException
     */
    public static String deCompressByGzip(String srcCompressText) throws IOException{
        if(StringUtils.isBlank(srcCompressText))
            return srcCompressText;
        byte[] bytes = Base64.decodeBase64(srcCompressText);
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        deCompressByGzip(in,out);
        bytes = out.toByteArray();
        return new String(bytes,TextCharSet);
    }

}
