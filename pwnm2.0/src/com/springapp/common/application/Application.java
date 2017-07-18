package com.springapp.common.application;

import java.util.HashMap;
import java.util.Map;

import com.springapp.common.config.Configuration;
import com.springapp.common.config.IConfigurationProvider;
import com.springapp.common.spring.BeanFactoryUtil;
import com.springapp.common.spring.IService;
import com.springapp.exception.ApplicationException;

/**
 * 应用总体类，应用启动时必须 Application.loadSpringConfig
 *
 */
public class Application {

	private static HashMap<Object, Object> values = new HashMap<Object, Object>();

	private static Map<String, IService> services = new HashMap<String, IService>();

	private static Map<String, Object> classBeans = new HashMap<String, Object>();

	private static IConfigurationProvider configProvider;

	private static Configuration config;

	/**
	 * 注册bean
	 * 
	 * @param name
	 *            名称
	 * @param classBean
	 *            注册的对象
	 */
	public static void registerClassBean(String name, Object classBean) {
		classBeans.put(name, classBean);
	}

	/**
	 * 注册bean
	 * 
	 * @param cls
	 *            类名
	 * @param classBean
	 *            注册的对象
	 */
	public static void registerClassBean(Class<?> cls, Object classBean) {
		classBeans.put(cls.getName(), classBean);
	}

	/**
	 * 注册service
	 * 
	 * @param name
	 *            名称
	 * @param service
	 *            service对象
	 */
	public static void registerService(String name, IService service) {
		services.put(name, service);
	}

	/**
	 * 注册service
	 * 
	 * @param cls
	 *            类名
	 * @param service
	 *            service对象
	 */
	public static void registerService(Class<?> cls, IService service) {
		services.put(cls.getName(), service);
	}

	/**
	 * 加载spring配置文件
	 * 
	 * @param files
	 *            spring配置文件
	 */
	public static void loadSpringConfig(String... files) {
		BeanFactoryUtil.initBean(files);
	}

	/**
	 * 初始化应用
	 * 
	 * @param files
	 *            配置文件
	 */
	public static void initialize(String... files) {
		loadSpringConfig(files);

		// 初始化别的，待扩展
	}

	/**
	 * 根据键值得到全局属性
	 * 
	 * @param key
	 *            键值
	 * @param t
	 *            值
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(String key, Class<? extends T> t) {
		return (T) values.get(key);
	}

	/**
	 * 根据键值得到全局属性
	 * 
	 * @param key
	 *            键值
	 * @return
	 */
	public static Object getAttribute(String key) {
		return values.get(key);
	}

	/**
	 * 设置全局属性
	 * 
	 * @param key
	 *            键值
	 * @param value
	 *            值
	 */
	public static void setAttribute(String key, Object value) {
		values.put(key, value);
	}

	/**
	 * 根据bean名称获取bean对象
	 * 
	 * @param beanName
	 *            bean名称
	 * @return
	 */
	public static <T> T getClassByName(String beanName) {
		if (classBeans.containsKey(beanName)) {
			return (T) classBeans.get(beanName);
		}
		return (T) BeanFactoryUtil.getBean(beanName);
	}

	/**
	 * 根据bean的类型得到bean对象
	 * 
	 * @param cls
	 *            类型
	 * @param defaultValue
	 *            缺省值
	 * @return
	 * @throws ApplicationException
	 */
	public static <T> T getClassBean(Class<T> cls, T defaultValue) throws ApplicationException {
		if (classBeans.containsKey(cls.getName())) {
			return (T) classBeans.get(cls.getName());
		}
		String[] beans = BeanFactoryUtil.getApplicationContext().getBeanNamesForType(cls);

		if (beans == null || beans.length <= 0) {
			return defaultValue;
		}
		if (beans.length > 1) {
			throw new ApplicationException(
					"There are " + beans.length + " " + cls.getName() + " implements in application context");
		}
		return (T) BeanFactoryUtil.getBean(beans[0]);
	}

	/**
	 * 根据bean的类型得到bean对象
	 * 
	 * @param cls
	 *            类型
	 * @return
	 * @throws ApplicationException
	 */
	public static <T> T getClassBean(Class<T> cls) throws ApplicationException {
		if (classBeans.containsKey(cls.getName())) {
			return (T) classBeans.get(cls.getName());
		}
		String[] beans = BeanFactoryUtil.getApplicationContext().getBeanNamesForType(cls);

		if (beans == null || beans.length <= 0) {
			throw new ApplicationException("Service Class " + cls.getName() + " is not found");
		}
		if (beans.length > 1) {
			throw new ApplicationException(
					"There are " + beans.length + " " + cls.getName() + " implements in application context");
		}
		return (T) BeanFactoryUtil.getBean(beans[0]);
	}

	/**
	 * 用class获取service
	 * 
	 * @param cls
	 *            服务类型
	 * @return
	 * @throws ApplicationException
	 */
	public static <T extends IService> T getService(Class<? extends IService> cls) throws ApplicationException {
		if (services.containsKey(cls.getName())) {
			return (T) services.get(cls.getName());
		} else {
			String[] beans = BeanFactoryUtil.getApplicationContext().getBeanNamesForType(cls);
			if (beans == null || beans.length <= 0) {
				throw new ApplicationException("Service Class " + cls.getName() + " is not found");
			}
			if (beans.length > 1) {
				throw new ApplicationException(
						"There are " + beans.length + " " + cls.getName() + " implements in application context");
			}
			return (T) BeanFactoryUtil.getBean(beans[0]);
		}
	}

	/**
	 * 用beanName获取service
	 * 
	 * @param beanName
	 *            bean名称
	 * @return
	 */
	public static <T extends IService> T getServiceByBeanName(String beanName) {
		if (services.containsKey(beanName)) {
			return (T) services.get(beanName);
		} else {
			IService service = null;

			try {
				service = (IService) BeanFactoryUtil.getApplicationContext().getBean(beanName);
			} catch (Exception ex) {
				throw new ApplicationException("bean:" + beanName + " 在配置文件spring-config中不存在\n", ex);
			}
			return (T) service;
		}
	}

}
