package com.springapp.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.springapp.common.converter.Convert;
import com.springapp.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.springframework.cglib.beans.BeanCopier;

/**
 * class处理的实用类
 * 
 * @author jacker
 */
public class ClassUtil {
	private static final int PROP_METHOD_PRE_LENGTH = 3;
	private static final int MAX_STRING_BUFFER_LENGTH = 1024;
	private static Log logger = LogFactory.getLog(ClassUtil.class);

	/**
	 * 默认构造函数
	 */
	public ClassUtil() {
	}

	/**
	 * 根据类型名称创建实例
	 * 
	 * @param name
	 *            类型名称
	 * @return
	 */
	public static Object getClassInstance(String name) {
		try {
			return Class.forName(name).newInstance();
		} catch (ClassNotFoundException ex) {
			logger.error(ex.toString());
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		} catch (InstantiationException ex) {
			logger.error(ex.toString());
		}
		return null;
	}

	/**
	 * 得到实体集合
	 * 
	 * @param req
	 *            请求
	 * @param entity
	 *            实体类型
	 * @return 实体集合
	 */
	public static Collection<?> getEntities(HttpServletRequest req, Class<?> entity) {
		try {
			return Convert.getCollection(req, entity);
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		} catch (InstantiationException ex) {
			logger.error(ex.toString());
		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
		}
		return null;
	}

	/**
	 * 拷贝对象
	 * 
	 * @param from
	 *            源对象
	 * @param to
	 *            目的对象
	 */
	public static void copyProperties(Object from, Object to) {
		try {
			if (from == null || to == null) {
				return;
			}
			// BeanUtils.copyProperties(to, from);
			BeanCopier.create(from.getClass(), to.getClass(), false).copy(from, to, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.toString());
		}
	}

