package com.springapp.common.op.work;

import java.sql.Connection;

import com.springapp.common.op.DbUtility;
import com.springapp.common.op.OPException;

public class ExecSqlUpdateWork extends AbstractWork {
	private String[] sqls;


	public String[] getSqls() {
		return sqls;
	}

	public void setSqls(String[] sqls) {
		this.sqls = sqls;
	}

	public ExecSqlUpdateWork(String[] sqls) {
		super();
		
		this.sqls = sqls;
	}

	public void execute(Connection connection) {
		try {
			DbUtility.execUpdate(connection, sqls);
		} catch (OPException e) {
			opException = e;
		}
	}
}
