package com.springapp.flexjson.factories;


import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class BigDecimalFactory implements ObjectFactory {

    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if( value instanceof Number) {
            return new BigDecimal( ((Number)value).doubleValue() );
        } else {
        	if(value.toString().equals("")) return null;
        	
            return new BigDecimal(value.toString() );
        }
    }
}
