package com.springapp.common.op.work;

import java.sql.Connection;

import org.hibernate.jdbc.Work;

import com.springapp.common.op.OPException;


public abstract class AbstractWork implements Work{
	protected Object returnValue;
	protected OPException opException;

	public OPException getOpException() {
		return opException;
	}
	
	public Object getReturnValue() {
		return returnValue;
	}

	public AbstractWork(){
		
	}
	
	abstract public void execute(Connection connection);
}
