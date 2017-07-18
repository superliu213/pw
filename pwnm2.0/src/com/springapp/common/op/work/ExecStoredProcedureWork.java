package com.springapp.common.op.work;

import java.sql.Connection;

import com.springapp.common.op.DbUtility;
import com.springapp.common.op.OPException;

public class ExecStoredProcedureWork extends AbstractWork {
	private String sql;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public ExecStoredProcedureWork(String sql) {
		super();
		
		this.sql = sql;
	}

	public void execute(Connection connection) {
		try {
			DbUtility.execStoredProcedure(connection, sql);
		} catch (OPException e) {
			opException = e;
		}
	}
}
