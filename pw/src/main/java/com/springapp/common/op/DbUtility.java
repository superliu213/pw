package com.springapp.common.op;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.springapp.common.util.StringUtil;


/**
 * JDBC操作实用类
 * 
 * 
 */
public class DbUtility {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DbUtility.class);

	/**
	 * 执行存储函数或存储过程，返回输出参数。假设输出参数为第一个参数
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call
	 *            <ProcName>(?, <ParamList>)}
	 * @param returnType
	 *            输出参数类型
	 * @return 输出参数的值
	 */
	public static Object execStoredFunction(Connection cnt, String sql,
			int returnType) throws OPException {
		CallableStatement stmt = null;
		Object obj = null;
		try {
			stmt = cnt.prepareCall(sql);

			stmt.registerOutParameter(1, returnType);
			stmt.execute();
			obj = stmt.getObject(1);

			if (stmt != null) {
				stmt.close();
			}
			logger.debug("execStoredFunction succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execStoredFunction failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return obj;
	}

	/**
	 * 执行存储函数或存储过程并返回指定字段的值。此时存储过程或函数只能返回单记录
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call
	 *            <ProcName>(?, <ParamList>)}
	 * @param fieldName
	 *            字段名称
	 * @param fieldType
	 *            字段类型
	 * @return 字段值。若存储过程返回空记录，则字段值为 null；存储过程返回多记录，则字段值取第一行记录的值
	 */
	public static Object execStoredFunction(Connection cnt, String sql,
			String fieldName, int fieldType) throws OPException {
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		Object fieldValue = null;
		try {
			stmt = cnt.prepareCall(sql);
			stmt.registerOutParameter(1, java.sql.Types.OTHER);
			stmt.execute();
			resultSet = (ResultSet) stmt.getObject(1);

			if (resultSet != null) {
				switch (fieldType) {
				case java.sql.Types.DECIMAL:
					fieldValue = resultSet.getBigDecimal(fieldName);
					break;
				case java.sql.Types.VARCHAR:
					fieldValue = resultSet.getString(fieldName);
					break;
				case java.sql.Types.DATE:
					fieldValue = resultSet.getTimestamp(fieldName);
					break;
				default:
					fieldValue = resultSet.getString(fieldName);
					break;
				}
			}

			if (resultSet != null) {
				resultSet.close();
			}

			if (stmt != null) {
				stmt.close();
			}

			logger.debug("execStoredFunction succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execStoredFunction failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return fieldValue;
	}

	/**
	 * 执行存储函数或存储过程并返回结果集
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call
	 *            <ProcName>(?, <ParamList>)}
	 * @return 对象记录集
	 */
	public static ResultSet execStoredFunction(Connection cnt, String sql)
			throws OPException {
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		try {
			stmt = cnt.prepareCall(sql);
			stmt.registerOutParameter(1, -10);
			stmt.execute();
			resultSet = (ResultSet) stmt.getObject(1);
			if (stmt != null) {
				stmt.close();
			}
			if (cnt != null) {
				cnt.close();
			}

			logger.debug("execStoredFunction succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execStoredFunction failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return resultSet;
	}

	/**
	 * 执行存储函数或存储过程并返回结果集
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call
	 *            <ProcName>(?, <ParamList>)}
	 * @return HashMap对象的记录集
	 */
	public static List<HashMap<String, Object>> execStoredFunctionForList(
			Connection cnt, String sql) throws OPException {
		CallableStatement stmt = null;
		ResultSet resultSet = null;
		List<HashMap<String, Object>> list = null;

		try {
			stmt = cnt.prepareCall(sql);
			stmt.registerOutParameter(1, -10);
			stmt.execute();
			resultSet = (ResultSet) stmt.getObject(1);

			if (resultSet != null) {
				list = new ArrayList<HashMap<String, Object>>();
				ResultSetMetaData resultMetaData = resultSet.getMetaData();
				int fieldCount = resultMetaData.getColumnCount();
				while (resultSet.next()) {
					HashMap<String, Object> hm = new HashMap<String, Object>();
					for (int i = 1; i <= fieldCount; i++) {
						String fieldName = resultMetaData.getColumnName(i);
						Object fieldValue = resultSet.getObject(fieldName);
						if (resultMetaData.getColumnType(i) == Types.VARCHAR
								&& fieldValue == null) {
							fieldValue = "";
						}
						hm.put(fieldName, fieldValue);
					}
					list.add(hm);
				}
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
			logger.error("execStoredFunctionForList failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (Exception e) {
				logger.error("关闭记录集出错!");
			}
		}
		return list;
	}

	/**
	 * 执行存储过程，不返回结果集
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            存储过程的调用语句，格式：{call <ProcName>(<ParamList>)}
	 */
	public static void execStoredProcedure(Connection cnt, String sql)
			throws OPException {
		Statement stmt = null;
		try {
			stmt = cnt.createStatement();
			stmt.executeUpdate(sql);

			if (stmt != null) {
				stmt.close();
			}

			logger.debug("execStoredProcedure succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execStoredProcedure failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
	}

	/**
	 * 执行sql，不返回结果集
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sqls
	 *            更新sql语句
	 */
	public static int[] execUpdate(Connection cnt, String[] sqls)
			throws OPException {
		Statement stmt = null;
		try {
			stmt = cnt.createStatement();
			for (int i = 0; i < sqls.length; i++) {
				stmt.addBatch(sqls[i]);
			}

			logger.debug("execUpdate succeed. sql: "
					+ StringUtil.getArrayString(sqls));
			return stmt.executeBatch();
		} catch (SQLException ex) {
			logger.error("execUpdate failed. sql: "
					+ StringUtil.getArrayString(sqls));
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
	}

	/**
	 * 根据sql获取列表
	 * 
	 * @param sql
	 *            sql
	 * @param params
	 *            定位参数
	 * @throws OPException
	 */
	public static int execUpdate(Connection cnt, String sql, Object... params)
			throws OPException {
		PreparedStatement stmt = null;
		try {
			stmt = cnt.prepareStatement(sql);
			prepareParams(stmt, params);
			logger.debug("execUpdate succeed. sql: " + sql);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("execUpdate failed. sql: " + sql);
			throw OPUtil.handleException(e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
	}

	private static void prepareParams(PreparedStatement stmt, Object[] params)
			throws SQLException {
		if (params == null) {
			return;
		}

		int i = 0;
		for (Object param : params) {
			if (param != null && param instanceof Date) {
				stmt.setTimestamp(++i, new Timestamp(((Date) param).getTime()));
			} else {
				stmt.setObject(++i, param);
			}
		}
	}

	/**
	 * 执行sql，返回结果集
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            更新sql语句
	 * @return HashMap对象的记录集
	 */
	public static List<HashMap<String, Object>> execQueryList(Connection cnt,
			String sql, Object... params) throws OPException {
		return execQueryList(cnt, 10000, sql, params); // 最多取10000条记录;
	}

	public static List<HashMap<String, Object>> execQueryList(Connection cnt,
			int limit, String sql, Object... params) throws OPException {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		List<HashMap<String, Object>> list = null;
		try {
			stmt = cnt.prepareStatement(sql);
			prepareParams(stmt, params);
			resultSet = stmt.executeQuery();
			if (resultSet != null) {
				list = new ArrayList<HashMap<String, Object>>();
				ResultSetMetaData resultMetaData = resultSet.getMetaData();
				int fieldCount = resultMetaData.getColumnCount();
				int index = 0;
				while (resultSet.next()) {
					if (limit == index++) {
						break;
					}
					HashMap<String, Object> hm = new HashMap<String, Object>();
					for (int i = 1; i <= fieldCount; i++) {
						String fieldName = resultMetaData.getColumnName(i);
						Object fieldValue = resultSet.getObject(fieldName);
						if (resultMetaData.getColumnType(i) == Types.VARCHAR
								&& fieldValue == null) {
							fieldValue = "";
						}
						hm.put(fieldName.toUpperCase(), fieldValue);
					}
					list.add(hm);
				}
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			logger.debug("execQueryList succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execQueryList failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return list;
	}

	/**
	 * 执行sql，返回结果集
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            更新sql语句
	 * @return HashMap对象的记录集
	 */
	public static Map<Object, Object> execQueryMap(Connection cnt, String sql,
			Object... params) throws OPException {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			stmt = cnt.prepareStatement(sql);
			prepareParams(stmt, params);
			resultSet = stmt.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					Object fieldValue = resultSet.getObject(2);
					map.put(resultSet.getObject(1), fieldValue == null ? ""
							: fieldValue);
				}
				resultSet.close();
			}
			logger.debug("execQueryList succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("execQueryList failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return map;
	}

	/**
	 * 返回记录数量
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            更新sql语句
	 * @return 记录数量
	 */
	public static int getRowCount(Connection cnt, String sql, Object... params)
			throws OPException {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			stmt = cnt.prepareStatement(sql);
			prepareParams(stmt, params);

			resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt(1);
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}

			logger.debug("getRowCount succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("getRowCount failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return count;
	}

	/**
	 * 执行sql，返回大对象的字节码
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            sql语句
	 * @return 大对象的字节码
	 */
	public static byte[] getDataStream(Connection cnt, String sql,
			Object... params) throws OPException {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		byte[] b = null;
		try {
			stmt = cnt.prepareStatement(sql);
			prepareParams(stmt, params);

			resultSet = stmt.executeQuery();
			if (resultSet != null) {
				if (resultSet.next()) {
					b = resultSet.getBytes(1);
				}
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			logger.debug("getDataStream succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("getDataStream failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return b;
	}

	/**
	 * 执行sql，返回blob的字节
	 * 
	 * @param cnt
	 *            数据库连接
	 * @param sql
	 *            sql语句
	 * @return blob的字段
	 */
	public static InputStream getBlob(Connection cnt, String sql,
			Object... params) throws OPException {
		PreparedStatement stmt = null;

		ResultSet resultSet = null;
		InputStream ret = null;
		try {
			stmt = cnt.prepareStatement(sql);
			prepareParams(stmt, params);

			resultSet = stmt.executeQuery();
			if (resultSet != null) {
				if (resultSet.next()) {
					Blob blob = resultSet.getBlob(1);
					ret = blob.getBinaryStream();
				}
				resultSet.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			logger.debug("getBlob succeed. sql: " + sql);
		} catch (SQLException ex) {
			logger.error("getBlob failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} catch (Exception ex) {
			logger.error("getBlob failed. sql: " + sql);
			throw new OPException(ex.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("释放对象错误!");
			}
		}
		return ret;
	}

}