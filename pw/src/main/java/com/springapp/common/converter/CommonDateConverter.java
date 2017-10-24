package com.springapp.common.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CommonDateConverter implements Converter {
	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Override
	public boolean canConvert(Class clazz) {
		return Date.class.isAssignableFrom(clazz);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		if (value == null) {
			writer.setValue("");
		} else {
			String str = new SimpleDateFormat(FORMAT).format((Date) value);
			writer.setValue(str);
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		try {
			if (StringUtils.isEmpty(reader.getValue())) {
				return null;
			}
			return new SimpleDateFormat(FORMAT).parse(reader.getValue());
		} catch (ParseException e) {
			throw new ConversionException(e.getMessage(), e);
		}
	}
}