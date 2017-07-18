package com.springapp.json;

import com.springapp.exception.ApplicationException;
import com.springapp.flexjson.JSONDeserializer;
import com.springapp.flexjson.transformer.DateTransformer;

import java.util.*;

/**
 * JSON数据处理的工具类
 * 
 * 
 */
public class JSONUtil {
	/**
	 * 把Java对象序列化成JSON字符串
	 * 
	 * @param obj 对象数据
	 * @return JSON字符串
	 */
	public static String getJSONString(Object obj) {
		com.springapp.flexjson.JSONSerializer serializer = new com.springapp.flexjson.JSONSerializer();
		return serializer.exclude("class")
				.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), Date.class)
				.transform(new DateTransformer("yyyy-MM-dd"), java.sql.Date.class)
				.serialize(obj);
	}

	/**
	 * 把Java对象序列化成JSON字符串
	 * 
	 * @param obj 对象数据
	 * @return JSON字符串
	 */
	public static String getDeepJSONString(Object obj) {
		return getDeepJSONStringOUsingExclude(obj, "class");
	}
	
	/**
	 * 把Java对象序列化成JSON字符串（可排除指定的字段）
	 * 
	 * @param obj 对象数据
	 * @param String变参  excludes 排除的字段变参
	 * @return JSON字符串
	 */
	public static String getDeepJSONStringOUsingExclude(Object obj, String...excludes) {
		com.springapp.flexjson.JSONSerializer serializer = new com.springapp.flexjson.JSONSerializer();
		return serializer.exclude(excludes)
				.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), Date.class)
				.transform(new DateTransformer("yyyy-MM-dd"), java.sql.Date.class)
				.deepSerialize(obj);
	}
	
	/**
	 * 把Java对象序列化成JSON字符串（可包含指定的字段）
	 * 
	 * @param obj 对象数据
	 * @param String变参  includes 包含的字段变参
	 * @return JSON字符串
	 */
	public static String getDeepJSONStringOUsingInclude(Object obj, String...includes) {
		com.springapp.flexjson.JSONSerializer serializer = new com.springapp.flexjson.JSONSerializer();
		return serializer.include(includes)
				.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), Date.class)
				.transform(new DateTransformer("yyyy-MM-dd"), java.sql.Date.class)
				.deepSerialize(obj);
	}
	
	/**
	 * 由JSONData序列化成JSON字符串
	 * 
	 * @param jsonData json对象
	 * @return JSON字符串
	 */
	public static String getJSONString(JSONData jsonData) {
		com.springapp.flexjson.JSONSerializer serializer = new com.springapp.flexjson.JSONSerializer();
		return serializer.exclude("class")
				.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), Date.class)
				.transform(new DateTransformer("yyyy-MM-dd"), java.sql.Date.class)
				.serialize(jsonData);
	}

	/**
	 * 由JSONData序列化成JSON字符串
	 * 
	 * @param jsonData json对象
	 * @return JSON字符串
	 */
	public static String getDeepJSONString(JSONData jsonData) {
		com.springapp.flexjson.JSONSerializer serializer = new com.springapp.flexjson.JSONSerializer();
		return serializer.exclude("class")
				.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), Date.class)
				.transform(new DateTransformer("yyyy-MM-dd"), java.sql.Date.class)
				.deepSerialize(jsonData);
	}

	/**
	 * 由ApplicationException得到JSON字符串
	 * 
	 * @param appException app异常
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(ApplicationException appException) {
		String jsonString = "";

		String dispMessage = "";
//		if (appException.getErrorCode() != 0) {
//			dispMessage = "错误代码：" + appException.getErrorCode() + "<br>";
//		}
		
		String message = appException.getErrorMessage();
		if (message == null) {
			message = "";
		}
		dispMessage += "错误描述:" + message.replace("'", "\\'");
		
		jsonString = "{success:false,code:" + appException.getErrorCode() + ",message:'" + dispMessage + "'}";

		return jsonString;
	}

	/**
	 * 由ApplicationException和url得到JSON字符串
	 * 
	 * @param appException app异常
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(ApplicationException appException, String url) {
		String jsonString = "";

		String dispMessage = "";
//		if (appException.getErrorCode() != 0) {
//			dispMessage = "错误代码：" + appException.getErrorCode() + "<br>";
//		}

		String message = appException.getErrorMessage();
		if (message == null) {
			message = "";
		}
		dispMessage += "错误描述:" + message.replace("'", "\\'");
		
		jsonString = "{success:false,code:" + appException.getErrorCode() + ",message:'" + dispMessage + "',url:'" + url + "'}";

		return jsonString;
	}

	/**
	 * 由ApplicationException得到JSON字符串
	 * 
	 * @param ex 异常
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(Exception ex) {
		String jsonString = "";
		
		String message = ex.getMessage();
		if (message == null) {
			message = "";
		}
		jsonString = "{success:false,code:-1,message:'未知异常：" + message.replace("'", "\\'") + "'}";
		
		return jsonString;
	}

	/**
	 * 由ApplicationException和url得到JSON字符串
	 * 
	 * @param ex 异常
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(Exception ex, String url) {
		String jsonString = "";
		
		String message = ex.getMessage();
		if (message == null) {
			message = "";
		}
		jsonString = "{success:false,code:-1,message:'未知异常：" + message.replace("'", "\\'") + "',url:'" + url + "'}";
		
		return jsonString;
	}

	/**
	 * 得到错误JSON字符串
	 * 
	 * @param code 代码
	 * @param message 信息
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(Long code, String message) {
		String jsonString = "";
		
		if (message == null) {
			message = "";
		}
		jsonString = "{success:false,code:" + code + ",message:'" + message.replace("'", "\\'") + "'}";
		
		return jsonString;
	}
	
	/**
	 * 得到错误JSON字符串
	 * 
	 * @param message 信息
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(String message) {
		return getErrorJSONString(new Long(-1), message);
	}

	/**
	 * 得到错误JSON字符串
	 * 
	 * @param code 代码
	 * @param message 消息
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(Long code, String message, String url) {
		String jsonString = "";
		
		if (message == null) {
			message = "";
		}
		jsonString = "{success:false,code:" + code + ",message:'" + message.replace("'", "\\'") + "',url:'" + url + "'}";
		
		return jsonString;
	}
	
	/**
	 * 得到错误JSON字符串
	 * 
	 * @param message 消息
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(String message, String url) {
		return getErrorJSONString(new Long(-1), message, url);
	}

	/**
	 * 得到成功JSON字符串
	 * 
	 * @param message 信息
	 * @return JSON字符串
	 */
	public static String getSuccessJSONString(String message) {
		String jsonString = "";

		if (message == null) {
			message = "";
		}
		jsonString = "{success:true,code:0,message:'" + message.replace("'", "\\'") + "'}";
		
		return jsonString;
	}

	/**
	 * 得到成功JSON字符串
	 * 
	 * @param message 信息
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getSuccessJSONString(String message, String url) {
		String jsonString = "";
		
		if (message == null) {
			message = "";
		}
		jsonString = "{success:true,code:0,message:'" + message.replace("'", "\\'") + "',url:'" + url + "'}";

		return jsonString;
	}

	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 陈功标记
	 * @param code 代码
	 * @param message 信息
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, Long code, String message) {
		String jsonString = "";
		
		if (message == null) {
			message = "";
		}
		jsonString = "{success:" + isSuccess + ",code:" + code + ",message:'" + message.replace("'", "\\'") + "'}";

		return jsonString;
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 陈功标记
	 * @param message 信息
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, String message) {
		Long code = new Long(0);
		if (!isSuccess) {
			code = new Long(-1);
		}

		return getJSONString(isSuccess, code, message);
	}

	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 成功标记
	 * @param code 代码
	 * @param message 信息
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, Long code, String message, String url) {
		String jsonString = "";
		
		if (message == null) {
			message = "";
		}
		
		jsonString = "{success:" + isSuccess + ",code:" + code + ",message:'" + message.replace("'", "\\'") + "',url:'" + url + "'}";

		return jsonString;
	}

	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 成功标记
	 * @param message 信息
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, String message, String url) {
		Long code = new Long(0);
		if (!isSuccess) {
			code = new Long(-1);
		}

		return getJSONString(isSuccess, code, message, url);
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param message 信息
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public static String getSuccessJSONString(String message, Map<String,Object> param) {
		return getJSONString(true, message, null, param);
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param message 信息
	 * @param url url
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public static String getSuccessJSONString(String message, String url, Map<String,Object> param) {
		return getJSONString(true, message, url, param);
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 成功标记
	 * @param message 信息
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, String message, Map<String,Object> param) {
		
		return getJSONString(isSuccess, message, null, param);
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 成功标记
	 * @param message 信息
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, Long code, String message, Map<String,Object> param) {
		return getJSONString(isSuccess, code, message, null, param);
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 成功标记
	 * @param message 信息
	 * @param url url
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, String message, String url, Map<String,Object> param) {
		Long code = new Long(0);
		if (!isSuccess) {
			code = new Long(-1);
		}
		
		return getJSONString(isSuccess, code, message, url,param);
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 成功标记
	 * @param message 信息
	 * @param url url
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, Long code, String message, String url, Map<String,Object> param) {
		String jsonString = "";
		
		if (message == null) {
			message = "";
		}
		
		jsonString = "{success:" + isSuccess + ",code:" + code + ",message:'" + message.replace("'", "\\'") + "',url:'" + url + "'";
		
		if (param != null) {
			Set<String> keySet = param.keySet();
			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				jsonString += "," +  key + ":'" +  param.get(key) + "'";
			}
		}
		
		jsonString += "}";
		
		return jsonString;
	}
	
	/**
	 * 把Java数组对象集合转换JSONArray的集合
	 * 
	 * @param list
	 *            数组对象集合
	 * @param objsNumber
	 *            数组对象的数量
	 * @return JSONArray的集合
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection list2JSONArrayList(Collection list, int objsNumber) {
		Collection jsonArrayList = new ArrayList();

		if (objsNumber == 1) {
			return list;
		}

		if (list == null) {
			return jsonArrayList;
		}
		
		Iterator it = list.iterator();
		if (it == null) {
			return jsonArrayList;
		}

		for (; it.hasNext();) {
			Object[] objs = (Object[]) it.next();
			JSONArray jsonArray = new JSONArray();

			jsonArray.setObj1(objs[0]);
			if (objsNumber == 2) {
				jsonArray.setObj2(objs[1]);
			}
			if (objsNumber == 3) {
				jsonArray.setObj2(objs[1]);
				jsonArray.setObj3(objs[2]);
			}

			jsonArrayList.add(jsonArray);
		}

		return jsonArrayList;
	}
	
	/**
	 * 转换json为map
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> convertJsonToMap(String json) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			JSONDeserializer deserializer = new JSONDeserializer();

			retMap = (Map<String, Object>) deserializer.deserialize(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 转换json为list
	 * 
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> convertJsonToList(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			JSONDeserializer deserializer = new JSONDeserializer()
			.use("values", HashMap.class);

			list = (List<Map<String, Object>>) deserializer.deserialize(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
