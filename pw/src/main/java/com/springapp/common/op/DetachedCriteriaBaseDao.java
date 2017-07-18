package com.springapp.common.op;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;

/**
 * Hibernate标准离线查询方式，无需组装sql语句
 *
 */
public class DetachedCriteriaBaseDao extends BaseHibernateDao {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(DetachedCriteriaBaseDao.class);

	public  static final String QUERY_TOTAL ="total";
	public  static final String QUERY_LIST="list";


	/**
	 * 分页查询，包含记录总数
	 * @param dcriteria
	 * @param start
	 * @param limit
	 * @return map{total:总记录数，list:符合条件的记录}
	 */
	public HashMap findByDetachedCriteria(DetachedCriteria dcriteria, int start, int limit) {
		HashMap hm = new HashMap();
		// get the original projection
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		CriteriaImpl impl = (CriteriaImpl) executableCriteria;
		// 得到原来的查询结果类型
		Projection projection = impl.getProjection();
		// 设置roucount查询类型 ，结果可以返回记录总数
		long totalCount =  ((Long)executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).longValue();
		// 重新设置list查询类型
		executableCriteria.setProjection(projection);
		hm.put(QUERY_TOTAL, totalCount);
		executableCriteria.setFirstResult(start);
		executableCriteria.setMaxResults(limit);
		List list = executableCriteria.list(); ;
		hm.put(QUERY_LIST, list);
		return hm;
	}

	/**
	 * 检索满足标准的数据
	 * @param criteria
	 * @return list 符合记录列表
	 */
	public List findByCriteria(DetachedCriteria dcriteria) {
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		return executableCriteria.list(); 
		
	}

	/**
	 * 检索满足标准的数据，返回指定范围的记录
	 * @param criteria
	 * @param start 
	 * @param limit
	 * @return list
	 */
	public List findByCriteria(DetachedCriteria dcriteria, int start, int limit) {
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		executableCriteria.setFirstResult(start);
		executableCriteria.setMaxResults(limit);
		return executableCriteria.list(); 
	}

	/**
	 * 使用指定的检索标准获取满足标准的记录总数
	 * @param criteria
	 * @return int
	 */
	public Long getRowCount(DetachedCriteria dcriteria) {
		Criteria executableCriteria = dcriteria.getExecutableCriteria(this.getSession());
		executableCriteria.setProjection(Projections.rowCount());
		return (Long) (executableCriteria).uniqueResult();
	}

}
