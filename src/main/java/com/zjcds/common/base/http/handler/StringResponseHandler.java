package com.zjcds.common.base.http.handler;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * String值响应处理
 * 创建日期：2015年9月13日
 * @author nzg
 */
public class StringResponseHandler extends AbstractResponseHandler<String> {

	@Override
	public String handleEntity(HttpEntity entity) throws IOException {
		return EntityUtils.toString(entity);
	}

}
