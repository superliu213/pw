package com.springapp.flexjson.factories;


import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;

public class BooleanObjectFactory implements ObjectFactory {
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        return (Boolean)value;
    }
}
