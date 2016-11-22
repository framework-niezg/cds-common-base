package com.zjcds.common.base.http.handler;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;

import java.io.IOException;

/**
 * 字节数据响应处理器
 * 创建日期：2015年9月13日
 * @author nzg
 */
public class ByteArrayResponseHandler extends AbstractResponseHandler<byte[]> {
	
	/**
	 * @see org.apache.http.impl.client.AbstractResponseHandler#handleEntity(org.apache.http.HttpEntity)
	 * 创建日期：2015年9月13日
	 * 修改说明：
	 * @author nzg
	 */
	@Override
	public byte[] handleEntity(HttpEntity entity) throws IOException {
		if(entity.getContentLength() > 0)
			return IOUtils.toByteArray(entity.getContent());
		else
			return null;
	}
	
}
