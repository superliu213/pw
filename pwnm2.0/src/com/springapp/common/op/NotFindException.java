package com.springapp.common.op;

import java.io.PrintStream;

/**
 * 
 * 
 */
public class NotFindException extends Exception {
	private static final long serialVersionUID = 1L;
	protected Exception exception;
	protected boolean fatal;

	/**
	 * 默认构造函数
	 */
	public NotFindException() {
	}

	/**
	 * 构造函数
	 * @param message 异常信息
	 */
	public NotFindException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * @param e 异常
	 */
	public NotFindException(Exception e) {
		this(e, e.getMessage());
	}

	/**
	 * 构造函数
	 * @param e 异常
	 * @param message 异常信息
	 */
	public NotFindException(Exception e, String message) {
		super(message);
		exception = e;
	}

	/**
	 * 构造函数
	 * @param e 异常
	 * @param message 异常信息
	 * @param fatal 是否致命异常
	 */
	public NotFindException(Exception e, String message, boolean fatal) {
		this(e, message);
		setFatal(fatal);
	}

	public boolean isFatal() {
		return fatal;
	}

	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}

	/**
	 * 打印异常
	 */
	public void printStackTrace() {
		super.printStackTrace();
		if (exception != null) {
			exception.printStackTrace();
		}
	}

	/**
	 * 打印异常
	 * @param printStream 打印流
	 */
	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
		if (exception != null) {
			exception.printStackTrace(printStream);
		}
	}

	/**
	 * 输出对象的字符串
	 */
	public String toString() {
		if (exception != null) {
			return super.toString() + " wraps: [" + exception.toString() + "]";
		} else {
			return super.toString();
		}
	}
}
