package com.springapp.common.op.work;

import java.sql.Connection;

import com.springapp.common.op.DbUtility;
import com.springapp.common.op.OPException;

public class ExecStoredFunctionWork extends AbstractWork {
	private String sql;
	private int returnType;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public ExecStoredFunctionWork(String sql, int returnType) {
		super();
		
		this.sql = sql;
		this.returnType = returnType;
	}

	public void execute(Connection connection) {
		try {
			returnValue = DbUtility.execStoredFunction(connection, sql, returnType);
		} catch (OPException e) {
			opException = e;
		}
	}
}
