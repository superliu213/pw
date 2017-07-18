package com.springapp.json;

import com.springapp.exception.ApplicationException;

import java.util.HashMap;

/**
 * JSON扩展的数据处理的工具类
 * 
 * 
 */
public class JSONExtSupport {
	private String callback = null;
	private boolean jsonP = false;
	
	public JSONExtSupport(){
		callback = null;
		jsonP = false;
	}
	
	public JSONExtSupport(String callback, boolean jsonP){
		this.callback = callback;
		this.jsonP = jsonP;
	}
	
	public JSONExtSupport(String callback){
		this.callback = callback;
		if (callback != null) {
			jsonP = true;
		}
	}
	
	/**
	 * 把Java对象序列化成JSON字符串
	 * 
	 * @param obj 对象数据
	 * @return JSON字符串
	 */
	public String getJSONString(Object obj) {
		String jsonString = JSONUtil.getJSONString(obj);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 把Java对象序列化成JSON字符串
	 * 
	 * @param obj 对象数据
	 * @return JSON字符串
	 */
	public String getDeepJSONString(Object obj) {
		String jsonString = JSONUtil.getDeepJSONStringOUsingExclude(obj, "class");
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}
	
	/**
	 * 把Java对象序列化成JSON字符串（可排除指定的字段）
	 * 
	 * @param obj 对象数据
	 * @param String变参  excludes 排除的字段变参
	 * @return JSON字符串
	 */
	public String getDeepJSONStringOUsingExclude(Object obj, String...excludes) {
		String jsonString = JSONUtil.getDeepJSONStringOUsingExclude(obj, excludes);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}
	
	/**
	 * 把Java对象序列化成JSON字符串（可包含指定的字段）
	 * 
	 * @param obj 对象数据
	 * @param String变参  includes 包含的字段变参
	 * @return JSON字符串
	 */
	public String getDeepJSONStringOUsingInclude(Object obj, String...includes) {
		String jsonString = JSONUtil.getDeepJSONStringOUsingInclude(obj, includes);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}
	
	/**
	 * 由JSONData序列化成JSON字符串
	 * 
	 * @param jsonData json对象
	 * @return JSON字符串
	 */
	public String getJSONString(JSONData jsonData) {
		String jsonString = JSONUtil.getJSONString(jsonData);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 由JSONData序列化成JSON字符串
	 * 
	 * @param jsonData json对象
	 * @return JSON字符串
	 */
	public String getDeepJSONString(JSONData jsonData) {
		String jsonString = JSONUtil.getDeepJSONString(jsonData);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 由ApplicationException得到JSON字符串
	 * 
	 * @param appException app异常
	 * @return JSON字符串
	 */
	public String getErrorJSONString(ApplicationException appException) {
		String jsonString = JSONUtil.getErrorJSONString(appException);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 由ApplicationException和url得到JSON字符串
	 * 
	 * @param appException app异常
	 * @param url url
	 * @return JSON字符串
	 */
	public String getErrorJSONString(ApplicationException appException, String url) {
		String jsonString = JSONUtil.getErrorJSONString(appException, url);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 由ApplicationException得到JSON字符串
	 * 
	 * @param ex 异常
	 * @return JSON字符串
	 */
	public String getErrorJSONString(Exception ex) {
		String jsonString = JSONUtil.getErrorJSONString(ex);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 由ApplicationException和url得到JSON字符串
	 * 
	 * @param ex 异常
	 * @param url url
	 * @return JSON字符串
	 */
	public String getErrorJSONString(Exception ex, String url) {
		String jsonString = JSONUtil.getErrorJSONString(ex, url);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 得到错误JSON字符串
	 * 
	 * @param message 信息
	 * @return JSON字符串
	 */
	public String getErrorJSONString(String message) {
		String jsonString = JSONUtil.getErrorJSONString(message);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 得到错误JSON字符串
	 * 
	 * @param message 消息
	 * @param url url
	 * @return JSON字符串
	 */
	public String getErrorJSONString(String message, String url) {
		String jsonString = JSONUtil.getErrorJSONString(message, url);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 得到成功JSON字符串
	 * 
	 * @param message 信息
	 * @return JSON字符串
	 */
	public String getSuccessJSONString(String message) {
		String jsonString = JSONUtil.getSuccessJSONString(message);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 得到成功JSON字符串
	 * 
	 * @param message 信息
	 * @param url url
	 * @return JSON字符串
	 */
	public String getSuccessJSONString(String message, String url) {
		String jsonString = JSONUtil.getSuccessJSONString(message, url);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 陈功标记
	 * @param message 信息
	 * @return JSON字符串
	 */
	public String getJSONString(Boolean isSuccess, String message) {
		String jsonString = JSONUtil.getJSONString(isSuccess, message);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
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
	public String getJSONString(Boolean isSuccess, String message, String url) {
		String jsonString = JSONUtil.getJSONString(isSuccess, message, url);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}

	/**
	 * 得到JSON字符串
	 * 
	 * @param message 信息
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public String getSuccessJSONString(String message, HashMap<String,Object> param) {
		String jsonString = JSONUtil.getJSONString(true, message, null, param);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param message 信息
	 * @param url url
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public String getSuccessJSONString(String message, String url, HashMap<String,Object> param) {
		String jsonString = JSONUtil.getJSONString(true, message, url, param);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}
	
	/**
	 * 得到JSON字符串
	 * 
	 * @param isSuccess 成功标记
	 * @param message 信息
	 * @param param 额外参数
	 * @return JSON字符串
	 */
	public String getJSONString(Boolean isSuccess, String message, HashMap<String,Object> param) {
		String jsonString = JSONUtil.getJSONString(isSuccess, message, null, param);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
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
	public String getJSONString(Boolean isSuccess, String message, String url, HashMap<String,Object> param) {
		String jsonString = JSONUtil.getJSONString(isSuccess, message, url, param);
		if (jsonP) {
			jsonString = callback + "(" + jsonString + ");";
		}
		
		return jsonString;
	}
	
}
