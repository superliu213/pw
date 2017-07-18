package com.springapp.common.op.work;

import java.sql.Connection;

import com.springapp.common.op.DbUtility;
import com.springapp.common.op.OPException;

public class ExecSqlQueryListWork extends AbstractWork {
	private String sql;
	private Object[] params;
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public ExecSqlQueryListWork(String sql, Object[] params) {
		super();
		
		this.sql = sql;
		this.params = params;
	}

	public void execute(Connection connection) {
		try {
			returnValue = DbUtility.execQueryList(connection, sql, params);
		} catch (OPException e) {
			opException = e;
		}
	}
}
