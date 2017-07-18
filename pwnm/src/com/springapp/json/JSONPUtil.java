package com.springapp.json;

import com.springapp.exception.ApplicationException;

import java.util.Map;

/**
 * JSONP数据处理的工具类(回调函数定死为callback)
 * 
 * 
 */
public class JSONPUtil {
	/**
	 * 把Java对象序列化成JSON字符串
	 * 
	 * @param obj 对象数据
	 * @return JSON字符串
	 */
	public static String getJSONString(Object obj) {
		String jsonString = JSONUtil.getJSONString(obj);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 把Java对象序列化成JSON字符串
	 * 
	 * @param obj 对象数据
	 * @return JSON字符串
	 */
	public static String getDeepJSONString(Object obj) {
		String jsonString = JSONUtil.getDeepJSONStringOUsingExclude(obj, "class");
		
		return "callback(" + jsonString + ");";
	}
	
	/**
	 * 把Java对象序列化成JSON字符串（可排除指定的字段）
	 * 
	 * @param obj 对象数据
	 * @param String变参  excludes 排除的字段变参
	 * @return JSON字符串
	 */
	public static String getDeepJSONStringOUsingExclude(Object obj, String...excludes) {
		String jsonString = JSONUtil.getDeepJSONStringOUsingExclude(obj, excludes);
		
		return "callback(" + jsonString + ");";
	}
	
	/**
	 * 把Java对象序列化成JSON字符串（可包含指定的字段）
	 * 
	 * @param obj 对象数据
	 * @param String变参  includes 包含的字段变参
	 * @return JSON字符串
	 */
	public static String getDeepJSONStringOUsingInclude(Object obj, String...includes) {
		String jsonString = JSONUtil.getDeepJSONStringOUsingInclude(obj, includes);
		
		return "callback(" + jsonString + ");";
	}
	
	/**
	 * 由JSONData序列化成JSON字符串
	 * 
	 * @param jsonData json对象
	 * @return JSON字符串
	 */
	public static String getJSONString(JSONData jsonData) {
		String jsonString = JSONUtil.getJSONString(jsonData);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 由JSONData序列化成JSON字符串
	 * 
	 * @param jsonData json对象
	 * @return JSON字符串
	 */
	public static String getDeepJSONString(JSONData jsonData) {
		String jsonString = JSONUtil.getDeepJSONString(jsonData);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 由ApplicationException得到JSON字符串
	 * 
	 * @param appException app异常
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(ApplicationException appException) {
		String jsonString = JSONUtil.getErrorJSONString(appException);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 由ApplicationException和url得到JSON字符串
	 * 
	 * @param appException app异常
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(ApplicationException appException, String url) {
		String jsonString = JSONUtil.getErrorJSONString(appException, url);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 由ApplicationException得到JSON字符串
	 * 
	 * @param ex 异常
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(Exception ex) {
		String jsonString = JSONUtil.getErrorJSONString(ex);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 由ApplicationException和url得到JSON字符串
	 * 
	 * @param ex 异常
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(Exception ex, String url) {
		String jsonString = JSONUtil.getErrorJSONString(ex, url);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 得到错误JSON字符串
	 * 
	 * @param message 信息
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(String message) {
		String jsonString = JSONUtil.getErrorJSONString(message);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 得到错误JSON字符串
	 * 
	 * @param message 消息
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getErrorJSONString(String message, String url) {
		String jsonString = JSONUtil.getErrorJSONString(message, url);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 得到成功JSON字符串
	 * 
	 * @param message 信息
	 * @return JSON字符串
	 */
	public static String getSuccessJSONString(String message) {
		String jsonString = JSONUtil.getSuccessJSONString(message);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 得到成功JSON字符串
	 * 
	 * @param message 信息
	 * @param url url
	 * @return JSON字符串
	 */
	public static String getSuccessJSONString(String message, String url) {
		String jsonString = JSONUtil.getSuccessJSONString(message, url);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 陈功标记
	 * @param message 信息
	 * @return JSON字符串
	 */
	public static String getJSONString(Boolean isSuccess, String message) {
		String jsonString = JSONUtil.getJSONString(isSuccess, message);
		
		return "callback(" + jsonString + ");";
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
		String jsonString = JSONUtil.getJSONString(isSuccess, message, url);
		
		return "callback(" + jsonString + ");";
	}

	/**
	 * 得到JSON字符串
	 * 
	 * @param message 信息
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public static String getSuccessJSONString(String message, Map<String,Object> param) {
		String jsonString = JSONUtil.getJSONString(true, message, null, param);
		
		return "callback(" + jsonString + ");";
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
		String jsonString = JSONUtil.getJSONString(true, message, url, param);
		
		return "callback(" + jsonString + ");";
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
		String jsonString = JSONUtil.getJSONString(isSuccess, message, null, param);
		
		return "callback(" + jsonString + ");";
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
		String jsonString = JSONUtil.getJSONString(isSuccess, message, url, param);
		
		return "callback(" + jsonString + ");";
	}
	
}
