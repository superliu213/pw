package com.springapp.common.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * 页面post数据时，转换规则类
 * 
 * @author jacker
 */
public final class BooleanConverter implements Converter {

	private Object defaultValue;
	private boolean useDefault;

	/**
	 * 构造函数
	 */
	public BooleanConverter() {
		defaultValue = null;
		useDefault = false;
	}

	/**
	 * 构造函数
	 * @param newDefaultValue 缺省值
	 */
	public BooleanConverter(Object newDefaultValue) {
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
			throw new ConversionException("No value specified");
		}
		if (value instanceof Boolean) {
			convertedObject = value;
		}
			
		try {
			String stringValue = value.toString();
			if (stringValue.equalsIgnoreCase("yes")
					|| stringValue.equalsIgnoreCase("y")
					|| stringValue.equalsIgnoreCase("true")
					|| stringValue.equalsIgnoreCase("on")
					|| stringValue.equalsIgnoreCase("1")) {
				convertedObject = Boolean.TRUE;
			}
			
			if (stringValue.equalsIgnoreCase("no")
					|| stringValue.equalsIgnoreCase("n")
					|| stringValue.equalsIgnoreCase("false")
					|| stringValue.equalsIgnoreCase("off")
					|| stringValue.equalsIgnoreCase("0")) {
				convertedObject = Boolean.FALSE;
			}
			
			if (useDefault) {
				convertedObject = defaultValue;
			} else {
				throw new ConversionException(stringValue);
			}
		} catch (ClassCastException e) {
			if (useDefault) {
				convertedObject = defaultValue;
			} else {
				throw new ConversionException(e);
			}
		}
		
		return convertedObject;
	}
}
