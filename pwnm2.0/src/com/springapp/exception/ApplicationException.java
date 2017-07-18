package com.springapp.exception;

/**
 * 应用程序异常类
 * 
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;
	
	/**
	 * 默认的构造方法
	 * 
	 */
	public ApplicationException() {
		this.errorCode = -1;
	}
	
	/**
	 * 带一个参数String参数的构造方法
	 * 
	 * @param errorMessage 错误描述
	 */
	public ApplicationException(String errorMessage) {
		super(errorMessage);
		this.errorCode = -1;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * 带一个String和一个Throwable参数的构造方法
	 * 
	 * @param errorMessage 错误描述
	 * @param cause 异常
	 */
	public ApplicationException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorCode = -1;
		this.errorMessage = errorMessage;
	}

	/**
	 * 带错误代码、错误描述和异常的构造方法
	 * 
	 * @param errorCode 错误代码
	 * @param errorMessage 错误描述
	 * @param cause 异常
	 */
	public ApplicationException(int errorCode, String errorMessage,
								Throwable cause) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * 带一个Throwable的构造方法
	 * 
	 * @param cause 异常
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
		this.errorCode = -1;
	}
	
	/**
	 * 
	 * @return 错误带啊吗
	 */
	public int getErrorCode() {
		return errorCode;
	}
	
	/**
	 * 
	 * @param errorCode 错误代码
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * 
	 * @return 错误描述
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * 
	 * @param errorMessage 错误描述
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
