package com.springapp.common.converter;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * 页面post数据时，转换规则类
 * 
 * @author jacker
 */
public final class CharacterConverter implements Converter {
	private Object defaultValue;
	private boolean useDefault;

	/**
	 * 构造函数
	 */
	public CharacterConverter() {
		defaultValue = null;
		useDefault = false;
	}

	/**
	 * 构造函数
	 * 
	 * @param newDefaultValue
	 *            缺省值
	 */
	public CharacterConverter(Object newDefaultValue) {
		this.defaultValue = newDefaultValue;
		useDefault = true;
	}

	/**
	 * 
	 * @param type
	 *            被转换对象 的类型
	 * @param value
	 *            被转换对象的值
	 * @return 转换后 的对象
	 */
	public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {
		Object convertedObject = null;
		
		if (value == null || "".equals(value.toString().trim())) {
			convertedObject = defaultValue;
		}
		
		if (value instanceof Character) {
			convertedObject = value;
		}
		
		try {
			return new Character(value.toString().charAt(0));
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
