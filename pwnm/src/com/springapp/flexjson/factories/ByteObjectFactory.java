package com.springapp.flexjson.factories;

import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;

public class ByteObjectFactory implements ObjectFactory {

    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        if( value instanceof Number) {
            return ((Number)value).byteValue();
        } else {
            throw context.cannotConvertValueToTargetType( value, Byte.class );
        }
    }
}
