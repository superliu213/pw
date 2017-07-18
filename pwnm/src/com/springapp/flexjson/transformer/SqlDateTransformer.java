package com.springapp.flexjson.transformer;

import com.springapp.flexjson.JSONException;
import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * User: brandongoodin
 * Date: Dec 12, 2007
 * Time: 11:20:39 PM
 */
public class SqlDateTransformer extends AbstractTransformer implements ObjectFactory {

    SimpleDateFormat simpleDateFormatter;

    public SqlDateTransformer(String dateFormat) {
        simpleDateFormatter = new SimpleDateFormat(dateFormat);
    }


    public void transform(Object value) {
        getContext().writeQuoted(simpleDateFormatter.format(value));
    }

    public Object instantiate(ObjectBinder context, Object value, Type targetType, Class targetClass) {
        try {
        	if(value.toString().equals("")) return null;
        	
            return new java.sql.Date( simpleDateFormatter.parse( value.toString() ).getTime());
        } catch (ParseException e) {
            throw new JSONException(String.format("Failed to parse %s with %s pattern.", value, simpleDateFormatter.toPattern()), e );
        }
    }
}
