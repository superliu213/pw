package com.springapp.common.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlTransient;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Configuration extends ConfigurationItem {
	@XStreamImplicit
	@XmlElementRef
	protected List<ConfigurationItem> configItems;

	@XStreamOmitField
	@XmlTransient
	protected Map<String, ConfigurationItem> configMaps;

	@XStreamOmitField
	@XmlTransient
	protected boolean isInit;

	/**
	 * 增加配置
	 * @param item 配置项
	 */
	public void addConfig(ConfigurationItem item) {
		if (configItems == null) {
			configItems = new LinkedList<ConfigurationItem>();

		}
		if (configMaps == null) {
			configMaps = new HashMap<String, ConfigurationItem>();
		}

		XStreamAlias x = item.getClass().getAnnotation(XStreamAlias.class);
		String key = item.getClass().getSimpleName();
		if (x != null) {
			key = x.value();
		}
		configItems.add(item);
		configMaps.put(key, item);
	}

	/**
	 * 得到配置项
	 * @param key 键
	 * @return
	 */
	public ConfigurationItem getConfigItem(String key) {
		if (!isInit) {
			initSubConfig();
		}
		return configMaps.get(key);
	}

	/**
	 * 得到配置项
	 * @param itemClass 配置项类型
	 * @return
	 */
	public ConfigurationItem getConfigItem(
			Class<? extends ConfigurationItem> itemClass) {
		if (!isInit) {
			initSubConfig();
		}
		if (itemClass.isInstance(this)) {
			return this;
		}
		XStreamAlias alias = itemClass.getAnnotation(XStreamAlias.class);
		return configMaps.get(alias.value());
	}

	/**
	 * 初始化子配置
	 */
	public void initSubConfig() {
		isInit = true;
		if (configItems == null) {
			return;
		}
		configMaps = new HashMap<String, ConfigurationItem>();
		for (ConfigurationItem iterableElement : configItems) {
			XStreamAlias x = iterableElement.getClass().getAnnotation(
					XStreamAlias.class);
			iterableElement.getClass().getSimpleName();
			String key = iterableElement.getClass().getSimpleName();
			if (x != null) {
				key = x.value();
			}
			configMaps.put(key, iterableElement);
		}
	}
}
