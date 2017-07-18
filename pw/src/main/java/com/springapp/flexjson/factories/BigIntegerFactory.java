package com.springapp.flexjson.factories;


import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;
import java.math.BigInteger;

public class BigIntegerFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
    	if(value.toString().equals("")) return null;
    	
    	return new BigInteger( value.toString() );
    }
}
