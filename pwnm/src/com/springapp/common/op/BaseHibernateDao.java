package com.springapp.common.op;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.springapp.common.application.ApplicationGlobalNames;
import com.springapp.common.op.work.ExecSqlQueryListWork;
import com.springapp.common.op.work.ExecSqlUpdateParamWork;
import com.springapp.common.op.work.ExecSqlUpdateWork;
import com.springapp.common.op.work.ExecStoredFunctionForListWork;
import com.springapp.common.op.work.ExecStoredFunctionWork;
import com.springapp.common.op.work.ExecStoredProcedureWork;
import com.springapp.common.op.work.GetBlobWork;
import com.springapp.common.op.work.GetDataStreamWork;
import com.springapp.common.op.work.GetSqlCountWork;
import com.springapp.common.util.StringUtil;

/**
 * 基于HibernateDaoSupport的基本数据库操作类，所有的BP都集成此类
 * 
 */
public class BaseHibernateDao {

	private static final Log logger = LogFactory.getLog(BaseHibernateDao.class);

	private static final Object[] NO_ARGS = new Object[0];

	private static final Type[] NO_TYPES = new Type[0];

	private static final String[] NO_PARANAMES = new String[0];

	private static final Object[] NO_PARAVALUES = new Object[0];

	public static final String QUERY_TOTAL = "total";
	public static final String QUERY_LIST = "list";

	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// 事务必须是开启的，否则获取不到
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存对象集合
	 * 
	 * @param obj
	 * @throws OPException
	 */
	public void saveObj(Object obj) throws OPException {
		saveObjs(new Object[] { obj });
	}

