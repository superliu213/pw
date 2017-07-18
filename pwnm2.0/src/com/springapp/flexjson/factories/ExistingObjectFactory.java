package com.springapp.flexjson.factories;


import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;
import java.util.Map;

public class ExistingObjectFactory implements ObjectFactory {

    private Object source;

    public ExistingObjectFactory(Object source) {
        this.source = source;
    }

    @Override
    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        return context.bindIntoObject( (Map)value, source, targetType );
    }
}
