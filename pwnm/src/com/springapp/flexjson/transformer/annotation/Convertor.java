package com.springapp.flexjson.transformer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Convertor {
	/**
	 * 转换的类名称
	 * @return
	 */
	String convertor() default "";

	/**
	 * el表达式
	 * @return
	 */
	String el() default "";
	
	/**
	 * 代码类型
	 * @return
	 */
	String codeType() default "";
	
	/**
	 * 值字段名称，如果为空，值为本身
	 * @return
	 */
	String valueFieldName() default "";
}