	/**
	 * 保存对象数组
	 * 
	 * @param objs
	 * @throws OPException
	 */
	public void saveObjs(Object... objs) throws OPException {
		if (objs == null) {
			throw new OPException("保存失败，持久化对象为空！");
		}
		try {
			Session session = getSession();
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] == null) {
					throw new OPException("保存失败，持久化对象为空！");
				}
				session.save(objs[i]);
				// getHibernateTemplate().flush();
			}
			session.flush();
			session.clear();// 清除内部缓存的全部数据，及时释放出占用的内存
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存对象数据集
	 * 
	 * @param objList
	 * @throws OPException
	 */
	public void saveObjByList(List objList) throws OPException {
		int size = objList.size();
		Object[] arr = (Object[]) objList.toArray(new Object[size]);
		saveObjs(arr);
	}

	/**
	 * 保存或更新对象
	 * 
	 * @param saveOrUpdate
	 * @throws OPException
	 */
	public void saveOrUpdateObj(Object saveOrUpdate) throws OPException {
		try {
			if (saveOrUpdate == null) {
				throw new OPException("保存失败，持久化对象为空！");
			}
			getSession().saveOrUpdate(saveOrUpdate);
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存或更新对象数组
	 * 
	 * @param saveAndUpdates
	 * @throws OPException
	 */
	public void saveOrUpdateObjs(Object... saveOrUpdates) throws OPException {
		try {
			Session session = getSession();
			if (saveOrUpdates != null) {
				for (int i = 0; i < saveOrUpdates.length; i++) {
					if (saveOrUpdates[i] == null) {
						throw new OPException("保存失败，持久化对象为空！");
					}
					session.saveOrUpdate(saveOrUpdates[i]);
					session.flush();
					session.clear();// 清除内部缓存的全部数据，及时释放出占用的内存
					// getHibernateTemplate().flush();
				}
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 更新对象
	 * 
	 * @param obj
	 * @throws OPException
	 */
	public void updateObj(Object obj) throws OPException {
		updateObjs(new Object[] { obj });
	}

	/**
	 * 更新对象数组
	 * 
	 * @param objs
	 * @throws OPException
	 */
	public void updateObjs(Object... objs) throws OPException {
		if (objs == null) {
			throw new OPException("持久化对象为空！");
		}
		try {
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] == null) {
					throw new OPException("更新失败，持久化对象为空！");
				}
				getSession().update(objs[i]);
				// getHibernateTemplate().flush();
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 根据pk删除对象
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @throws OPException
	 */
	public void removeObj(Class<?> c, Serializable id) throws OPException {
		try {
			Object obj = getSession().load(c, id);
			getSession().delete(obj);
		} catch (HibernateException e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 * @throws OPException
	 */
	public void removeObj(Object obj) throws OPException {
		removeObjs(new Object[] { obj });
	}

	/**
	 * 删除对象数组
	 * 
	 * @param objs
	 * @throws OPException
	 */
	public void removeObjs(Object... objs) throws OPException {
		if (objs == null) {
			throw new OPException("持久化对象为空！");
		}
		try {
			// getSession().deleteAll(objs);
			for (Object object : objs) {
				getSession().delete(object);
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 删除对象集合
	 * 
	 * @param objs
	 *            对象集合 Collection
	 * @throws OPException
	 */
	public void removeObjs(Collection<?> objs) throws OPException {
		if (objs == null) {
			throw new OPException("持久化对象为空！");
		}
		try {
			// getSession().deleteAll(objs);
			for (Object object : objs) {
				getSession().delete(object);
			}
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 根据HQL找回对象集合
	 * 
	 * @param hql
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> retrieveObjs(String hql) throws OPException {
		return retrieveObjs(hql, NO_PARANAMES, NO_PARAVALUES, NO_TYPES);
	}

	/**
	 * 根据HQL找回对象集合
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param paraTypes
	 *            参数类型
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> retrieveObjs(String hql, String[] paraNames, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		List<?> result = null;
		logger.debug("======##==hql: " + hql);
		try {
			Query query = getSession().createQuery(hql);

			if (paraNames != null) {
				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
					if (paraValues[i] != null) {
						if (paraTypes == null || paraTypes.length == 0) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i], paraTypes[i]);
						}
					}
				}
			}
			result = query.list();
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		}
		if (result.iterator().hasNext()) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 根据HQL找回对象集合
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> retrieveObjs(String hql, String[] paraNames, Object[] paraValues) throws OPException {
		return retrieveObjs(hql, paraNames, paraValues, NO_TYPES);
	}

	/**
	 * 根据HQL找回对象集合
	 * 
	 * @param hql
	 * @param params
	 *            参数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> retrieveObjsLP(String hql, Object... params) throws OPException {
		List<?> result = null;
		logger.debug("======##==hql: " + hql);
		try {
			Query query = getSession().createQuery(hql);
			int i = 0;
			if (params != null) {
				for (Object object : params) {
					query.setParameter(i++, object);
				}
			}
			result = query.list();
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		}
		if (result.iterator().hasNext()) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 根据HQL找回对象
	 * 
	 * @param hql
	 * @return Object
	 * @throws OPException
	 */
	public Object retrieveObj(String hql) throws OPException {
		return retrieveObj(hql, NO_PARANAMES, NO_PARAVALUES, NO_TYPES);
	}

	/**
	 * 根据HQL找回对象
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param paraTypes
	 *            参数类型
	 * @return Object
	 * @throws OPException
	 */
	public Object retrieveObj(String hql, String[] paraNames, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		Object result = null;
		logger.debug("======##==hql: " + hql);
		try {
			Query query = getSession().createQuery(hql);

			if (paraNames != null) {
				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
					if (paraValues[i] != null) {
						if (paraTypes == null || paraTypes.length == 0) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i], paraTypes[i]);
						}
					}
				}
			}

			List<?> list = query.setMaxResults(1).list();
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
			return result;
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		}
	}

	/**
	 * 根据HQL找回对象
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @return Object
	 * @throws OPException
	 */
	public Object retrieveObj(String hql, String[] paraNames, Object[] paraValues) throws OPException {
		return retrieveObj(hql, paraNames, paraValues, NO_TYPES);
	}

	/**
	 * 根据HQL找回对象
	 * 
	 * @param hql
	 * @param params
	 * @return Object
	 * @throws OPException
	 */
	public Object retrieveObjLP(String hql, Object... params) throws OPException {
		Object result = null;
		logger.debug("======##==hql: " + hql);
		try {
			Query query = getSession().createQuery(hql);

			int i = 0;
			if (params != null) {
				for (Object object : params) {
					query.setParameter(i++, object);
				}
			}

			List<?> list = query.setMaxResults(1).list();
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
			return result;
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		}
	}

	/**
	 * 根据pk找回对象,没找到抛出ObjectNotFoundException异常
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @return Object
	 * @throws OPException
	 */
	public Object retrieveObj(Class<?> c, Serializable id) throws OPException, NotFindException {
		Object obj = null;
		try {
			obj = getSession().load(c, id, LockMode.READ);
		} catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				throw new NotFindException(he);
			} else {
				throw OPUtil.handleException(he);
			}
		}
		return obj;
	}

	/**
	 * 根据pk找回对象(带锁模式),没找到抛出ObjectNotFoundException异常
	 * 
	 * @param c
	 *            对象类型
	 * @param id
	 *            主键
	 * @param lockMode
	 *            锁模式
	 * @return Object
	 * @throws OPException
	 * @throws NotFindException
	 */
	public Object retrieveObjForUpdate(Class<?> c, Serializable id, LockMode lockMode)
			throws OPException, NotFindException {
		Object obj;
		obj = null;
		try {
			obj = getSession().load(c, id, lockMode);
		} catch (HibernateException he) {
			if (he instanceof ObjectNotFoundException) {
				throw new NotFindException(he);
			} else {
				throw OPUtil.handleException(he);
			}
		}
		return obj;
	}

	/**
	 * 根据hql找回对象集合(带锁模式)
	 * 
	 * @param hql
	 *            hql
	 * @param alias
	 *            带锁模式的对象
	 * @param lockMode
	 *            锁模式
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> retrieveObjForUpdate(String hql, String alias, LockMode lockMode) throws OPException {
		List<?> list = null;
		try {
			Query query = getSession().createQuery(hql);
			query.setLockMode(alias, lockMode);
			list = query.list();
		} catch (HibernateException ex) {
			throw OPUtil.handleException(ex);
		}
		if (list.iterator().hasNext()) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 根据sql返回集合
	 * 
	 * @param sql
	 * @return List<Map<String, Object>>
	 * @throws OPException
	 */
	public List<Map<String, Object>> retrieveListSQL(String sql) throws OPException {
		logger.debug("======##==sql: " + sql);
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			try {
				c = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();

				ps = c.prepareStatement(sql);
				rs = ps.executeQuery();
				ResultSetMetaData md = rs.getMetaData();
				while (rs.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					for (int i = 1; i <= md.getColumnCount(); i++) {
						String name = md.getColumnName(i);
						map.put(name, rs.getString(name));
					}
					result.add(map);
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (c != null) {
					c.close();
				}
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
		return result;
	}

	/**
	 * 执行sql，返回结果集
	 * 
	 * @param sql
	 * @param params
	 * @return List<Map<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> retrieveListSQLLP(String sql, Object... params) throws OPException {
		// return DbUtility.execQueryList(getConnection(), sql, params);

		ExecSqlQueryListWork work = new ExecSqlQueryListWork(sql, params);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
		return (List<Map<String, Object>>) work.getReturnValue();
	}

	/**
	 * 根据HQL更新对象集合（类似 from Entity as e where e.pk=*）
	 * 
	 * @param hql
	 * @throws OPException
	 */
	public void execHqlUpdate(String hql) throws OPException {
		execHqlUpdate(hql, NO_PARANAMES, NO_PARAVALUES, NO_TYPES);
	}

	/**
	 * 根据HQL更新对象集合（类似 from Entity as e where e.pk=*）
	 * 
	 * @param hql
	 * @throws OPException
	 */
	public void execHqlUpdate(String hql, String[] paraNames, Object[] paraValues) throws OPException {
		execHqlUpdate(hql, paraNames, paraValues, NO_TYPES);
	}

	/**
	 * 根据HQL更新对象集合（类似 from Entity as e where e.pk=*）
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名
	 * @param paraValues
	 *            参数值
	 * @param paraTypes
	 *            参数类型
	 * @throws OPException
	 */
	public void execHqlUpdate(String hql, String[] paraNames, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		logger.debug("======##==hql: " + hql);
		try {
			// ((org.hibernate.classic.Session) getSession()).delete(hql,
			// paraValues, paraTypes);

			Query query = getSession().createQuery(hql);
			if (paraNames != null) {
				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
					if (paraValues[i] != null) {
						if (paraTypes == null || paraTypes.length == 0) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i], paraTypes[i]);
						}
					}
				}

			}
			query.executeUpdate();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 利用hql更新
	 * 
	 * @param hql
	 * @param params
	 *            变量绑定参数
	 * @throws OPException
	 */
	public void execHqlUpdateLP(String hql, Object... params) throws OPException {
		try {
			Query query = getSession().createQuery(hql);
			int i = 0;
			for (Object object : params) {
				query.setParameter(i++, object);
			}
			query.executeUpdate();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 执行sql，不返回结果集
	 * 
	 * @param sqls
	 */
	public void execSqlUpdate(String[] sqls) throws OPException {
		// DbUtility.execUpdate(getConnection(), sqls);

		ExecSqlUpdateWork work = new ExecSqlUpdateWork(sqls);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
	}

	/**
	 * 执行sql，不返回结果集
	 * 
	 * @param sql
	 * @param params
	 */
	public void execSqlUpdateLP(String sql, Object... params) throws OPException {
		// DbUtility.execUpdate(getConnection(), sql, params);

		ExecSqlUpdateParamWork work = new ExecSqlUpdateParamWork(sql, params);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
	}

	/**
	 * 返回记录集
	 * 
	 * @param hql
	 * @param pageNumber
	 *            页数
	 * @param pageSize
	 *            每页记录数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> query(String hql, int pageNumber, int pageSize) throws OPException {
		return query(hql, NO_PARANAMES, NO_PARAVALUES, pageNumber, pageSize);
	}

	/**
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param paraTypes
	 *            参数类型
	 * @param pageNumber
	 *            页数
	 * @param pageSize
	 *            每页行数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> query(String hql, String[] paraNames, Object[] paraValues, Type[] paraTypes, int pageNumber,
			int pageSize) throws OPException {
		logger.debug("======##==hql: " + hql);
		List<?> lt = null;
		try {
			Query query = getSession().createQuery(hql);
			if (paraNames != null) {
				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
					if (paraValues[i] != null) {
						if (paraTypes == null || paraTypes.length == 0) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i], paraTypes[i]);
						}
					}
				}
			}

			if (-1 != pageNumber) {
				query.setFirstResult(pageSize * pageNumber);
				query.setMaxResults(pageSize);
			}
			lt = query.list();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}

		if (lt.iterator().hasNext()) {
			return lt;
		} else {
			return null;
		}
	}

	/**
	 * 返回集合（每页数量由系统常量支持）
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param pageNumber
	 *            页数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> query(String hql, String[] paraNames, Object[] paraValues, int pageNumber) throws OPException {
		return query(hql, paraNames, paraValues, null, pageNumber, ApplicationGlobalNames.PAGE_SIZE);
	}

	/**
	 * 返回记录集
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param pageNumber
	 *            页数
	 * @param pageSize
	 *            每页记录数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> query(String hql, String[] paraNames, Object[] paraValues, int pageNumber, int pageSize)
			throws OPException {
		return query(hql, paraNames, paraValues, null, pageNumber, pageSize);
	}

	/**
	 * 利用预编译HQL查询，如果查询不到，返回新的ArrayList
	 * 
	 * @param hql
	 * @param page
	 *            第一页从0开始 ,小于0不分页
	 * @param pageSize
	 *            每页显示数量
	 * @param objects
	 *            变量绑定参数
	 * @return List<?>
	 */
	public List<?> queryLP(String hql, int page, int pageSize, Object... objects) throws OPException {
		try {
			Query query = getSession().createQuery(hql);
			int i = 0;
			if (objects != null) {
				for (Object object : objects) {
					query.setParameter(i++, object);
				}
			}

			if (-1 != page) {
				query.setFirstResult(pageSize * page);
				query.setMaxResults(pageSize);
			}
			query.setCacheable(true);
			query.setCacheRegion("frontpages");
			List<?> result = query.list();

			return result;
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 通过sql获取list数据
	 * 
	 * @param sql
	 * @param objs
	 * @return List<?>
	 */
	public List<?> querySQL(String sql, Object... objs) throws OPException {
		return querySQL(sql, -1, 0, objs);
	}

	/**
	 * 通过sql获取list数据
	 * 
	 * @param sql
	 * @param page
	 * @param pageSize
	 * @param objects
	 * @return List<?>
	 */
	public List<?> querySQL(String sql, int page, int pageSize, Object... objects) throws OPException {
		SQLQuery query = getSession().createSQLQuery(sql);
		int i = 0;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		if (page >= 0) { // NOPMD
			query.setFirstResult(pageSize * page);
			query.setMaxResults(pageSize);
		}
		List<?> result = query.list();
		if (result == null) {
			return Collections.emptyList();
		}
		return result;
	}

	/**
	 * 返回集合（每页数量由系统常量支持）
	 * 
	 * @param hql
	 * @param start
	 *            开始
	 * @param pageSize
	 *            每页记录数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> query2(String hql, int start, int pageSize) throws OPException {
		return query2(hql, NO_PARANAMES, NO_PARAVALUES, NO_TYPES, start, pageSize);
	}

	/**
	 * 返回集合（每页数量由系统常量支持）
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param paraTypes
	 *            参数类型
	 * @param start
	 *            开始
	 * @param pageSize
	 *            每页记录数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> query2(String hql, String[] paraNames, Object[] paraValues, Type[] paraTypes, int start,
			int pageSize) throws OPException {
		logger.debug("======##==hql: " + hql);
		List<?> lt = null;
		try {
			Query query = getSession().createQuery(hql);
			if (paraNames != null) {
				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
					if (paraValues[i] != null) {
						if (paraTypes == null || paraTypes.length == 0) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i], paraTypes[i]);
						}
					}
				}
			}
			query.setFirstResult(start);
			query.setMaxResults(pageSize);

			lt = query.list();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}

		if (lt.iterator().hasNext()) {
			return lt;
		} else {
			return null;
		}
	}

	/**
	 * 返回集合（每页数量由系统常量支持）
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param start
	 *            开始
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> query2(String hql, String[] paraNames, Object[] paraValues, int start) throws OPException {
		return query2(hql, paraNames, paraValues, NO_TYPES, start, ApplicationGlobalNames.PAGE_SIZE);
	}

	/**
	 * 利用预编译HQL查询，如果查询不到，返回新的ArrayList
	 * 
	 * @param hql
	 * @param start
	 *            开始的记录位置
	 * @param pageSize
	 *            每页显示数量
	 * @param objects
	 *            变量绑定参数
	 * @return List<?>
	 */
	public List<?> queryLP2(String hql, int start, int pageSize, Object... objects) throws OPException {
		try {
			Query query = getSession().createQuery(hql);
			int i = 0;
			if (objects != null) {
				for (Object object : objects) {
					query.setParameter(i++, object);
				}
			}

			query.setFirstResult(start);
			query.setMaxResults(pageSize);

			query.setCacheable(true);
			query.setCacheRegion("frontpages");
			List<?> result = query.list();

			return result;
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 取记录数（hql=select count(*) from Entity）
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @return Long 返回记录条数
	 * @throws OPException
	 */
	public Long getCount(String hql) throws OPException {
		return getCount(hql, NO_PARANAMES, NO_PARAVALUES, NO_TYPES);
	}

	/**
	 * 取记录数（hql=select count(*) from Entity）
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param paraTypes
	 *            参数类型
	 * @return Long 返回记录条数
	 * @throws OPException
	 */
	public Long getCount(String hql, String[] paraNames, Object[] paraValues, Type[] paraTypes) throws OPException {
		List<?> list = retrieveObjs(hql, paraNames, paraValues, paraTypes);
		Object obj = list.iterator().next();

		if (obj instanceof Long) {
			return (Long) obj;
		} else {
			return Long.valueOf(((Integer) obj).toString());
		}
	}

	/**
	 * 取记录数（hql=select count(*) from Entity）
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @return Long 返回记录条数
	 * @throws OPException
	 */
	public Long getCount(String hql, String[] paraNames, Object[] paraValues) throws OPException {
		return getCount(hql, paraNames, paraValues, NO_TYPES);
	}

	/**
	 * 利用预编译HQL查询记录总数
	 * 
	 * @param hql
	 * @param params
	 *            变量绑定参数
	 * @return Long 返回记录条数
	 */
	public Long getCountLP(String hql, Object... params) throws OPException {
		try {
			Query query = getSession().createQuery(hql);
			int i = 0;
			for (Object object : params) {
				query.setParameter(i++, object);
			}
			List<?> result = query.list();
			if (result == null) {
				return 0l;
			} else {
				return (Long) result.iterator().next();
			}
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 返回记录数量
	 * 
	 * @param sql
	 *            更新sql语句
	 * @return Long 返回记录条数
	 */
	public Long getSqlCountLP(String sql, Object... params) throws OPException {
		// return DbUtility.getRowCount(getConnection(), sql, params);

		GetSqlCountWork work = new GetSqlCountWork(sql, params);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
		return (Long) work.getReturnValue();
	}

	/**
	 * 根据pk查找对象
	 * 
	 * @param c
	 * @param id
	 * @return Object
	 * @throws OPException
	 */
	public Object findObj(Class<?> c, Serializable id) throws OPException {
		Object obj = null;
		try {
			obj = getSession().get(c, id);
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
		return obj;
	}

	/**
	 * 根据sql语句得到单一对象
	 * 
	 * @param sql
	 * @return Object
	 */
	public Object getSingleObjectLP(String sql, Object... params) throws OPException {
		try {
			SQLQuery query = getSession().createSQLQuery(sql);
			int i = 0;
			if (params != null) {
				for (Object object : params) {
					query.setParameter(i++, object);
				}
			}

			return query.uniqueResult();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 保存对象集合
	 * 
	 * @param obj
	 * @throws OPException
	 */
	public void persistObj(Object obj) throws OPException {
		persistObjs(new Object[] { obj });
	}

	/**
	 * 保存对象数据
	 * 
	 * @param objs
	 * @throws OPException
	 */
	public void persistObjs(Object... objs) throws OPException {
		if (objs == null) {
			throw new OPException("保存失败，持久化对象为空！");
		}
		try {
			Session session = getSession();
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] == null) {
					throw new OPException("保存失败，持久化对象为空！");
				}
				session.persist(objs[i]);
				// getHibernateTemplate().flush();
			}
			session.flush();
			session.clear();// 清除内部缓存的全部数据，及时释放出占用的内存
		} catch (HibernateException he) {
			throw OPUtil.handleException(he);
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
	}

	/**
	 * 根据HQL返回迭代
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param paraTypes
	 *            参数类型
	 * @return Iterator<?>
	 * @throws OPException
	 */
	public Iterator<?> iterate(String hql, String[] paraNames, Object[] paraValues, Type[] paraTypes)
			throws OPException {
		Iterator<?> it = null;
		try {
			Query query = getSession().createQuery(hql);

			if (paraNames != null) {
				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
					if (paraValues[i] != null) {
						if (paraTypes == null || paraTypes.length == 0) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i], paraTypes[i]);
						}
					}
				}
			}
			it = query.iterate();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
		if (it.hasNext()) {
			return it;
		} else {
			return null;
		}
	}

	/**
	 * 根据HQL返回迭代
	 * 
	 * @param hql
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @return Iterator<?>
	 * @throws OPException
	 */
	public Iterator<?> iterate(String hql, String[] paraNames, Object[] paraValues) throws OPException {
		return iterate(hql, paraNames, paraValues, NO_TYPES);
	}

	/**
	 * 根据HQL返回迭代
	 * 
	 * @param hql
	 * @return Iterator<?>
	 * @throws OPException
	 */
	public Iterator<?> iterate(String hql) throws OPException {
		return iterate(hql, NO_PARANAMES, NO_PARAVALUES, NO_TYPES);
	}

	/**
	 * 根据HQL返回迭代
	 * 
	 * @param hql
	 * @param params
	 *            参数
	 * @return Iterator<?>
	 * @throws OPException
	 */
	public Iterator<?> iterateLP(String hql, Object... params) throws OPException {
		Iterator<?> it = null;
		try {
			Query query = getSession().createQuery(hql);

			int i = 0;
			if (params != null) {
				for (Object object : params) {
					query.setParameter(i++, object);
				}
			}

			it = query.iterate();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}
		if (it.hasNext()) {
			return it;
		} else {
			return null;
		}
	}

	/**
	 * 返回named sql支持的集合
	 * 
	 * @param key
	 *            sql键
	 * @param paraNames
	 *            参数名
	 * @param paraValues
	 *            参数值
	 * @param paraTypes
	 *            参数类型
	 * @param pageNumber
	 *            页数
	 * @param pageSize
	 *            每页记录数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> getQuery(String key, String[] paraNames, Object[] paraValues, Type[] paraTypes, int pageNumber,
			int pageSize) throws OPException {
		List<?> lt = null;
		try {
			Query query = getSession().getNamedQuery(key);
			if (paraNames != null) {
				for (int i = 0; i < paraNames.length; i++) {
					logger.debug("查询参数名: " + paraNames[i]);
					logger.debug("查询参数值: " + paraValues[i]);
					if (paraValues[i] != null) {
						if (paraTypes == null || paraTypes.length == 0) {
							query.setParameter(paraNames[i], paraValues[i]);
						} else {
							query.setParameter(paraNames[i], paraValues[i], paraTypes[i]);
						}
					}
				}

			}
			if (-1 != pageNumber) {
				query.setFirstResult(pageSize * pageNumber);
				query.setMaxResults(pageSize);
			}
			lt = query.list();
		} catch (Exception e) {
			throw OPUtil.handleException(e);
		}

		if (lt.iterator().hasNext()) {
			return lt;
		} else {
			return null;
		}
	}

	/**
	 * 返回named sql支持的集合
	 * 
	 * @param key
	 *            sql键
	 * @param pageNumber
	 *            页数
	 * @param pageSize
	 *            每页记录数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> getQuery(String key, int pageNumber, int pageSize) throws OPException {
		return getQuery(key, NO_PARANAMES, NO_PARAVALUES, null, pageNumber, pageSize);
	}

	/**
	 * 返回named sql支持的集合
	 * 
	 * @param key
	 *            sql键
	 * @param paraNames
	 *            参数名称
	 * @param paraValues
	 *            参数值
	 * @param pageNumber
	 *            页数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> getQuery(String key, String[] paraNames, Object[] paraValues, int pageNumber) throws OPException {
		return getQuery(key, paraNames, paraValues, NO_TYPES, pageNumber, ApplicationGlobalNames.PAGE_SIZE);
	}

	/**
	 * 返回hql的记录集
	 * 
	 * @param key
	 *            hql键
	 * @param pageNumber
	 *            页数
	 * @return List<?>
	 * @throws OPException
	 */
	public List<?> getQuery(String key, int pageNumber) throws OPException {
		return getQuery(key, NO_PARANAMES, NO_PARAVALUES, NO_TYPES, pageNumber, ApplicationGlobalNames.PAGE_SIZE);
	}

	/**
	 * oracle特有，得到sequence的当前值
	 * 
	 * @param sequenceName
	 *            序列名称
	 * @return String sequence值
	 * @throws OPException
	 */
	public String getOracleSequence(String sequenceName) throws OPException {
		String seq;
		if (sequenceName == null) {
			throw new OPException("sequence的名字不能为空！");
		}
		seq = null;
		String sql = "select " + sequenceName + ".nextval from dual";

		try {
			// seq = (String) getSession().createSQLQuery(sql).uniqueResult();
			seq = getSession().createSQLQuery(sql).uniqueResult().toString();
		} catch (HibernateException ex) {
			throw OPUtil.handleException(ex);
		}
		return seq;
	}

	/**
	 * oracle特有，得到sequence的当前值
	 * 
	 * @param sequenceName
	 *            序列名称
	 * @param sequenceLen
	 *            序列长度
	 * @param sequencePre
	 *            序列前缀
	 * @return String sequence值
	 * @throws OPException
	 */
	public String getOracleSequence(String sequenceName, int sequenceLen, String sequencePre) throws OPException {
		String seq = getOracleSequence(sequenceName);
		if (sequenceLen == 0 && "0".equals(sequencePre)) {
			return seq;
		}
		if (sequenceLen == 0 && !"0".equals(sequencePre)) {
			return sequencePre + seq;
		}
		if (sequenceLen != 0 && "0".equals(sequencePre)) {
			return StringUtil.fillZero(seq, sequenceLen);
		}
		if (sequenceLen != 0 && !"0".equals(sequencePre)) {
			seq = StringUtil.fillZero(seq, sequenceLen);
			return sequencePre + seq.substring(sequencePre.length());
		} else {
			return seq;
		}
	}

	/**
	 * 删除集合
	 * 
	 * @param className
	 * @throws OPException
	 */
	public void delObjs(String className) throws OPException {
		delObjs(NO_PARANAMES, className, null);
	}

	/**
	 * 删除集合
	 * 
	 * @param ids
	 *            主键值集合
	 * @param className
	 *            对象类名
	 * @param key
	 *            主键
	 * @throws OPException
	 */
	public void delObjs(String[] ids, String className, String key) throws OPException {
		String id = null;
		try {
			StringBuffer buffer = new StringBuffer();
			String lowerClassName = className.toLowerCase();
			buffer.append("delete from ");
			buffer.append(className);
			if (key != null && key.length() > 0 && ids.length > 0) {
				buffer.append(" as ");
				buffer.append(lowerClassName);
				buffer.append(" where ");
				buffer.append(lowerClassName);
				buffer.append(".");
				buffer.append(key);
				buffer.append(" in (");
				for (int i = 0; i < ids.length; i++) {
					id = ids[i];
					buffer.append("'").append(id).append("',");
					logger.debug(id);
				}
				buffer.deleteCharAt(buffer.length() - 1);
				buffer.append(")");
			}

			logger.debug(buffer.toString());
			execHqlUpdate(buffer.toString());
		} catch (OPException ex) {
			throw OPUtil.handleException(ex);
		}
	}

	/********************************** 以下部分支持jdbc ***********************************/

	/**
	 * 执行存储函数或存储过程，返回输出参数。假设输出参数为第一个参数
	 * 
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call
	 *            <ProcName>(?, <ParamList>)}
	 * @param returnType
	 *            输出参数类型
	 * @return Object 输出参数的值
	 */
	public Object execStoredFunction(String sql, int returnType) throws OPException {
		// return DbUtility.execStoredFunction(getConnection(), sql,
		// returnType);

		ExecStoredFunctionWork work = new ExecStoredFunctionWork(sql, returnType);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
		return work.getReturnValue();
	}

	/**
	 * 执行存储函数或存储过程并返回结果集
	 * 
	 * @param sql
	 *            存储函数的调用语句，格式：{?=call <FuncName>(<ParamList>)} 或 {call
	 *            <ProcName>(?, <ParamList>)}
	 * @return List<HashMap<String, Object>>
	 */
	@SuppressWarnings("unchecked")
	public List<HashMap<String, Object>> execStoredFunctionForList(String sql) throws OPException {
		// return DbUtility.execStoredFunctionForList(getConnection(), sql);

		ExecStoredFunctionForListWork work = new ExecStoredFunctionForListWork(sql);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
		return (List<HashMap<String, Object>>) work.getReturnValue();
	}

	/**
	 * 执行存储过程，不返回结果集
	 * 
	 * @param sql
	 *            存储过程的调用语句，格式：{call <ProcName>(<ParamList>)}
	 */
	public void execStoredProcedure(String sql) throws OPException {
		// DbUtility.execStoredProcedure(getConnection(), sql);

		ExecStoredProcedureWork work = new ExecStoredProcedureWork(sql);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
	}

	/**
	 * 执行sql，返回大对象的字节码
	 * 
	 * @param sql
	 * @return byte[] 大对象的字节码
	 */
	public byte[] getDataStreamLP(String sql, Object... params) throws OPException {
		// return DbUtility.getDataStream(getConnection(), sql, params);

		GetDataStreamWork work = new GetDataStreamWork(sql, params);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
		return (byte[]) work.getReturnValue();
	}

	/**
	 * 执行sql，返回blob的字节
	 * 
	 * @param sql
	 * @return InputStream blob的字段
	 */
	public InputStream getBlobLP(String sql, Object... params) throws OPException {
		// return DbUtility.getBlob(getConnection(), sql, params);

		GetBlobWork work = new GetBlobWork(sql, params);
		getSession().doWork(work);

		if (work.getOpException() != null) {
			throw work.getOpException();
		}
		return (InputStream) work.getReturnValue();
	}

	/**
	 * 通过批处理的方式，执行SQL命令。有任何命令失败，抛出异常。
	 *
	 * @param connection
	 * @param sqls
	 */
	public void execUpdateBatchSql(Connection connection, String[] sqls) {
		if (sqls.length == 0) { // NOPMD
			return;
		}

		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			for (int i = 0; i < sqls.length; i++) {
				stmt.addBatch(sqls[i]);
			}
			stmt.executeBatch();
			if (logger.isDebugEnabled()) {
				logger.debug("execUpdate succeed. sql: " + Arrays.toString(sqls));
			}
		} catch (SQLException ex) {
			logger.error("execUpdate failed. sql: " + Arrays.toString(sqls));
			throw new HibernateException(ex);
		} finally {
			closeStatementQuietly(stmt);
		}
	}

	/**
	 * 通过批处理的方式批量处理SQL命令。执行objList.size()次。 第i此执行的参数列表是objList.get(i)。
	 * 
	 * @param cnt
	 * @param sql
	 * @param objList
	 * @return int
	 */
	public int execUpdateBatchSqlLP(Connection cnt, String sql, List<Object[]> objList) {
		PreparedStatement ps = null;
		boolean batchMode = objList.size() > 1;
		try {
			ps = cnt.prepareStatement(sql);
			for (int i = 0; i < objList.size(); i++) {
				int n = 0;
				for (Object object : objList.get(i)) {
					setObject(ps, ++n, object);
				}

				if (batchMode) {
					ps.addBatch();
				}
			}
			if (batchMode) {
				int[] rets = ps.executeBatch();
				return summary(rets);
			} else {
				return ps.executeUpdate();
			}
		} catch (SQLException ex) {
			logger.error("execStoredProcedure failed. sql: " + sql);
			throw new HibernateException(ex);
		} finally {
			closeStatementQuietly(ps);
		}
	}

	/**
	 * 分页查询，包含记录总数
	 * 
	 * @param dcriteria
	 * @param start
	 * @param limit
	 * @return map{total:总记录数，list:符合条件的记录}
	 */
	public Map<String, Object> findByDetachedCriteria(DetachedCriteria dcriteria, int start, int limit) {
		Map<String, Object> hm = new HashMap<String, Object>();
		// get the original projection
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		CriteriaImpl impl = (CriteriaImpl) executableCriteria;
		// 得到原来的查询结果类型
		Projection projection = impl.getProjection();
		// 设置roucount查询类型 ，结果可以返回记录总数
		long totalCount = ((Long) executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		// 重新设置list查询类型
		executableCriteria.setProjection(projection);
		hm.put(QUERY_TOTAL, totalCount);
		executableCriteria.setFirstResult(start);
		executableCriteria.setMaxResults(limit);
		List<?> list = executableCriteria.list();
		hm.put(QUERY_LIST, list);
		return hm;
	}

	/**
	 * 检索满足标准的数据
	 * 
	 * @param criteria
	 * @return list 符合记录列表
	 */
	@SuppressWarnings("rawtypes")
	public List findByCriteria(DetachedCriteria dcriteria) {
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		return executableCriteria.list();

	}

	/**
	 * 检索满足标准的数据
	 * 
	 * @param criteria
	 * @return Object 符合条件的对象
	 */
	public Object findSingleByCriteria(DetachedCriteria dcriteria) {
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		return executableCriteria.uniqueResult();

	}

	/**
	 * 检索满足标准的数据，返回指定范围的记录
	 * 
	 * @param criteria
	 * @param start
	 * @param limit
	 * @return list
	 */
	@SuppressWarnings("rawtypes")
	public List findByCriteria(DetachedCriteria dcriteria, int start, int limit) {
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		executableCriteria.setFirstResult(start);
		executableCriteria.setMaxResults(limit);
		return executableCriteria.list();
	}

	/**
	 * 使用指定的检索标准获取满足标准的记录总数
	 * 
	 * @param criteria
	 * @return Long
	 */
	public Long getRowCountCriteria(DetachedCriteria dcriteria) {
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		executableCriteria.setProjection(Projections.rowCount());
		return (Long) (executableCriteria).uniqueResult();
	}

	/**
	 * 得到数据库连接(hibernate4需要自己关闭connection)
	 * 
	 * @return Connection 数据库连接
	 * @throws OPException
	 */
	@Deprecated
	public Connection getConnection() throws OPException {
		// hibernate4以前版本
		// SessionFactoryImplementor sessionFactory =
		// (SessionFactoryImplementor)new
		// Configuration().configure().buildSessionFactory();
		try {
			// 连接需要自己释放
			return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();

			// 连接需要自己释放
			// return
			// SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		} catch (SQLException e) {
			throw new OPException(e.getMessage());
		}
	}

	private void setObject(PreparedStatement pstmt, int index, Object object) throws SQLException {
		Object tmp = object;
		if (tmp instanceof java.util.Date) {
			tmp = new Timestamp(((java.util.Date) object).getTime());
		}
		pstmt.setObject(index, tmp);
	}

	private void closeStatementQuietly(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			logger.error("释放对象错误!");
		}
	}

	private int summary(int[] values) {
		int result = 0;
		for (int i = 0; i < values.length; i++) {
			result += values[i];
		}
		return result;
	}

}
