package com.springapp.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.springapp.common.collection.PageHolder;
import com.springapp.common.constants.DbCollection;
import com.springapp.common.op.BaseHibernateDao;
import com.springapp.mvc.entiy.SysFunction;

@Repository("functionService")
public class FunctionService extends BaseHibernateDao implements FunctionServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	@Override
	public PageHolder<SysFunction> getFunctions(Integer page, Integer pageSize, String functionId, String functionName,
			String functionType, String functionParentId) {
		int totalCount = 0;

		List<SysFunction> datas = new ArrayList<>();

		int startIndex = (page - 1) * (pageSize) + 1;
		int endIndex = page * pageSize;

		if (DbCollection.dbFunction == null) {
			initData();
		}

		for (int i = 0; i < DbCollection.dbFunction.size(); i++) {
			if (i >= startIndex-1 && i < endIndex) {
				datas.add(DbCollection.dbFunction.get(i));
			}
		}

		totalCount = DbCollection.dbFunction.size();

		return new PageHolder<SysFunction>(page, pageSize, totalCount, datas);
	}

	@Override
	public List<SysFunction> getAllFunctions() {
		List<SysFunction> result = null;

		if (DbCollection.dbFunction == null) {
			initData();
		}
		result = DbCollection.dbFunction;

		return result;
	}

	@Override
	public void removeFunctionByKey(Long id) {
		for (SysFunction temp : DbCollection.dbFunction) {
			if (temp.getId().equals(id)) {
				DbCollection.dbFunction.remove(temp);
				break;
			}
		}
	}

	@Override
	public void addFunction(SysFunction function) {
		if (DbCollection.dbFunction == null) {
			initData();
		}

		function.setId((long) (DbCollection.dbFunction.size() + 1));
		DbCollection.dbFunction.add(function);
	}

	@Override
	public void updateFunction(SysFunction function) {
		int index = 0;
		for (SysFunction temp : DbCollection.dbFunction) {
			if (temp.getId().equals(function.getId())) {
				DbCollection.dbFunction.remove(temp);
				break;
			}
			index++;
		}
		DbCollection.dbFunction.add(index, function);
	}

	@Override
	public void initData() {
		DbCollection.dbFunction = new ArrayList<>();

		SysFunction function1 = new SysFunction(1L, "1", "权限管理", (short) 1, "-1", "#", 1, "fa-diamond", null, "1");
		SysFunction function2 = new SysFunction(2L, "2", "报表", (short) 1, "-1", "#", 2, "fa-bar-chart", null, "1");
		SysFunction function6 = new SysFunction(6L, "6", "日志管理", (short) 1, "-1", "#", 6, "fa-map-o", null, "1");

		SysFunction function7 = new SysFunction(7L, "7", "用户管理", (short) 2, "1", "./modules/authority/user/user.html",
				1, "", null, "1");
		SysFunction function8 = new SysFunction(8L, "8", "角色管理", (short) 2, "1", "./modules/authority/role/role.html",
				2, "", null, "1");
		SysFunction function9 = new SysFunction(9L, "9", "机构管理", (short) 2, "1", "./modules/authority/group/group.html",
				3, "", null, "1");
		SysFunction function10 = new SysFunction(10L, "10", "权限管理", (short) 2, "1",
				"./modules/authority/function/function.html", 4, "", null, "1");
		SysFunction function11 = new SysFunction(11L, "11", "demo", (short) 2, "2", "./modules/report/demo/report.html",
				1, "", null, "1");
		SysFunction function15 = new SysFunction(15L, "15", "日志查询", (short) 2, "6", "./modules/log/log/log.html", 1, "",
				null, "1");

		SysFunction function16 = new SysFunction(16L, "16", "添加", (short) 3, "7", "", 1, "", "userForm.add_table", "1");
		SysFunction function17 = new SysFunction(17L, "17", "编辑", (short) 3, "7", "", 2, "", "userForm.update_table",
				"1");
		SysFunction function18 = new SysFunction(18L, "18", "删除", (short) 3, "7", "", 3, "", "userForm.remove_table",
				"1");
		SysFunction function19 = new SysFunction(19L, "19", "密码重置", (short) 3, "7", "", 4, "",
				"userForm.password_reset", "1");
		SysFunction function20 = new SysFunction(20L, "20", "导入", (short) 3, "7", "", 5, "", "userForm.import_table",
				"1");
		SysFunction function21 = new SysFunction(21L, "21", "导出", (short) 3, "7", "", 6, "", "userForm.export_table",
				"1");
		SysFunction function22 = new SysFunction(22L, "22", "配置机构", (short) 3, "7", "", 7, "",
				"userForm.configure_group", "1");
		SysFunction function23 = new SysFunction(23L, "23", "配置角色", (short) 3, "7", "", 8, "",
				"userForm.configure_role", "1");
		SysFunction function24 = new SysFunction(24L, "24", "查询", (short) 3, "7", "", 9, "", "userForm.query_table",
				"1");
		SysFunction function25 = new SysFunction(25L, "25", "重置", (short) 3, "7", "", 10, "", "userForm.reset_form",
				"1");
		SysFunction function26 = new SysFunction(26L, "26", "数据初始化", (short) 3, "7", "", 11, "", "userForm.reset_data",
				"1");

		SysFunction function27 = new SysFunction(27L, "27", "添加", (short) 3, "8", "", 1, "", "roleForm.add_table", "1");
		SysFunction function28 = new SysFunction(28L, "28", "编辑", (short) 3, "8", "", 2, "", "roleForm.update_table",
				"1");
		SysFunction function29 = new SysFunction(29L, "29", "删除", (short) 3, "8", "", 3, "", "roleForm.remove_table",
				"1");
		SysFunction function30 = new SysFunction(30L, "30", "导入", (short) 3, "8", "", 4, "", "roleForm.import_table",
				"1");
		SysFunction function31 = new SysFunction(31L, "31", "导出", (short) 3, "8", "", 5, "", "roleForm.export_table",
				"1");
		SysFunction function32 = new SysFunction(32L, "32", "配置权限", (short) 3, "8", "", 6, "",
				"roleForm.configure_function", "1");
		SysFunction function33 = new SysFunction(33L, "33", "查询", (short) 3, "8", "", 7, "", "roleForm.query_table",
				"1");
		SysFunction function34 = new SysFunction(34L, "34", "重置", (short) 3, "8", "", 8, "", "roleForm.reset_form",
				"1");
		SysFunction function35 = new SysFunction(35L, "36", "数据初始化", (short) 3, "8", "", 9, "", "roleForm.reset_data",
				"1");

		SysFunction function36 = new SysFunction(36L, "36", "添加", (short) 3, "9", "", 1, "", "groupForm.add_table",
				"1");
		SysFunction function37 = new SysFunction(37L, "37", "编辑", (short) 3, "9", "", 2, "", "groupForm.update_table",
				"1");
		SysFunction function38 = new SysFunction(38L, "38", "删除", (short) 3, "9", "", 3, "", "groupForm.remove_table",
				"1");
		SysFunction function39 = new SysFunction(39L, "39", "配置角色", (short) 3, "9", "", 4, "",
				"groupForm.configure_function", "1");
		SysFunction function40 = new SysFunction(40L, "40", "查询", (short) 3, "9", "", 5, "", "groupForm.query_table",
				"1");
		SysFunction function41 = new SysFunction(41L, "41", "重置", (short) 3, "9", "", 6, "", "groupForm.reset_form",
				"1");
		SysFunction function42 = new SysFunction(42L, "42", "数据初始化", (short) 3, "9", "", 7, "", "groupForm.reset_data",
				"1");

		SysFunction function43 = new SysFunction(43L, "43", "添加", (short) 3, "10", "", 1, "", "functionForm.add_table",
				"1");
		SysFunction function44 = new SysFunction(44L, "44", "编辑", (short) 3, "10", "", 2, "",
				"functionForm.update_table", "1");
		SysFunction function45 = new SysFunction(45L, "45", "删除", (short) 3, "10", "", 3, "",
				"functionForm.remove_table", "1");
		SysFunction function46 = new SysFunction(46L, "46", "查询", (short) 3, "10", "", 5, "",
				"functionForm.query_table", "1");
		SysFunction function47 = new SysFunction(47L, "47", "重置", (short) 3, "10", "", 6, "", "functionForm.reset_form",
				"1");
		SysFunction function48 = new SysFunction(48L, "48", "数据初始化", (short) 3, "10", "", 7, "",
				"functionForm.reset_data", "1");

		SysFunction function49 = new SysFunction(49L, "49", "查询", (short) 3, "11", "", 1, "", "reportForm.query_table",
				"1");
		SysFunction function50 = new SysFunction(50L, "50", "重置", (short) 3, "11", "", 2, "", "reportForm.reset_form",
				"1");
		SysFunction function65 = new SysFunction(65L, "65", "查询", (short) 3, "15", "", 1, "", "logForm.query_table",
				"1");
		SysFunction function66 = new SysFunction(66L, "66", "重置", (short) 3, "15", "", 2, "", "logForm.reset_form",
				"1");
		SysFunction function67 = new SysFunction(67L, "67", "数据初始化", (short) 3, "15", "", 3, "", "logForm.reset_data",
				"1");

		DbCollection.dbFunction.add(function1);
		DbCollection.dbFunction.add(function2);
		DbCollection.dbFunction.add(function6);
		DbCollection.dbFunction.add(function7);
		DbCollection.dbFunction.add(function8);
		DbCollection.dbFunction.add(function9);
		DbCollection.dbFunction.add(function10);
		DbCollection.dbFunction.add(function11);
		DbCollection.dbFunction.add(function15);
		DbCollection.dbFunction.add(function16);
		DbCollection.dbFunction.add(function17);
		DbCollection.dbFunction.add(function18);
		DbCollection.dbFunction.add(function19);
		DbCollection.dbFunction.add(function20);
		DbCollection.dbFunction.add(function21);
		DbCollection.dbFunction.add(function22);
		DbCollection.dbFunction.add(function23);
		DbCollection.dbFunction.add(function24);
		DbCollection.dbFunction.add(function25);
		DbCollection.dbFunction.add(function26);
		DbCollection.dbFunction.add(function27);
		DbCollection.dbFunction.add(function28);
		DbCollection.dbFunction.add(function29);
		DbCollection.dbFunction.add(function30);
		DbCollection.dbFunction.add(function31);
		DbCollection.dbFunction.add(function32);
		DbCollection.dbFunction.add(function33);
		DbCollection.dbFunction.add(function34);
		DbCollection.dbFunction.add(function35);
		DbCollection.dbFunction.add(function36);
		DbCollection.dbFunction.add(function37);
		DbCollection.dbFunction.add(function38);
		DbCollection.dbFunction.add(function39);
		DbCollection.dbFunction.add(function40);
		DbCollection.dbFunction.add(function41);
		DbCollection.dbFunction.add(function42);
		DbCollection.dbFunction.add(function43);
		DbCollection.dbFunction.add(function44);
		DbCollection.dbFunction.add(function45);
		DbCollection.dbFunction.add(function46);
		DbCollection.dbFunction.add(function47);
		DbCollection.dbFunction.add(function48);
		DbCollection.dbFunction.add(function49);
		DbCollection.dbFunction.add(function50);
		DbCollection.dbFunction.add(function65);
		DbCollection.dbFunction.add(function66);
		DbCollection.dbFunction.add(function67);

	}

	// TODO 无法实现控制
	public List<String> getButtonPosition(String formName, String user) {
		List<String> result = null;

		result = new ArrayList<>();

		return result;
	}

	@Override
	public List<SysFunction> getFunctions(String userId) {
		// TODO 无法实现控制
		List<SysFunction> result = new ArrayList<>();

		result = getAllFunctions();

		return result;
	}

}
