package com.springapp.common.converter;

import org.apache.commons.beanutils.Converter;

/**
 * 页面post数据时，转换规则类
 * 
 * @author jacker
 */
public final class StringConverter implements Converter {

	/**
	 * 构造函数
	 */
	public StringConverter() {
	}

	/**
	 * 
	 * @param type 被转换对象 的类型
	 * @param value 被转换对象的值
	 * @return 转换后 的对象
	 */
	public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}
	}
}
