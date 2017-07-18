package com.springapp.flexjson.factories;


import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;

public class DoubleObjectFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            try {
            	if(value.toString().equals("")) return null;
            	
                return Double.parseDouble(value.toString());
            } catch (Exception e) {
                throw context.cannotConvertValueToTargetType(value, Double.class);
            }
        }
    }
}
