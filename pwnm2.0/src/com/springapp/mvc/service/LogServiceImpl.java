package com.springapp.mvc.service;

import java.util.List;

import com.springapp.common.busilog.entity.SysLog;
import com.springapp.common.collection.PageHolder;
import com.springapp.common.spring.BusinessService;

public interface LogServiceImpl extends BusinessService {

	PageHolder<SysLog> getLogs(Integer page, Integer pageSize);
	
	List<SysLog> getLogs();

	void initData();

}
