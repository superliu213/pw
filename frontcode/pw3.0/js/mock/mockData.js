var mockjax = require('jquerymockjax')($, window);

//拦截ajax 模拟数据
$(function(){
  if(mockFlag == true){

    mockjax({
      url: "/api/login",
      response: function(res) {
        var userName,password;
        userName = res.data["userName"];
        password = res.data["password"];
        // console.log("userName="+userName+",password="+password);
        if (userName === "1" && password === "1") {
            this.responseText = {code: 10000, message:"成功"};
        } else {
            this.responseText = {code: 20000, message:"失败"};
        }
      }
    });

    mockjax({
      url: "/api/menu",
      response: function(res) {
        console.log(res);
				this.responseText = menu;
      }
    });

  }
});

var menu = {
	"sysMenuList": [{
		"menuName": "票务Demo",
		"url": "#",
		"logoTag": "fa-diamond",
		"sysMenuList": [{
			"menuName": "表单",
			"url": "../../pwdemo/form/page.html"
		},
		{
			"menuName": "分页",
			"url": "../../pwdemo/paging/page.html"
		},
		{
			"menuName": "CURD",
			"url": "../../pwdemo/curd/page.html"
		},
		{
			"menuName": "权限",
			"url": "#",
			"sysMenuList": [{
				"menuName": "用户管理",
				"url": "../../pwdemo/user/page.html"
			},
			{
				"menuName": "角色管理",
				"url": "../../pwdemo/role/page.html"
			},
			{
				"menuName": "机构管理",
				"url": "../../pwdemo/group/page.html"
			},
			{
				"menuName": "权限管理",
				"url": "../../pwdemo/function/page.html"
			}]
		},
		{
			"menuName": "smartbi",
			"url": "./form_builder.html"
		},
		{
			"menuName": "JavaMelody",
			"url": "./form_builder.html"
		}]
	},
	{
		"menuName": "表格",
		"url": "#",
		"logoTag": "fa-table",
		"sysMenuList": [{
			"menuName": "票务DataGrid",
			"url": "../../datagrid/datatables/page.html"
		}]
	}]
}
