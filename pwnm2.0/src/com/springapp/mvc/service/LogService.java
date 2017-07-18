package com.springapp.mvc.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.springapp.common.busilog.entity.SysLog;
import com.springapp.common.busilog.service.SyncLogService;
import com.springapp.common.collection.PageHolder;
import com.springapp.common.constants.DbCollection;
import com.springapp.common.op.BaseHibernateDao;

@Repository("logService")
public class LogService extends BaseHibernateDao implements LogServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	@Override
	public void initData() {
		DbCollection.dbLog = new ArrayList<>();
		Date date = new Timestamp(System.currentTimeMillis());
		SysLog log1 = new SysLog(1L, date, "admin", (short) 1, (short) 1);
		SysLog log2 = new SysLog(2L, date, "2", (short) 2, (short) 2);
		DbCollection.dbLog.add(log1);
		DbCollection.dbLog.add(log2);
	}

	@Override
	public PageHolder<SysLog> getLogs(Integer page, Integer pageSize) {
		// if (dbFunction == null) {
		// initData();
		// }
		//
		// return dbFunction;
		int totalCount = 0;

		List<SysLog> datas = new ArrayList<>();

		int startIndex = (page - 1) * (pageSize) + 1;
		int endIndex = page * pageSize;

		if (DbCollection.dbLog == null) {
			initData();
		}

		for (int i = 0; i < DbCollection.dbLog.size(); i++) {
			if (i >= startIndex-1 && i < endIndex) {
				datas.add(DbCollection.dbLog.get(i));
			}
		}

		return new PageHolder<SysLog>(page, pageSize, totalCount, datas);
	}

	@Override
	public List<SysLog> getLogs() {
		List<SysLog> result = null;

		if (DbCollection.dbLog == null) {
			initData();
		}

		result = DbCollection.dbLog;

		return result;
	}

	public void addLog(SysLog sysLog) {
		if (DbCollection.dbLog == null) {
			initData();
		}

		sysLog.setId((long) (DbCollection.dbLog.size() + 1));
		DbCollection.dbLog.add(sysLog);
	}

}
