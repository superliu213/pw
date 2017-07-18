package com.springapp.common.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ConfigurationItem {

	@XStreamAsAttribute
	@XmlAttribute(required = false)
	private long version;
	
	public void setVersion(long version) {
		this.version = version;
	}

	public long getVersion() {
		return version;
	}

}
