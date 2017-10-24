package com.springapp.common.converter;

import org.apache.commons.beanutils.ConvertUtils;

/**
 * 转换规则注册
 * 
 * @author jacker
 */
public class ConvertRegister {
	/**
	 * 构造函数
	 */
	public ConvertRegister() {
	}

	/**
	 * 注册转换器
	 */
	public static void register() {
		ConvertUtils.register(new BigDecimalConverter(null),
				java.math.BigDecimal.class);
		ConvertUtils.register(new BigIntegerConverter(null),
				java.math.BigInteger.class);
		ConvertUtils.register(new BooleanConverter(null), Boolean.TYPE);
		ConvertUtils.register(new BooleanConverter(null),
				Boolean.class);
		ConvertUtils.register(new ByteConverter(null), Byte.TYPE);
		ConvertUtils.register(new ByteConverter(null), Byte.class);
		ConvertUtils.register(new CharacterConverter(null), Character.TYPE);
		ConvertUtils.register(new CharacterConverter(null),
				Character.class);
		ConvertUtils.register(new DoubleConverter(null), Double.TYPE);
		ConvertUtils
				.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new FloatConverter(null), Float.TYPE);
		ConvertUtils.register(new FloatConverter(null), Float.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.TYPE);
		ConvertUtils.register(new IntegerConverter(null),
				Integer.class);
		ConvertUtils.register(new LongConverter(null), Long.TYPE);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new ShortConverter(null), Short.TYPE);
		ConvertUtils.register(new ShortConverter(null), Short.class);
		ConvertUtils.register(new StringConverter(), String.class);
		ConvertUtils
				.register(new UtilDateConverter(null), java.util.Date.class);
		ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);
	}
}
