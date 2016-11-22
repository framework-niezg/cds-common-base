package com.zjcds.common.base.utils;


import com.zjcds.common.base.utils.codec.HexUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要辅助工具类
 * 创建日期：2015年9月11日
 * @author Administrator
 */
public final class MessageDigestUtils {
	/**默认字符集*/
	public static final String DefaultCharSet = "UTF-8";
	
	public enum MessageDigestALGO{
		
		SHA_1("SHA-1"),
		
		SHA_256("SHA-256"),
		
		MD5("MD5");
		
		private String algo;
		
		public String value() {
			return algo;
		}

		private MessageDigestALGO(String algo){
			this.algo = algo;
		}
	}
	
	/**
	 * 生成消息摘要
	 * @param inputString
	 * @param algo
	 * @return
	 * 创建日期：2015年9月11日
	 * 修改说明：
	 * @author Administrator
	 */
	public static String generateMessageDigest(String inputString,MessageDigestALGO algo) {
		return generateMessageDigest(inputString,DefaultCharSet,algo);
	}
	
	
	/**
	 * 生成消息摘要
	 * @param input
	 * @param algo
	 * @return
	 * 创建日期：2015年9月11日
	 * 修改说明：
	 * @author Administrator
	 */
	public static String generateMessageDigest(byte[] input,MessageDigestALGO algo) {
		try {
			byte[] messageDigest = createDigest(input, algo);
			return HexUtils.encodeToString(messageDigest);
		} catch (Exception e) {
			throw new RuntimeException("生成消息摘要失败：", e);
		}
	}
	
	/**
	 * 生成消息摘要
	 * @param inputString
	 * @param charSet
	 * @param algo
	 * @return
	 * 创建日期：2015年9月11日
	 * 修改说明：
	 * @author Administrator
	 */
	public static String generateMessageDigest(String inputString,String charSet,MessageDigestALGO algo) {
		if(StringUtils.isBlank(inputString))
			throw new IllegalArgumentException("传入的参数不能为空");
		try {
			byte[] messageDigest = createDigest(inputString.getBytes(charSet), algo);
			return HexUtils.encodeToString(messageDigest);
		} catch (Exception e) {
			throw new RuntimeException("生成消息摘要失败：", e);
		}
	}
	
	private static byte[] createDigest(byte[] input, MessageDigestALGO algo)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algo.value());
		md.reset();
		md.update(input);
		return md.digest();
	}
}
