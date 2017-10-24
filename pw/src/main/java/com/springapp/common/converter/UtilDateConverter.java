package com.springapp.common.converter;

import java.util.Date;

import com.springapp.common.util.DateUtil;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * 页面post数据时，转换规则类
 * 
 * @author jacker
 */
public final class UtilDateConverter implements Converter {
	private Object defaultValue;
	private boolean useDefault;

	/**
	 * 构造函数
	 */
	public UtilDateConverter() {
		defaultValue = null;
		useDefault = false;
	}

	/**
	 * 构造函数
	 * @param newDefaultValue 缺省值
	 */
	public UtilDateConverter(Object newDefaultValue) {
		this.defaultValue = newDefaultValue;
		useDefault = true;
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
		if (value instanceof Date) {
			convertedObject = value;
		}
		
		try {
			String format = "YYYY-MM-DD HH24:MI:SS";
			if (value.toString().length() == DateUtil.DATELENGTH) {
				format = "YYYY-MM-DD";
			}
			convertedObject = DateUtil.stringToDate(value.toString(), format);
		} catch (Exception e) {
			if (useDefault) {
				return defaultValue;
			} else {
				throw new ConversionException(e);
			}
		}
		
		return convertedObject;
	}
}
