package com.springapp.flexjson.factories;


import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;

public class FloatObjectFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else {
            try {
            	if(value.toString().equals("")) return null;
            	
                return Float.parseFloat(value.toString());
            } catch (Exception e) {
                throw context.cannotConvertValueToTargetType(value, Float.class);
            }
        }
    }
}
