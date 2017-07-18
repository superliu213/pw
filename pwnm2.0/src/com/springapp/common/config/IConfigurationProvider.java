package com.springapp.common.config;

public interface IConfigurationProvider {

	/**
	 * 得到配置项
	 * @param configInstance 配置的instance
	 * @return
	 */
	<T extends ConfigurationItem> T getConfigItem(T configInstance);

	/**
	 * 载入配置
	 * @param externalConfigClasses 配置类型
	 */
	@SuppressWarnings("unchecked")
	void loadConfiguration(Class<? extends ConfigurationItem>... externalConfigClasses);

	/**
	 * 载入配置
	 * @param configFileName 配置文件名称
	 * @param externalConfigClasses 配置类型
	 */
	@SuppressWarnings("unchecked")
	void loadConfiguration(String configFileName,
			Class<? extends ConfigurationItem>... externalConfigClasses);

	/**
	 * 获取配置项
	 * @param cls 配置项类型
	 * @return
	 */
	ConfigurationItem getConfig(Class<? extends ConfigurationItem> cls);

}
