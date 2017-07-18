package com.springapp.json;

import java.io.Serializable;

/**
 * JSON数据对象，可用JSONUtil转换成JSON String
 *
 */
public class JSONData implements Serializable {
	private static final long serialVersionUID = 1L;

	//记录集总页数
	private Long totalCount;
	
	//实体对象或对象集
	private Object data;

	//成功标记
	private Boolean success;
	
	//json代码
	private Long code;
		
	//json信息
	private String message;

	/**
	 * 默认构造函数
	 */
	public JSONData() {
	}

	/**
	 * 构造函数
	 * @param data 对象数据
	 */
	public JSONData(Object data) {
		this.data = data;
		this.success = true;
	}

	/**
	 * 构造函数
	 * @param totalCount 总的记录数
	 * @param data 对象数据
	 */
	public JSONData(Long totalCount, Object data) {
		this.totalCount = totalCount;
		this.data = data;
		this.success = true;
	}

	/**
	 * 构造函数
	 * @param totalCount 总的记录数
	 * @param data 对象数据
	 * @param success 成功标记
	 */
	public JSONData(Long totalCount, Object data, Boolean success) {
		this.totalCount = totalCount;
		this.data = data;
		this.success = success;
	}

	/**
	 * 构造函数
	 * @param totalCount 总的记录数
	 * @param data 对象数据
	 * @param success 成功标记
	 * @param message 信息
	 */
	public JSONData(Long totalCount, Object data, Boolean success, String message) {
		this.totalCount = totalCount;
		this.data = data;
		this.success = success;
		this.message = message;
	}

	/**
	 * 构造函数
	 * @param data 对象数据
	 * @param message 信息
	 */
	public JSONData(Object data, String message) {
		this.totalCount = new Long(1);
		this.data = data;
		this.success = true;
		this.message = message;
	}
	
	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Object getData() {
		/*
		if(data == null){
			data = new ArrayList();
		}*/
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
