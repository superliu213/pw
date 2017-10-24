package com.springapp.common.converter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 页面post数据时，转换规则类
 * 
 * @author jacker
 */
public class Convert {
	private static final String PROPNAME = "class";
	private static FastHashMap converters = new FastHashMap();
	private static Log log = LogFactory.getLog(Convert.class);

	static {
		converters.setFast(false);
		register();
	}
	
	/**
	 * 
	 * @param req request请求
	 * @param entity 实体对象
	 * @return 集合
	 * @throws InvocationTargetException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 */
	public static synchronized Collection<Object> getCollection(
			HttpServletRequest req, Class<?> entity)
			throws InvocationTargetException, InstantiationException,
			IllegalAccessException {
		Object dto = entity.newInstance();
		PropertyDescriptor[] origDescriptors = PropertyUtils
				.getPropertyDescriptors(dto);
		String propName = null;
		int length = 0;
		String[] tmp = (String[]) null;
		for (int i = 0; i < origDescriptors.length; i++) {
			propName = origDescriptors[i].getName();
			if (PROPNAME.equals(propName)) {
				continue;
			}
			
			tmp = req.getParameterValues(propName);
			if (tmp == null) {
				continue;
			}
			length = tmp.length;
			break;
		}

		Collection<Object> result = new ArrayList<Object>();
		for (int j = 0; j < length; j++) {
			Object item = entity.newInstance();
			for (int i = 0; i < origDescriptors.length; i++) {
				if (origDescriptors[i].getReadMethod() == null) {
					if (log.isTraceEnabled()) {
						log.trace("-->No getter on JavaBean for "
								+ origDescriptors[i].getName() + ", skipping");
					}
				} else {
					String name = origDescriptors[i].getName();
					if (!PROPNAME.equals(name)) {
						Object value = null;
						tmp = req.getParameterValues(name);
						if (tmp != null) {
							value = tmp[j];
						}
						BeanUtils.copyProperty(item, name, value);
					}
				}
			}
			
			result.add(item);
		}

		return result;
	}

	/**
	 * 
	 * @param value 对象值
	 * @param clazz 类型
	 * @return 转换后对象
	 */
	public static Object convert(String value, Class<?> clazz) {
		Converter converter = (Converter) converters.get(clazz);
		if (converter == null) {
			converter = (Converter) converters.get(String.class);
		}
		
		return converter.convert(clazz, value);
	}

	/**
	 * 
	 * @param values 对象数组
	 * @param clazz 类型
	 * @return 转换后对象
	 */
	public static Object convert(String[] values, Class<?> clazz) {
		Class<?> type = clazz;
		if (clazz.isArray()) {
			type = clazz.getComponentType();
		}
		
		Converter converter = (Converter) converters.get(type);
		if (converter == null) {
			converter = (Converter) converters.get(String.class);
		}
		
		Object array = Array.newInstance(type, values.length);
		for (int i = 0; i < values.length; i++) {
			Array.set(array, i, converter.convert(type, values[i]));
		}
		
		return array;
	}

	/**
	 * 注册转换器
	 */
	private static void register() {
		converters.put(java.math.BigDecimal.class,
				new BigDecimalConverter(null));
		converters.put(java.math.BigInteger.class,
				new BigIntegerConverter(null));
		converters.put(Boolean.TYPE, new BooleanConverter(null));
		converters.put(Boolean.class, new BooleanConverter(null));
		converters.put(Byte.TYPE, new ByteConverter(null));
		converters.put(Byte.class, new ByteConverter(null));
		converters.put(Character.TYPE, new CharacterConverter(null));
		converters.put(Character.class, new CharacterConverter(null));
		converters.put(Double.TYPE, new DoubleConverter(null));
		converters.put(Double.class, new DoubleConverter(null));
		converters.put(Float.TYPE, new FloatConverter(null));
		converters.put(Float.class, new FloatConverter(null));
		converters.put(Integer.TYPE, new IntegerConverter(null));
		converters.put(Integer.class, new IntegerConverter(null));
		converters.put(Long.TYPE, new LongConverter(null));
		converters.put(Long.class, new LongConverter(null));
		converters.put(Short.TYPE, new ShortConverter(null));
		converters.put(Short.class, new ShortConverter(null));
		converters.put(String.class, new StringConverter());
		converters.put(java.sql.Date.class, new SqlDateConverter(null));
		converters.put(java.util.Date.class, new UtilDateConverter(null));
	}

	
}
