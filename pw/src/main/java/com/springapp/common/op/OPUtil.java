package com.springapp.common.op;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;

/**
 * 把Exception转换为OPException
 * 
 * 
 */
public class OPUtil {
	/**
	 * 把未知异常转换成OPException
	 * @param e 异常
	 * @return
	 */
	public static OPException handleException(Exception e) {
		if (e instanceof JDBCException) {
			return new OPException((HibernateException) e);
		} else if (e instanceof HibernateException) {
			return new OPException((HibernateException) e);
		} else {
			return new OPException(e);
		}
	}
}
