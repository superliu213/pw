package com.springapp.common.converter;

import java.math.BigInteger;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * 页面post数据时，转换规则类
 * 
 * @author jacker
 */
public final class BigIntegerConverter implements Converter {

	private Object defaultValue;
	private boolean useDefault;

	/**
	 * 构造函数
	 */
	public BigIntegerConverter() {
		defaultValue = null;
		useDefault = false;
	}

	/**
	 * 构造函数
	 * @param newDefaultValue 缺省值
	 */
	public BigIntegerConverter(Object newDefaultValue) {
		this.defaultValue = newDefaultValue;
		this.useDefault = true;
	}

	/**
	 * 
	 * @param type 被转换对象 的类型
	 * @param value 被转换对象的值
	 * @return 转换后 的对象
	 */
	public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {
		Object convertedObject = null;
		
		if (value == null || "".equals(value.toString().trim())) {
			convertedObject = defaultValue;
		}
		if (value instanceof BigInteger) {
			convertedObject = value;
		}
		
		try {
			return new BigInteger(value.toString());
		} catch (NumberFormatException e) {
			if (useDefault) {
				convertedObject = defaultValue;
			} else {
				throw new ConversionException(e);
			}
		}
		
		return convertedObject;
	}
}
