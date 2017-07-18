package com.springapp.flexjson.transformer.annotation;

public interface IConvertor {
	String getString(Object obj, Object value, Object... args) throws Exception;
}
