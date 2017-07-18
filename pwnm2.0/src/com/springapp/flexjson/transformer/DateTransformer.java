package com.springapp.flexjson.transformer;

import com.springapp.flexjson.JSONException;
import com.springapp.flexjson.ObjectBinder;
import com.springapp.flexjson.ObjectFactory;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * User: brandongoodin Date: Dec 12, 2007 Time: 11:20:39 PM
 */
public class DateTransformer extends AbstractTransformer implements
		ObjectFactory {

	SimpleDateFormat simpleDateFormatter;

	public DateTransformer(String dateFormat) {
		simpleDateFormatter = new SimpleDateFormat(dateFormat);
	}

	public DateTransformer(String dateFormat, Locale locale) {
		simpleDateFormatter = new SimpleDateFormat(dateFormat, locale);
	}

	public void transform(Object value) {
		getContext().writeQuoted(simpleDateFormatter.format(value));
	}

	public Object instantiate(ObjectBinder context, Object value,
			Type targetType, Class targetClass) {
		try {
			if (value == null || value.toString().equals(""))
				return null;
			
			if (value.toString().length() == 10) {
				SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(value.toString());
			}
			
			return simpleDateFormatter.parse(value.toString());
		} catch (ParseException e) {
			throw new JSONException(String.format(
					"Failed to parse %s with %s pattern.", value,
					simpleDateFormatter.toPattern()), e);
		}
	}
}
