package com.springapp.mvc.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.springapp.common.busilog.entity.SysLog;
import com.springapp.common.collection.PageHolder;
import com.springapp.common.op.BaseHibernateDao;
import com.springapp.common.op.OPException;

@Repository("logService")
public class LogService extends BaseHibernateDao implements LogServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	@Override
	public void initData() {
		Date date = new Timestamp(System.currentTimeMillis());
		SysLog log1 = new SysLog(1L, date, "admin", (short) 1, (short) 1);
		SysLog log2 = new SysLog(2L, date, "2", (short) 2, (short) 2);

		String hql = "delete from SysLog";

		try {
			this.execHqlUpdate(hql);
		} catch (OPException e) {
			logger.error("清理数据失败", e);
		}

		addLog(log1);
		addLog(log2);
	}

	@Override
	public PageHolder<SysLog> getLogs(Integer page, Integer pageSize) {
		int totalCount = 0;

		List<SysLog> datas = null;

		String hql = "from SysLog t";

		try {
			datas = (List<SysLog>) this.query(hql, page - 1, pageSize);

			List<?> retrieveObjs = this.retrieveObjs(hql);
			if (retrieveObjs != null) {
				totalCount = retrieveObjs.size();
			}

		} catch (OPException e) {
			logger.error("查询失败", e);
		}

		return new PageHolder<SysLog>(page, pageSize, totalCount, datas);
	}

	@Override
	public List<SysLog> getLogs() {
		String hql = "from SysLog";
		List<SysLog> result = null;
		try {
			result = (List<SysLog>) this.retrieveObj(hql);
		} catch (OPException e) {
			logger.error("查询失败", e);
		}
		return result;
	}

	public void addLog(SysLog sysLog) {
		try {
			this.saveObj(sysLog);
		} catch (OPException e) {
			logger.error("添加失败", e);
		}
	}

}
