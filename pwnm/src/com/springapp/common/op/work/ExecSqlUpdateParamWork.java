package com.springapp.common.op.work;

import java.sql.Connection;

import com.springapp.common.op.DbUtility;
import com.springapp.common.op.OPException;

public class ExecSqlUpdateParamWork extends AbstractWork {
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

	public ExecSqlUpdateParamWork(String sql, Object[] params) {
		super();
		
		this.sql = sql;
		this.params = params;
	}

	public void execute(Connection connection) {
		try {
			DbUtility.execUpdate(connection, sql, params);
		} catch (OPException e) {
			opException = e;
		}
	}
}
