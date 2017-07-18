package com.springapp.flexjson.transformer.annotation;


public class JSONConvertor implements IConvertor {
	/**
	 * json转换
	 * @param obj 转换的对象本身
	 * @param value 对象
	 * @param args 变参，第二个参数为xml对应的java类型
	 * @return
	 * @throws Exception
	 */
	public String getString(Object obj, Object value,Object... args) throws Exception {
		return (String)value;
	}
}
