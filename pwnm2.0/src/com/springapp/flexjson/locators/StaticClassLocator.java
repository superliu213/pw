package com.springapp.flexjson.locators;


import com.springapp.flexjson.ClassLocator;
import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.Path;

/**
 * Simple implementation for translating an object path to a single class.
 * Normally you would not use this class directly and use the
 * {@link com.springapp.flexjson.JSONDeserializer#use(String, Class)} method
 * instead.
 */
public class StaticClassLocator implements ClassLocator {
    private Class target;

    public StaticClassLocator(Class clazz) {
        target = clazz;
    }

    public Class locate(ObjectBinder context, Path currentPath) {
        return target;
    }
}
