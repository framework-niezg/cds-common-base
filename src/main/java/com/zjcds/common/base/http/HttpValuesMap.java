package com.zjcds.common.base.http;

import org.apache.commons.lang3.ArrayUtils;

import java.util.LinkedHashMap;

/**
 * 为了query和form参数
 * 一个简单的多值Map
 * 创建日期：2015年9月15日
 * @author Administrator
 */
public abstract class HttpValuesMap<T> extends LinkedHashMap<String, T[]> {

	public static HttpValuesMap<String> ofStrings() {
		return new HttpValuesMap<String>() {
			@Override
			protected String[] createNewArray() {
				return new String[1];
			}
		};
	}

	public static HttpValuesMap<Object> ofObjects() {
		return new HttpValuesMap<Object>() {
			@Override
			protected Object[] createNewArray() {
				return new Object[1];
			}
		};
	}

	public void set(String key, T value) {
		remove(key);
		add(key, value);
	}

	protected abstract T[] createNewArray();

	public void add(String key, T value) {
		// null values replaces all existing values for this key
		if (value == null) {
			put(key, null);
			return;
		}

		T[] values = get(key);

		if (values == null) {
			values = createNewArray();
			values[0] = value;
		} else {
			values = ArrayUtils.addAll(values, value);
		}

		super.put(key, values);
	}

	
	public T getFirst(String key) {
		T[] value = get(key);

		if (value == null) {
			return null;
		}

		return value[0];
	}

	
	public String[] getStrings(String key) {
		T[] values = get(key);

		if (values == null) {
			return null;
		}

		String[] strings = new String[values.length];

		for (int i = 0; i < values.length; i++) {
			T value = values[i];

			strings[i] = value.toString();
		}

		return strings;
	}

}
