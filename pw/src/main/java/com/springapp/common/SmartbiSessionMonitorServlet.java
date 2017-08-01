package com.springapp.common;


import com.springapp.json.JSONUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartbi.net.sf.json.JSONArray;
import smartbi.net.sf.json.JSONObject;

import smartbi.sdk.ClientConnector;
import smartbi.sdk.InvokeResult;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmartbiSessionMonitorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(getClass());

	String SmartbiURL = "http://127.0.0.1:8080/smartbi_proxy";
	ClientConnector smartbiConn = new ClientConnector(SmartbiURL);
	Boolean isOpen;

	/**
	 * 构造函数
	 */
	public SmartbiSessionMonitorServlet() {

	}

	/**
	 * 初始化
	 */
	public void init() throws ServletException {
		try{
			isOpen = smartbiConn.open("admin", "manager123456");
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.printf("2");
	}

	/**
	 * 
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (isOpen) {
				String remoteServerName = "CurrentServer";
				InvokeResult result = smartbiConn.remoteInvoke(
						"SessionService", "getAllSessionMsgByURL",
						new String[] { remoteServerName });
				JSONArray resultArray = (JSONArray) result.getResult();
				int sessionNumber = resultArray.length();
				if (sessionNumber > 3) {// 当登录达到3个用户后，杀掉第一个用户(userName=user)
					for (int i = sessionNumber - 1; i >= 0; i--) {
						JSONObject jsonObject = resultArray.getJSONObject(i);

						String userName = jsonObject.getString("userName");
						String sessionId = jsonObject.getString("sessionId");
						if (userName.equals("user")) {
							InvokeResult ret = smartbiConn.remoteInvoke(
									"SessionService", "invalidateSession",
									new String[] { sessionId });
							if (ret.isSucceed()) {
								logger.info("成功杀掉sessionId=" + sessionId);
							} else {
								logger.error("不能杀掉sessionId=" + sessionId);
							}
						}
					}
				}
			} else {
				isOpen = smartbiConn.open("admin", "manager123456");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			isOpen = false;
		}
		
		if (isOpen) {
			String jsonString = JSONUtil.getSuccessJSONString("成功");
			response.getWriter().println(jsonString);;
		} else {
			String jsonString = JSONUtil.getErrorJSONString("失败");
			response.getWriter().println(jsonString);;
		}
	}

	/**
	 * 销毁
	 */
	public void destroy() {
		try {
			if (isOpen) {
				// 关闭应用连接器
				smartbiConn.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
}
