package com.zjcds.common.base.utils;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageDigestUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String md5 = MessageDigestUtils.generateMessageDigest("123456", MessageDigestUtils.MessageDigestALGO.MD5);
		Assert.assertEquals("e10adc3949ba59abbe56e057f20f883e", md5);
		String sh1 = MessageDigestUtils.generateMessageDigest("123456", MessageDigestUtils.MessageDigestALGO.SHA_1);
		Assert.assertEquals("7c4a8d09ca3762af61e59520943dc26494f8941b", sh1);
		String sh256 = MessageDigestUtils.generateMessageDigest("123456", MessageDigestUtils.MessageDigestALGO.SHA_256);
		Assert.assertEquals("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", sh256);
	}
}
