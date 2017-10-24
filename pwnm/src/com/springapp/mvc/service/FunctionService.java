package com.springapp.mvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.springapp.common.op.LikeMatchMode;
import com.springapp.common.op.SqlRestrictions;
import com.springapp.exception.ApplicationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.springapp.common.collection.PageHolder;
import com.springapp.common.op.BaseHibernateDao;
import com.springapp.common.op.OPException;
import com.springapp.mvc.entiy.SysFunction;

@Repository("functionService")
public class FunctionService extends BaseHibernateDao implements FunctionServiceImpl {

	Log logger = LogFactory.getLog(getClass());

	@Override
	public PageHolder<SysFunction> getFunctions(Integer page, Integer pageSize, String functionId, String functionName,
			String functionType, String functionParentId) {
		int totalCount = 0;

		List<SysFunction> datas = null;

		String hql = "from SysFunction t where 1=1 ";
		hql += SqlRestrictions.eq("t.functionId", functionId);
		hql += SqlRestrictions.like("t.functionName", functionName, LikeMatchMode.BOTHADD);
		hql += SqlRestrictions.eq("t.functionType", functionType);
		hql += SqlRestrictions.eq("t.functionParentId", functionParentId);
		hql += " order by t.functionType, t.orderNo";

		try {
			datas = (List<SysFunction>) this.queryLP(hql, page - 1, pageSize);

			List<?> retrieveObjs = retrieveObjs(hql);
			if (retrieveObjs != null) {
				totalCount = retrieveObjs.size();
			}

		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return new PageHolder<SysFunction>(page, pageSize, totalCount, datas);
	}

	@Override
	public List<SysFunction> getAllFunctions() {
		String hql = "from SysFunction t order by t.functionType, t.orderNo";
		List<SysFunction> result = null;
		try {
			result = (List<SysFunction>) this.retrieveObjs(hql);
		} catch (OPException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}
		return result;
	}

	@Override
	public void removeFunctionByKey(Long id) {
		String sqlQuery = "SELECT b.id "
						+ "FROM sys_role_function a,sys_function b "
						+ "WHERE a.function_id = b.function_id "
						+ " AND b.id = ?"
						+ "UNION "
						+ "SELECT c.id "
						+ "FROM sys_function c "
						+ "WHERE c.function_id IN ( "
                        + "       SELECT DISTINCT b.function_parent_id "
                        + "       FROM sys_role_function a,sys_function b "
                        + " 	  WHERE b.function_type = 3 "
                        + "       AND a.function_id = b.function_id ) "
                        + "AND c.id =? "
                        + "UNION "
                        + "SELECT d.id "
                        + "FROM sys_function d "
                        + "WHERE d.FUNCTION_ID IN ( "
                        + "      SELECT DISTINCT c.FUNCTION_PARENT_ID "
                        + "      FROM sys_function c "
                        + "      WHERE c.function_id IN ( "
                        +"             SELECT DISTINCT b.function_parent_id "
                        +"             FROM sys_role_function a, sys_function b "
                        +"             WHERE b.function_type = 3 "
                        +"             AND a.function_id = b.function_id)) "
                        +"AND d.id =? ";
		List entityListHQL = null;
		try {
			entityListHQL = this.querySQL(sqlQuery, id, id, id);
		} catch (Exception e) {
			logger.error("查询失败", e);
			throw new ApplicationException("查询失败", e);
		}
		
		if(entityListHQL.size() > 0){
			throw new ApplicationException("此functionId已被引用，无法删除");
		}else{
			String sqlrf = "delete from SYS_ROLE_FUNCTION t where t.FUNCTION_ID in (select t.FUNCTION_ID from Sys_Function t where t.id = ? )";
			String sql = "delete from Sys_Function t where t.id = ?";

			try {
				this.execSqlUpdateLP(sqlrf, id);
				this.execSqlUpdateLP(sql, id);
			} catch (Exception e) {
				logger.error("删除失败", e);
				throw new ApplicationException("删除失败", e);
			}
		}
	}

	@Override
	public void addFunction(SysFunction function) {
		try {
			this.saveObj(function);
		} catch (OPException e) {
			logger.error("添加失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void updateFunction(SysFunction function) {
		try {
			this.updateObj(function);
		} catch (OPException e) {
			logger.error("更新失败", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public void initData() {
		SysFunction function1 = new SysFunction(1L, "1", "权限管理", (short) 1, "-1", "#", 1, "fa-diamond", null, "1");
		SysFunction function2 = new SysFunction(2L, "2", "报表管理", (short) 1, "-1", "#", 2, "fa-bar-chart", null, "1");
		SysFunction function3 = new SysFunction(3L, "3", "系统管理", (short) 1, "-1", "#", 3, "fa-money", null, "1");
		SysFunction function4 = new SysFunction(4L, "4", "日志管理", (short) 1, "-1", "#", 4, "fa-map-o", null, "1");

		SysFunction function5 = new SysFunction(5L, "5", "用户管理", (short) 2, "1", "./modules/authority/user/user.html",
				101, "", null, "1");
		SysFunction function6 = new SysFunction(6L, "6", "角色管理", (short) 2, "1", "./modules/authority/role/role.html",
				102, "", null, "1");
		SysFunction function7 = new SysFunction(7L, "7", "机构管理", (short) 2, "1", "./modules/authority/group/group.html",
				103, "", null, "1");
		SysFunction function8 = new SysFunction(8L, "8", "权限管理", (short) 2, "1",
				"./modules/authority/function/function.html", 104, "", null, "1");

		SysFunction function9 = new SysFunction(9L, "9", "demo", (short) 2, "2", "./modules/report/demo/report.html", 201,
				"", null, "1");

		SysFunction function10 = new SysFunction(10L, "10", "性能监控", (short) 2, "3", "/monitoring", 301, "", null, "1");
		SysFunction function11 = new SysFunction(11L, "11", "数据库监控", (short) 2, "3", "/proxooladmin", 302, "", null, "1");

		SysFunction function12 = new SysFunction(12L, "12", "日志查询", (short) 2, "4", "./modules/log/log/log.html", 401, "",
				null, "1");

		SysFunction function13 = new SysFunction(13L, "13", "添加", (short) 3, "5", "", 501, "", "userForm.add_table", "1");
		SysFunction function14 = new SysFunction(14L, "14", "编辑", (short) 3, "5", "", 502, "", "userForm.update_table",
				"1");
		SysFunction function15 = new SysFunction(15L, "15", "删除", (short) 3, "5", "", 503, "", "userForm.remove_table",
				"1");
		SysFunction function16 = new SysFunction(16L, "16", "密码重置", (short) 3, "5", "", 504, "",
				"userForm.password_reset", "1");
		SysFunction function17 = new SysFunction(17L, "17", "导入", (short) 3, "5", "", 505, "", "userForm.import_table",
				"1");
		SysFunction function18 = new SysFunction(18L, "18", "导出", (short) 3, "5", "", 506, "", "userForm.export_table",
				"1");
		SysFunction function19 = new SysFunction(19L, "19", "配置机构", (short) 3, "5", "", 507, "",
				"userForm.configure_group", "1");
		SysFunction function20 = new SysFunction(20L, "20", "配置角色", (short) 3, "5", "", 508, "",
				"userForm.configure_role", "1");
		SysFunction function21 = new SysFunction(21L, "21", "查询", (short) 3, "5", "", 509, "", "userForm.query_table",
				"1");
		SysFunction function22 = new SysFunction(22L, "22", "重置", (short) 3, "5", "", 510, "", "userForm.reset_form",
				"1");
		SysFunction function23 = new SysFunction(23L, "23", "数据初始化", (short) 3, "5", "", 511, "", "userForm.reset_data",
				"1");

		SysFunction function24 = new SysFunction(24L, "24", "添加", (short) 3, "6", "", 601, "", "roleForm.add_table", "1");
		SysFunction function25 = new SysFunction(25L, "25", "编辑", (short) 3, "6", "", 602, "", "roleForm.update_table",
				"1");
		SysFunction function26 = new SysFunction(26L, "26", "删除", (short) 3, "6", "", 603, "", "roleForm.remove_table",
				"1");
		SysFunction function27 = new SysFunction(27L, "27", "导入", (short) 3, "6", "", 604, "", "roleForm.import_table",
				"1");
		SysFunction function28 = new SysFunction(28L, "28", "导出", (short) 3, "6", "", 604, "", "roleForm.export_table",
				"1");
		SysFunction function29 = new SysFunction(29L, "29", "配置权限", (short) 3, "6", "", 605, "",
				"roleForm.configure_function", "1");
		SysFunction function30 = new SysFunction(30L, "30", "查询", (short) 3, "6", "", 606, "", "roleForm.query_table",
				"1");
		SysFunction function31 = new SysFunction(31L, "31", "重置", (short) 3, "6", "", 607, "", "roleForm.reset_form",
				"1");
		SysFunction function32 = new SysFunction(32L, "32", "数据初始化", (short) 3, "6", "", 608, "", "roleForm.reset_data",
				"1");

		SysFunction function33 = new SysFunction(33L, "33", "添加", (short) 3, "7", "", 701, "", "groupForm.add_table",
				"1");
		SysFunction function34 = new SysFunction(34L, "34", "编辑", (short) 3, "7", "", 702, "", "groupForm.update_table",
				"1");
		SysFunction function35 = new SysFunction(35L, "35", "删除", (short) 3, "7", "", 703, "", "groupForm.remove_table",
				"1");
		SysFunction function36 = new SysFunction(36L, "36", "配置角色", (short) 3, "7", "", 704, "",
				"groupForm.configure_function", "1");
		SysFunction function37 = new SysFunction(37L, "37", "查询", (short) 3, "7", "", 705, "", "groupForm.query_table",
				"1");
		SysFunction function38 = new SysFunction(38L, "38", "重置", (short) 3, "7", "", 706, "", "groupForm.reset_form",
				"1");
		SysFunction function39 = new SysFunction(39L, "39", "数据初始化", (short) 3, "7", "", 707, "", "groupForm.reset_data",
				"1");

		SysFunction function40 = new SysFunction(40L, "40", "添加", (short) 3, "8", "", 801, "", "functionForm.add_table",
				"1");
		SysFunction function41 = new SysFunction(41L, "41", "编辑", (short) 3, "8", "", 802, "",
				"functionForm.update_table", "1");
		SysFunction function42 = new SysFunction(42L, "42", "删除", (short) 3, "8", "", 803, "",
				"functionForm.remove_table", "1");
		SysFunction function43 = new SysFunction(43L, "43", "查询", (short) 3, "8", "", 804, "", "functionForm.query_table",
				"1");
		SysFunction function44 = new SysFunction(44L, "44", "重置", (short) 3, "8", "", 805, "", "functionForm.reset_form",
				"1");
		SysFunction function45 = new SysFunction(45L, "45", "数据初始化", (short) 3, "8", "", 806, "",
				"functionForm.reset_data", "1");

		SysFunction function46 = new SysFunction(46L, "46", "查询", (short) 3, "9", "", 901, "", "reportForm.query_table",
				"1");
		SysFunction function47 = new SysFunction(47L, "47", "重置", (short) 3, "9", "", 902, "", "reportForm.reset_form",
				"1");

		SysFunction function48 = new SysFunction(48L, "48", "查询", (short) 3, "12", "", 1201, "", "logForm.query_table",
				"1");
		SysFunction function49 = new SysFunction(49L, "49", "重置", (short) 3, "12", "", 1203, "", "logForm.reset_form",
				"1");
		SysFunction function50 = new SysFunction(50L, "50", "数据初始化", (short) 3, "12", "", 1203, "", "logForm.reset_data",
				"1");

		String hql = "delete from SysFunction";

		try {
			this.execHqlUpdate(hql);
		} catch (OPException e) {
			logger.error("清理数据失败", e);
		}

		addFunction(function1);
		addFunction(function2);
		addFunction(function3);
		addFunction(function4);
		addFunction(function5);
		addFunction(function6);
		addFunction(function7);
		addFunction(function8);
		addFunction(function9);
		addFunction(function10);
		addFunction(function11);
		addFunction(function12);
		addFunction(function13);
		addFunction(function14);
		addFunction(function15);
		addFunction(function16);
		addFunction(function17);
		addFunction(function18);
		addFunction(function19);
		addFunction(function20);
		addFunction(function21);
		addFunction(function22);
		addFunction(function23);
		addFunction(function24);
		addFunction(function25);
		addFunction(function26);
		addFunction(function27);
		addFunction(function28);
		addFunction(function29);
		addFunction(function30);
		addFunction(function31);
		addFunction(function32);
		addFunction(function33);
		addFunction(function34);
		addFunction(function35);
		addFunction(function36);
		addFunction(function37);
		addFunction(function38);
		addFunction(function39);
		addFunction(function40);
		addFunction(function41);
		addFunction(function42);
		addFunction(function43);
		addFunction(function44);
		addFunction(function45);
		addFunction(function46);
		addFunction(function47);
		addFunction(function48);
		addFunction(function49);
		addFunction(function50);

	}

	public List<String> getButtonPosition(String formName, String user) {
		List<String> result = null;
		if (!"admin".equals(user)) {
			String sql = "select d.button_position from sys_function d where d.button_position LIKE '" + formName
					+ "%' AND d.button_position NOT IN " + "(select c.button_position "
					+ "from sys_user_role a , sys_role_function b , sys_function c "
					+ "where a.role_id = b.role_id and b.function_id = c.function_id "
					+ "and a.user_id = ? and c.button_position like '" + formName + "%')";

			try {
				result = (List<String>) this.querySQL(sql, user);
			} catch (OPException e) {
				logger.error("查询失败", e);
				throw new ApplicationException(e);
			}
		} else {
			result = new ArrayList<>();
		}

		return result;
	}

	@Override
	public List<SysFunction> getFunctionsNoButton(String userId) {
		List<SysFunction> result = new ArrayList<>();

		try {
			if ("admin".equals(userId)) {
				String hql = "from SysFunction t where t.functionType < 3 order by t.functionType, t.orderNo";
				try {
					result = (List<SysFunction>) this.retrieveObjs(hql);
				} catch (Exception e) {
					logger.error("查询失败", e);
					throw new ApplicationException(e);
				}
			} else {
				String sql = " SELECT * FROM (SELECT c.* "
						+ "FROM  sys_user_role a, sys_role_function b, sys_function c "
						+ "WHERE a.role_id = b.role_id AND b.function_id = c.function_id AND a.user_id = ? "
						+ "UNION "
						+ "SELECT e.* FROM sys_function e "
						+ "WHERE e.function_id IN (SELECT DISTINCT c.function_parent_id "
						+ "                       FROM sys_user_role a, sys_role_function b,sys_function c "
						+ "                       WHERE a.role_id = b.role_id AND c.function_type = 3 AND b.function_id = c.function_id "
						+ "                       AND a.user_id = ?) "
						+ "UNION "
						+ "SELECT f.* FROM sys_function f "
						+ "WHERE f.FUNCTION_ID IN (SELECT DISTINCT e.FUNCTION_PARENT_ID "
						+ "                        FROM sys_function e "
						+ "                        WHERE e.function_id IN (SELECT DISTINCT c.function_parent_id "
						+ "                                               FROM sys_user_role a,sys_role_function b,sys_function c "
						+ "                                               WHERE a.role_id = b.role_id AND c.function_type = 3 "
						+ "                                               AND b.function_id = c.function_id AND a.user_id = ?) "
						+ "                        )) ss where ss.function_type < 3";
				List<Map<String, Object>> tempList = null;
				try {
					tempList = this.retrieveListSQLLP(sql, userId, userId, userId);
				} catch (Exception e) {
					logger.error("查询失败", e);
				}

				for (Map<String, Object> obj : tempList) {
					SysFunction sysFunction = new SysFunction();
					Long id = Long.valueOf(obj.get("ID").toString());
					String functionId = (String) obj.get("FUNCTION_ID");
					String functionName = (String) obj.get("FUNCTION_NAME");
					Short functionType = Short.valueOf(obj.get("FUNCTION_TYPE").toString());
					String functionParentId = obj.get("FUNCTION_PARENT_ID") == null ? null : (String) obj.get("FUNCTION_PARENT_ID");
					String functionUrl = obj.get("FUNCTION_URL") == null ? null : (String) obj.get("FUNCTION_URL");
					Integer orderNo = Integer.valueOf(obj.get("ORDER_NO").toString());
					String functionLogo = obj.get("FUNCTION_LOGO") == null ? null : (String) obj.get("FUNCTION_LOGO");
					String buttonPosition = obj.get("BUTTON_POSITION") == null ? null : (String) obj.get("BUTTON_POSITION");
					String remark =obj.get("REMARK") == null ? null : (String) obj.get("REMARK");
					sysFunction.setButtonPosition(buttonPosition);
					sysFunction.setFunctionId(functionId);
					sysFunction.setFunctionLogo(functionLogo);
					sysFunction.setFunctionName(functionName);
					sysFunction.setFunctionParentId(functionParentId);
					sysFunction.setFunctionType(functionType);
					sysFunction.setFunctionUrl(functionUrl);
					sysFunction.setId(id);
					sysFunction.setOrderNo(orderNo);
					sysFunction.setRemark(remark);
					result.add(sysFunction);
				}
			}
		} catch (NumberFormatException e) {
			logger.error("查询失败", e);
			throw new ApplicationException(e);
		}

		return result;
	}

}