	/**
	 * 得到对象的属性字符串
	 * 
	 * @param bean
	 *            对象
	 * @return 对象的属性字符串
	 */
	public static synchronized String getProperties(Object bean) {
		StringBuffer sb = new StringBuffer(MAX_STRING_BUFFER_LENGTH);
		int index = 0;
		Method[] methodsOfBean = bean.getClass().getMethods();
		String property = null;
		String objStr = null;
		String nameGet = null;
		String nameGetAttr = null;

		try {
			for (int i = 0; i < methodsOfBean.length; i++) {
				nameGet = methodsOfBean[i].getName().substring(0, PROP_METHOD_PRE_LENGTH);
				nameGetAttr = methodsOfBean[i].getName().substring(PROP_METHOD_PRE_LENGTH);
				if ("get".equalsIgnoreCase(nameGet) && !"class".equalsIgnoreCase(nameGetAttr)
						&& !"SERVLETWRAPPER".equalsIgnoreCase(nameGetAttr.toUpperCase())
						&& !"MULTIPARTREQUESTHANDLER".equalsIgnoreCase(nameGetAttr.toUpperCase())) {
					Object args = methodsOfBean[i].invoke(bean, new Object[] {});
					if (args != null) {
						objStr = args.toString();
					} else {
						objStr = "null";
					}

					property = getObj(args, objStr);
					if (index == 0) {
						sb.append(nameGetAttr.toUpperCase() + "=" + property);
						index++;
					} else {
						sb.append("," + nameGetAttr.toUpperCase() + "=" + property);
					}
				}
			}

		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.toString());
		}
		return sb.toString();
	}

	/**
	 * 得到对象数组的字符串
	 * 
	 * @param objs
	 *            对象数组
	 * @return
	 */
	public static String getArray(Object[] objs) {
		StringBuffer sb = new StringBuffer(MAX_STRING_BUFFER_LENGTH);
		String objStr = null;
		for (int index = 0; index < objs.length; index++) {
			objStr = objs[index].toString();
			String property = getObj(objs[index], objStr);
			if (index == 0) {
				sb.append("[" + index + "]=" + property);
			} else {
				sb.append(",[" + index + "]=" + property);
			}
		}

		return sb.toString();
	}

	/**
	 * 得到map的字符表达
	 * 
	 * @param map
	 *            map
	 * @return
	 */
	public static String getMap(Map<?, ?> map) {
		return getIterator(map.values().iterator());
	}

	/**
	 * 
	 * @param iterator
	 * @return
	 */
	public static String getIterator(Iterator<?> iterator) {
		StringBuffer sb = new StringBuffer(MAX_STRING_BUFFER_LENGTH);
		String property = null;
		String objStr = null;
		int index = 0;
		for (Iterator<?> it = iterator; it.hasNext();) {
			Object obj = it.next();
			objStr = obj.toString();
			property = getObj(obj, objStr);
			if (index == 0) {
				sb.append(property);
				index++;
			} else {
				sb.append("," + property);
			}
		}

		return sb.toString();
	}

	private static String getObj(Object obj, String property) {
		return property;
	}

	/**
	 * 
	 * @param bean
	 */
	public static synchronized void trimProperties(Object bean) {
		Method[] methods = bean.getClass().getMethods();
		String nameGet = null;
		String nameGetAttr = null;
		Class<?>[] tmpTypes = new Class[methods.length];
		String[] tmpNames = new String[methods.length];
		Method[] tmpSetMethods = new Method[methods.length];
		int index = 0;
		for (int i = 0; i < methods.length; i++) {
			nameGet = methods[i].getName().substring(0, PROP_METHOD_PRE_LENGTH);
			nameGetAttr = methods[i].getName().substring(PROP_METHOD_PRE_LENGTH);
			if ("set".equalsIgnoreCase(nameGet)) {
				tmpTypes[index] = methods[i].getParameterTypes()[0];
				tmpNames[index] = nameGetAttr;
				tmpSetMethods[index] = methods[i];
				index++;
			}
		}

		Class<?>[] types = new Class[index];
		String[] names = new String[index];
		Method[] setMethods = new Method[index];
		for (int i = 0; i < index; i++) {
			types[i] = tmpTypes[i];
			names[i] = tmpNames[i];
			setMethods[i] = tmpSetMethods[i];
		}

		Method[] methodsOfFrom = bean.getClass().getMethods();
		Object arg = null;
		Object encoded = null;
		try {
			for (int i = 0; i < names.length; i++) {
				for (int j = 0; j < methodsOfFrom.length; j++) {
					nameGet = methodsOfFrom[j].getName().substring(0, PROP_METHOD_PRE_LENGTH);
					nameGetAttr = methodsOfFrom[j].getName().substring(PROP_METHOD_PRE_LENGTH);
					if (names[i].equalsIgnoreCase(nameGetAttr) && "get".equalsIgnoreCase(nameGet)) {
						Class<?> returnType = methodsOfFrom[j].getReturnType();
						if (returnType.equals(String.class)) {
							arg = methodsOfFrom[j].invoke(bean, new Object[] {});
							String temp = null;
							if (arg != null) {
								temp = ((String) arg).trim();
							}
							encoded = Convert.convert(temp, types[i]);
							setMethods[i].invoke(bean, new Object[] { encoded });
						}
					}
				}

			}

		} catch (InvocationTargetException ex) {
			logger.error(ex.toString());
		} catch (IllegalArgumentException ex) {
			logger.error(ex.toString());
		} catch (IllegalAccessException ex) {
			logger.error(ex.toString());
		}
	}

	/**
	 * 获取属性值
	 * 
	 * @param obj
	 *            对象
	 * @param method
	 *            方法名称
	 * @return
	 */
	public static String getPropertyValue(Object obj, String method) {
		return getPropertyValue(obj, method, null);
	}

	/**
	 * 获取属性值
	 * 
	 * @param obj
	 *            对象
	 * @param method
	 *            方法名称
	 * @param paramList
	 *            参数列表
	 * @return
	 */
	public static String getPropertyValue(Object obj, String method, Object[] paramList) {
		String value = null;
		try {
			Method m = obj.getClass().getMethod(method, new Class[] {});
			Object retObj = m.invoke(obj, paramList);
			if (retObj instanceof Date) {
				value = DateUtil.dateToString((Date) retObj, "yyyy-mm-dd");
			} else if (retObj instanceof Long) {
				value = ((Long) retObj).toString();
			} else if (retObj instanceof Double) {
				value = ((Double) retObj).toString();
			} else {
				value = (String) retObj;
			}

		} catch (Exception e) {
			logger.error(e.toString());
		}

		return value;
	}

	/**
	 * 转换对象为Hashmap
	 * 
	 * @param entity
	 *            实体对象
	 * @param hm
	 *            hashmap
	 */
	public static void convertEntity2HashMap(Object entity, HashMap<String, Object> hm) {
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				Method m = entity.getClass().getMethod(
						"get" + StringUtil.converFirstUpper(fieldName), new Class[] {});
				Object retObj = m.invoke(entity, new Object[] {});

				if (retObj instanceof Date) {
					hm.put(fieldName, DateUtil.dateToString((Date) retObj, "yyyy-mm-dd"));
				} else {
					hm.put(fieldName, retObj);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 转换对象为Hashmap
	 * 
	 * @param hm
	 * @param entity
	 */
	public static void convertHashMap2Entity(HashMap<?, ?> hm, Object entity) {
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				Method m = entity.getClass().getMethod(
						"set" + StringUtil.converFirstUpper(fieldName), new Class[] {});
				Object[] value = new Object[] { hm.get(fieldName) };

				m.invoke(entity, value);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 将map中的值转为对应对象值
	 * 
	 * @param hm
	 * @param entity
	 */
	public static void convertMap2Entity(Map<?, ?> hm, Object entity) {
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (field.getModifiers() > 16) { // 权限过高 不能设置(已经为final的类型)
					break;
				}
				field.setAccessible(true);
				Object x = hm.get(field.getName());
				if (x == null) {
					continue;
				}
				field.set(entity, x);
				field.setAccessible(false);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 填充master对象到detailList中的detail
	 * 
	 * @param obj
	 *            主对象
	 * @param list
	 *            明细对象集合
	 * @param detailType
	 *            明细对象类型
	 * @param propertyName
	 *            明细对象中主对象属性名称
	 * @throws Exception
	 */
	public static void fillObjectToList(Object obj, Collection<?> list, Class<?> detailType,
			String propertyName) throws Exception {
		Class<? extends Object> masterType = obj.getClass();

		Method m = detailType.getMethod(
				"set" + StringUtil.converFirstUpper(propertyName), new Class[] { masterType });
		Object[] paramsList = { obj };

		Iterator<?> it = null;
		if (list != null) {
			it = list.iterator();
		}

		for (; it != null && it.hasNext();) {
			Object detail = it.next();
			m.invoke(detail, paramsList);
		}
	}

	/**
	 * 获取要移除的属性列表
	 * 
	 * @param oldObjects
	 *            源对象集合
	 * @param newObjects
	 *            新对象集合
	 * @param key
	 *            键值
	 * @return
	 * @throws Exception
	 */
	public static List<Object> getRemoveList(Collection<?> oldObjects, Collection<?> newObjects,
			String key) {
		List<Object> removeList = new ArrayList<Object>();

		if (oldObjects == null) {
			return removeList;
		}

		try {
			for (Iterator<?> oldIt = oldObjects.iterator(); oldIt.hasNext();) {
				Object oldDetail = oldIt.next();
				Method m1 = oldDetail.getClass().getMethod(
						"get" + StringUtil.converFirstUpper(key));
				Object oldObjectKey = m1.invoke(oldDetail);

				Boolean flag = true;
				for (Iterator<?> it = newObjects.iterator(); it.hasNext();) {
					Object newDetail = it.next();
					Method m2 = newDetail.getClass().getMethod(
							"get" + StringUtil.converFirstUpper(key));
					Object newObjectKey = m2.invoke(newDetail);

					if (oldObjectKey != null && newObjectKey != null
							&& newObjectKey.equals(oldObjectKey)) {
						flag = false;
						break;
					}
				}
				if (flag) {
					removeList.add(oldDetail);
				}
			}
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}

		return removeList;
	}

	/**
	 * 转换java对象为xml
	 * 
	 * @param cls
	 *            class
	 * @param javaObject
	 *            java对象
	 * @return
	 */
	public static String toXML(Class<? extends Object> cls, Object javaObject) {
		String xmlString = null;
		try {
			if (javaObject != null && cls != null) {
				XStream xs = new XStream(new DomDriver());
				xs.autodetectAnnotations(true);
				xs.processAnnotations(cls);
				xmlString = xs.toXML(javaObject);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return xmlString;
	}

	/**
	 * 转换xml为java对象
	 * 
	 * @param cls
	 *            class
	 * @param xmlString
	 *            xml字符串
	 * @return
	 */
	public static Object fromXML(Class<? extends Object> cls, String xmlString) {
		Object javaObject = null;
		try {
			if (xmlString != null && !xmlString.equals("") && cls != null) {
				XStream xs = new XStream(new DomDriver());
				xs.autodetectAnnotations(true);
				xs.processAnnotations(cls);
				javaObject = xs.fromXML(xmlString);
			}

		} catch (Exception e) {
			logger.error(e.toString());
		}

		return javaObject;
	}
	
	/**
	 * 得到class的所有field，包括父类
	 * @param c
	 * @return
	 */
	public static Field[] getFields(Class<?> c) {
		Field[] newFields = c.getDeclaredFields();
		
		Class<?> superC = c.getSuperclass();
		if (superC.equals(Object.class)) {
			return ArrayUtil.AddArray(newFields, new Field[0]);
		} else {
			return ArrayUtil.AddArray(newFields, getFields(superC));
		}
	}
	
	/**
	 * fields中是否存在field=fieldName的字段
	 * @param fields
	 * @param fieldName
	 * @return
	 */
	public static boolean isExist(Field[] fields, String fieldName) {
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return true;
			}
		}
		
		return false;
	}
}
