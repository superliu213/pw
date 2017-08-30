package com.springapp.common.op;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.springapp.common.application.ApplicationGlobalNames;
import com.springapp.common.constants.DBType;


/**
 * sql语句处理的实用类
 * 
 * 
 */
public class SqlRestrictions {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String oracleDateString = "yyyy-mm-dd hh24:mi:ss";
	
	/**
	 * 如果hm对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName
	 *            :字段名字
	 * @param hm
	 *            :查询的集合
	 * @param queryName
	 *            :查询字段的名字
	 * @return 部分sql
	 */
	public static String eq(String fieldName, HashMap<?, ?> hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " = '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param matchObject 比较的对象
	 * @return 部分sql
	 */
	public static String eq(String fieldName, Object matchObject) {
		String sqlWhere = "";
		if (matchObject != null) {
			if(matchObject instanceof java.lang.String && !matchObject.equals("")){
				sqlWhere = " and " + fieldName + " = '" + matchObject + "'";
			}else if(matchObject instanceof java.lang.Number){
				sqlWhere = " and " + fieldName + " = " + matchObject;
			}else if(matchObject instanceof java.sql.Date || matchObject instanceof java.util.Date){
				if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
					sqlWhere = " and " + fieldName + " = to_date('" + sdf.format(matchObject) + "','"+oracleDateString+"')";
				} else {
					sqlWhere = " and " + fieldName + " = '" + sdf.format(matchObject) + "'";
				}
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param hm 查询条件的hashmap对象
	 * @param queryName 条件字段名称
	 * @return 部分sql
	 */
	public static String ne(String fieldName, HashMap<?, ?> hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " <> '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param matchObject 比较对象 非字符串类型
	 * @return 部分sql
	 */
	public static String ne(String fieldName, Object matchObject) {
		String sqlWhere = "";
		if (matchObject != null) {
			if(matchObject instanceof java.lang.String && !matchObject.equals("")){
				sqlWhere = " and " + fieldName + " <> '" + matchObject + "'";
			}else if(matchObject instanceof java.lang.Number){
				sqlWhere = " and " + fieldName + " <> " + matchObject;
			}else if(matchObject instanceof java.sql.Date || matchObject instanceof java.util.Date){
				if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
					sqlWhere = " and " + fieldName + " <> to_date('" + sdf.format(matchObject) + "','"+oracleDateString+"')";
				} else {
					sqlWhere = " and " + fieldName + " <> '" + sdf.format(matchObject) + "')";
				}
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param hm 查询条件的哈希表
	 * @param queryName 查询条件名称
	 * @return 部分sql
	 */
	public static String gt(String fieldName, HashMap<?, ?> hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " > '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchMode对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param matchObject 比较对象 
	 * @return 部分sql
	 */
	public static String gt(String fieldName, Object matchObject) {
		String sqlWhere = "";
		if (matchObject != null) {
			if(matchObject instanceof java.lang.String && !matchObject.equals("")){
				sqlWhere = " and " + fieldName + " > '" + matchObject + "'";
			}else if(matchObject instanceof java.lang.Number){
				sqlWhere = " and  " + fieldName + " > " + matchObject;
			}else if(matchObject instanceof java.sql.Date || matchObject instanceof java.util.Date){
				if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
					sqlWhere = " and " + fieldName + " > to_date('" + sdf.format(matchObject) + "','"+oracleDateString+"')";
				} else {
					sqlWhere = " and " + fieldName + " > '" + sdf.format(matchObject) + "'";
				}
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param hm 查询条件的哈希表
	 * @param queryName 查询条件名称
	 * @return 部分sql
	 */
	public static String lt(String fieldName, HashMap<?, ?> hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " < '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时，将其连接成sql串，返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param matchObject 比较对象
	 * @return 部分sql
	 */
	public static String lt(String fieldName, Object matchObject) {
		String sqlWhere = "";
		if (matchObject != null) {
			if(matchObject instanceof java.lang.String && !matchObject.equals("")){
				sqlWhere = " and " + fieldName + " < '" + matchObject + "'";
			}else if(matchObject instanceof java.lang.Number){
				sqlWhere = " and " + fieldName + " < " + matchObject;
			}else if(matchObject instanceof java.sql.Date || matchObject instanceof java.util.Date){
				if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
					sqlWhere = " and " + fieldName + " < to_date('" + sdf.format(matchObject) + "','"+oracleDateString+"')";
				} else {
					sqlWhere = " and " + fieldName + " < '" + sdf.format(matchObject) + "'";
				}
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param hm 查询条件的哈希表
	 * @param queryName 查询条件名称
	 * @return 部分sql
	 */
	public static String ge(String fieldName, HashMap<?, ?> hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " >= '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param matchObject 比较对象
	 * @return 部分sql
	 */
	public static String ge(String fieldName, Object matchObject) {
		String sqlWhere = "";
		if (matchObject != null) {
			if(matchObject instanceof  java.lang.String && !matchObject.equals("")){
				sqlWhere = " and " + fieldName + " >= '" + matchObject + "'";
			}else if(matchObject instanceof java.lang.Number){
				sqlWhere = " and " + fieldName + " >= " + matchObject;
			}else if(matchObject instanceof java.sql.Date || matchObject instanceof java.util.Date){
				if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
					sqlWhere = " and " + fieldName + " >= to_date('" + sdf.format(matchObject) + "','"+oracleDateString+"')";
				} else {
					sqlWhere = " and " + fieldName + " >= '" + sdf.format(matchObject) + "'";
				}
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param hm 查询条件的哈希表
	 * @param queryName 查询条件名称
	 * @return 部分sql
	 */
	public static String le(String fieldName, HashMap<?, ?> hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " <= '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param matchObject 比较对象 
	 * @return 部分sql
	 */
	public static String le(String fieldName, Object matchObject) {
		String sqlWhere = "";
		if (matchObject != null) {
			if(matchObject instanceof java.lang.String && !matchObject.equals("")){
				sqlWhere = " and " + fieldName + " <= '" + matchObject + "'";
			}else if(matchObject instanceof java.lang.Number){
				sqlWhere = " and " + fieldName + " <= " + matchObject;
			}else if(matchObject instanceof java.sql.Date || matchObject instanceof java.util.Date){
				if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
					sqlWhere = " and " + fieldName + " <= to_date('" + sdf.format(matchObject) + "','"+oracleDateString+"')";
				} else {
					sqlWhere = " and " + fieldName + " <= '" + sdf.format(matchObject) + "'";
				}
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param hm 查询条件的哈希表
	 * @param loQueryName 小的查询条件名称
	 * @param hiQueryName 大的查询条件名称
	 * @param flag false为<=,true为<
	 * @return 部分sql
	 */
	public static String between(String fieldName, HashMap<?, ?> hm, String loQueryName,
			String hiQueryName, boolean flag) {
		String sqlWhere = "";
		if (hm.containsKey(loQueryName) && !hm.get(loQueryName).equals("")) {
			sqlWhere = " and " + fieldName + " >= '" + hm.get(loQueryName) + "'";
		}
		if (hm.containsKey(hiQueryName) && !hm.get(hiQueryName).equals("")) {
			if (!flag) {
				sqlWhere += " and " + fieldName + " < '" + hm.get(hiQueryName) + "'";
			} else {
				sqlWhere += " and " + fieldName + " <= '" + hm.get(hiQueryName) + "'";
			}
		}
		return sqlWhere;
	}

	public static String between(String fieldName, HashMap<?, ?> hm, String loQueryName,
			String hiQueryName) {
		return between(fieldName, loQueryName, hiQueryName, true);
	}
	
	/**
	 * 如果loMatchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param loMatchObject 小的比较对象  
	 * @param hiMatchObject 大的比较对象 
	 * @param flag true为<,false为<=
	 * @return 部分sql
	 */
	public static String between(String fieldName, Object loMatchObject, Object hiMatchObject, boolean flag) {
		String sqlWhere = "";
		if (loMatchObject != null) {
			if(loMatchObject instanceof java.lang.String && !loMatchObject.equals("")){
				sqlWhere = " and " + fieldName + " >= '" + loMatchObject + "'";
			}else if(loMatchObject instanceof java.lang.Number){
				sqlWhere = " and " + fieldName + " >= " + loMatchObject;
			}else if(loMatchObject instanceof java.sql.Date || loMatchObject instanceof java.util.Date){
				if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
					sqlWhere = " and " + fieldName + " >= to_date('" + sdf.format(loMatchObject)
							+ "','"+oracleDateString+"')";
				} else {
					sqlWhere = " and " + fieldName + " >='" + sdf.format(loMatchObject) + "' ";
				}
			}
		}
		if (hiMatchObject != null) {
			if (flag) {
				if(hiMatchObject instanceof java.lang.String && !hiMatchObject.equals("")){
					sqlWhere += " and " + fieldName + " < '" + hiMatchObject + "'";
				}else if(hiMatchObject instanceof java.lang.Number){
					sqlWhere += " and " + fieldName + " < " + hiMatchObject;
				}else if(hiMatchObject instanceof java.sql.Date || hiMatchObject instanceof java.util.Date){
					if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
						sqlWhere += " and " + fieldName + " < to_date('" + sdf.format(hiMatchObject)
									+ "','"+oracleDateString+"')";
					} else {
						sqlWhere += " and " + fieldName + " < '" + sdf.format(hiMatchObject) + "' ";
					}
				}
			} else {
				if(hiMatchObject instanceof java.lang.String && !hiMatchObject.equals("")){
					sqlWhere += " and " + fieldName + " <= '" + hiMatchObject + "'";
				}else if(hiMatchObject instanceof java.lang.Number){
					sqlWhere += " and " + fieldName + " <= " + hiMatchObject;
				}else if(hiMatchObject instanceof java.sql.Date || hiMatchObject instanceof java.util.Date){
					if (ApplicationGlobalNames.DB_TYPE.equals(DBType.DB_ORACLE)) {
						sqlWhere += " and " + fieldName + " <= to_date('" + sdf.format(hiMatchObject)
									+ "','"+oracleDateString+"')";
					} else {
						sqlWhere += " and " + fieldName + " <= '" + sdf.format(hiMatchObject) + "' ";
					}
				}
			}
		}
		return sqlWhere;
	}

	public static String between(String fieldName, Object loMatchObject, Object hiMatchObject){
		return between(fieldName, loMatchObject, hiMatchObject, false);
	}

	/**
	 * 连接字符串
	 * 
	 * @param fieldName 字段名称
	 * @param loMatchDate "yyyy-MM-dd" 小的比较对象
	 * @param hiMatchDate "yyyy-MM-dd" 大的比较对象
	 * @return 部分sql
	 */
	public static String betweenDate(String fieldName, String loMatchDate, String hiMatchDate) {
		String sqlWhere = "";
		if (loMatchDate != null && !loMatchDate.equals("")) {
			sqlWhere = " and " + fieldName + " >= '" + loMatchDate + " 00:00:00'";
		}
		if (hiMatchDate != null && !hiMatchDate.equals("")) {
			sqlWhere += " and " + fieldName + " <= '" + hiMatchDate + " 23:59:59'";
		}
		return sqlWhere;
	}
	
	/**
	 * 如果hm对象或值不为空时,将其连接成sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param hm 查询条件的哈希表
	 * @param queryName 查询条件名称
	 * @return 部分sql
	 */
	public static String in(String fieldName, HashMap<?, ?> hm, String queryName) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			sqlWhere = " and " + fieldName + " in '" + hm.get(queryName) + "'";
		}
		return sqlWhere;
	}

	/**
	 * 如果matchObject对象或值不为空时,将其连接成sql串，并返回一个String
	 *
	 * @param fieldName 字段名称
	 * @param matchObject 比较对象
     * eg: fieldName = "fieldName" ;matchObject = "v1,v2,v3";
	 * @return sqlWhere = " and fieldName in (v1,v2,v3)"
	 */
	public static String in(String fieldName, String matchObject) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
//			sqlWhere = " and " + fieldName + " in ('" + matchObject + "')";
			sqlWhere = " and " + fieldName + " in (" + matchObject + ")";
		}
		return sqlWhere;
	}
	
	/**
	 * @param fieldName
	 * @param matchObjects 同一类型
	 * @return
	 */
	public static String in(String fieldName, Object[] matchObjects) {
		String sqlWhere = "";
		String matchObjectsStr = "";
		
		if (matchObjects != null && !matchObjects.equals("")) {
			int len = matchObjects.length;
			for (int i = 0; i < len; i++) {
				if(matchObjects[i] instanceof java.lang.String){
					matchObjectsStr += "'" + matchObjects[i] + "'";
				}else{
					matchObjectsStr += matchObjects[i];
				}
				if (i != len -1) {
					matchObjectsStr += ",";
				}
			}
		}
		
		if (!matchObjectsStr.equals("")) {
			sqlWhere = " and " + fieldName + " in (" + matchObjectsStr + ")";
		}
		
		return sqlWhere;
	}

	/**
	 * 如果hm对象或值不为空时，将其连接成匹配的sql串，并返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param hm 查询条件的hashmap
	 * @param queryName 查询条件的hash名称
	 * @param matchMode
	 *            :1-不加% 2-左边加% 3-两边加% 4-右边加%
	 * @return 部分sql
	 */
	public static String like(String fieldName, HashMap<?, ?> hm, String queryName, int matchMode) {
		String sqlWhere = "";
		if (hm.containsKey(queryName) && !hm.get(queryName).equals("")) {
			if (matchMode == LikeMatchMode.NOTADD) {
				sqlWhere = " and " + fieldName + " like '" + hm.get(queryName) + "'";
			} else if (matchMode == LikeMatchMode.LEFTADD) {
				sqlWhere = " and " + fieldName + " like '%" + hm.get(queryName) + "'";
			} else if (matchMode == LikeMatchMode.BOTHADD) {
				sqlWhere = " and " + fieldName + " like '%" + hm.get(queryName) + "%'";
			} else if (matchMode == LikeMatchMode.RIGHTADD) {
				sqlWhere = " and " + fieldName + " like '" + hm.get(queryName) + "%'";
			}
		}
		return sqlWhere;
	}

	/**
	 * 如果matchMode对象或值不为空时，将其连接成匹配的sql串，返回一个String
	 * 
	 * @param fieldName 字段名称
	 * @param matchObject 比较对象
	 * @param matchMode 比较模式
	 * @return 部分sql
	 */
	public static String like(String fieldName, String matchObject, int matchMode) {
		String sqlWhere = "";
		if (matchObject != null && !matchObject.equals("")) {
			if (matchMode == LikeMatchMode.NOTADD) {
				sqlWhere = " and " + fieldName + " like '" + matchObject + "'";
			} else if (matchMode == LikeMatchMode.LEFTADD) {
				sqlWhere = " and " + fieldName + " like '%" + matchObject + "'";
			} else if (matchMode == LikeMatchMode.BOTHADD) {
				sqlWhere = " and " + fieldName + " like '%" + matchObject + "%'";
			} else if (matchMode == LikeMatchMode.RIGHTADD) {
				sqlWhere = " and " + fieldName + " like '" + matchObject + "%'";
			}
		}
		return sqlWhere;
	}
	
	/**
	 * 如果fieldName的值不为空，将sqlWhere进行排序，返回一个S
	 * 
	 * @param fieldName 字段名称
	 * @param orderType
	 *            :1-升序 2-降序
	 * @return 部分sql
	 */
	public static String order(String fieldName, int orderType) {
		String sqlWhere = " order by " + fieldName;
		if (!fieldName.equals("")) {
			if (orderType != 1) {
				sqlWhere += " desc ";
			}
		}
		return sqlWhere;
	}

	/**
	 * 返回一个排序后的String
	 * 
	 * @param fieldName 字段名称
	 * @return 部分sql
	 */
	public static String order(String fieldName) {
		return order(fieldName, 1);
	}
}