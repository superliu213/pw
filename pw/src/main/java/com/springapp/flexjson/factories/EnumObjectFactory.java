package com.springapp.flexjson.factories;


import com.springapp.flexjson.JSONException;
import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;

public class EnumObjectFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if( value instanceof String) {
            return Enum.valueOf((Class) targetType, value.toString());
        } else {
            throw new JSONException( String.format("%s:  Don't know how to convert %s to enumerated constant of %s", context.getCurrentPath(), value, targetType) );
        }
    }
}
