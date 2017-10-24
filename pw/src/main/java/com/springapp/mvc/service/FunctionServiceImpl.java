package com.springapp.mvc.service;

import java.util.List;

import com.springapp.common.collection.PageHolder;
import com.springapp.common.spring.BusinessService;
import com.springapp.mvc.entiy.SysFunction;

public interface FunctionServiceImpl extends BusinessService {
	
	PageHolder<SysFunction> getFunctions(Integer page, Integer pageSize, String functionId, String functionName,
			String functionType, String functionParentId);

	List<SysFunction> getAllFunctions();

	List<SysFunction> getFunctionsNoButton(String userId);

	void removeFunctionByKey(Long id);

	void addFunction(SysFunction function);

	void updateFunction(SysFunction function);

	void initData();

	List<String> getButtonPosition(String formName, String user);

}
