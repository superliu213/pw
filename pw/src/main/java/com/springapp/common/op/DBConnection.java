package com.springapp.common.op;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBConnection {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DBConnection.class);

	/**
	 * 得到数据库连接
	 * 
	 * @param driveClassName
	 *            数据库驱动
	 * @param url
	 *            url
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 数据库连接
	 * @throws OPException
	 */
	public static Connection getConnection(String driveClassName, String url,
			String username, String password) throws OPException {
		Connection conn = null;
		try {
			Class.forName(driveClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			String errorMessage = "加载数据库驱动:'" + driveClassName + "' 出错!";
			// logger.error(errorMessage);
			throw new OPException(errorMessage);
		}

		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			String errorMessage = "连接数据库(url = '" + url + "',username = '"
					+ username + "',password = '" + password + "')出错!";
			// logger.error(errorMessage);
			throw new OPException(errorMessage);
		}

		return conn;
	}

	/**
	 * 释放数据库连接
	 * 
	 * @param rs
	 *            记录集
	 * @param stmt
	 *            声明
	 * @param conn
	 *            数据库连接
	 */
	public static void free(ResultSet rs, Statement stmt, Connection conn)
			throws OPException {
		try {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("释放ResultSet失败！");
					throw new OPException(e.getMessage());
				}
		} finally {
			try {
				if (stmt != null)
					try {
						stmt.close();
					} catch (SQLException e) {
						logger.error("释放Statement失败！");
						throw new OPException(e.getMessage());
					}
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						logger.error("释放Connection失败！");
						throw new OPException(e.getMessage());
					}
			}
		}
	}

	/**
	 * 释放数据库连接
	 * 
	 * @param stmt
	 *            声明
	 * @param conn
	 *            数据库连接
	 */
	public static void free(Statement stmt, Connection conn) throws OPException {
		free(null, stmt, conn);
	}

	/**
	 * 释放数据库连接
	 * 
	 * @param conn
	 *            数据库连接
	 */
	public static void free(Connection conn) throws OPException {
		free(null, null, conn);
	}

}
