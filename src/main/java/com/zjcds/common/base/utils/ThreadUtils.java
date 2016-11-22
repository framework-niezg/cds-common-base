package com.zjcds.common.base.utils;

/**
 * 线程工具类 创建日期：2014年5月28日
 * 
 * @author niezhegang
 */
public class ThreadUtils {
	
	/**
	 * 当前线程安静地（不抛出异常）暂停设定的毫秒数
	 * @param millis
	 * 创建日期：2014年5月28日
	 * 修改说明：
	 * @author niezhegang
	 */
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} 
		catch (InterruptedException e) {
		}
	}
}
