//validator addMethod start
$.validator.addMethod("commonString",function(value,element,params){
 var commonString = /^-?\w{1,20}$/;
 return (commonString.test(value));
 },"请输入数字、英文字母或者下划线")
//validator addMethod end

//handle undefined start
var checkNullValue = function(value){
	if(typeof(value) == "undefined"){
		return "";
	}

	return value;
}
//handle undefined end


//AuthorizationInterceptor start
var authorityInterceptorJump = function(res){
	var o;
	if (typeof res === "string") {
		o = JSON.parse(res);
	} else {
			o = res;
	}
		
	if(o['code'] == 30000){	
		   BootstrapDialog.show({
			   message: o.message,
	           closable: false,
	           onshow: function(dialog) {
	               setTimeout(function(){
				          	location.href = "/view/login.html";
		                  }, 3000);
	           }
	        });
	return true;
	}
	
	return false;
}
// AuthorizationInterceptor end


// pagefunction start
var pageFunction = function(){
	$.ajax({
		url : interUrl.basic + interUrl.function.pagefunction,
		type : "POST",
		data : {"form" : form , "userId" : sessionStorage.getItem("userId")},
		headers:{
            "AUTH_ID": sessionStorage.getItem('authId')
		},
		success : function(res) {
			if(authorityInterceptorJump(res)){
				return;
			}
			
			for(var i = 0; i< res.data.length; i++){
				res.data[i];
				$("#" + res.data[i].replace(form+".","")).hide();
			}
		},
		error : function(e) {
			console.log("error:", e);
			BootstrapDialog.show({
				title : '错误信息',
				message : 'ajax请求error'
			});
		}
	});
}

// pagefunction end

// table query start
var dataLoad = function(params) {
	setTimeout(function() {
		tableData(params, $("#" + form).values(), dataLoadUrl);
	});// 默认delay 0
};

var tableData = function(params, data, url, callback) {
	var p;
	p = params.data;
	if (url) {
		return $.ajax({
			url : url,
			data : $.extend(data, p),
			headers:{
	            "AUTH_ID": sessionStorage.getItem('authId')
			},
			type : "POST",
			success : function(res) {
				
				if(authorityInterceptorJump(res)){
					return;
				}
				
				params.success({
					'total' : res.totalItem,
					'rows' : res.data
				});
				params.complete();
				return typeof callback === "function" ? callback(res) : void 0;
			},
			error : function(e) {
				console.log("ERROR: ", e);
			}
		});
	}
};

var queryParams = function(params) {
	return {
		search : params.search,
		page : (params.limit + params.offset) / params.limit,
		pageSize : params.limit
	};
};
// table query end

// fomrmatter function start
var transObjectFormatter = function(value, row) {
	var result = '支付宝';
	if (value == '2') {
		result = '微信';
	}
	return result;
}

var ifOpenFormatter = function(value, row) {
	var result = '是';
	if (value == 0) {
		result = '否';
	}
	return result;
}

var ifValidFormatter = function(value, row) {
	var result = '是';
	if (value == 0) {
		result = '否';
	}
	return result;
}

var timeFormatter = function(value) {
	return laydate.now(value, "YYYY-MM-DD hh:mm:ss");
}

var orderStatusFormatter = function(value, row) {
	if (value == '0') {
		result = '结果未知';
	} else if (value == '1') {
		result = '创建订单失败';
	} else if (value == '2') {
		result = '等待用户支付中';
	} else if (value == '3') {
		result = '订单取消';
	} else if (value == '4') {
		result = '支付成功';
	} else if (value == '5') {
		result = '支付失败';
	} else if (value == '6') {
		result = '退款';
	} else if (value == '7') {
		result = '退款失败'; 
	} else{
		result = '--';
	}
	return result;
}

var payTypeFormatter = function(value, row) {
	if (value == '1') {
		result = '订单预创建';
	} else if (value == '2') {
		result = '订单支付';
	} else if (value == '3') {
		result = '订单退款';
	} else if (value == '4') {
		result = '订单查询';
	} else if (value == '5') {
		result = '订单退款查询';
	} else{
		result = '--';
	}
	return result;
}
var businessTypeFormatter = function(value, row) {
	if (value == '1') {
		result = '购票';
	} else if (value == '2') {
		result = '核销取票';
	} else if (value == '3') {
		result = '充值';
	} else if (value == '4') {
		result = '更新';
	} else if (value == '5') {
		result = '行政罚款';
	} else{
		result = '--';
	}
	return result;
}

var functionTypeFormatter = function(value, row) {
	if (value == '1') {
		result = '一级菜单';
	} else if (value == '2') {
		result = '二级菜单';
	} else if (value == '3') {
		result = '按钮';
	} else{
		result = '--';
	}
	return result;
}

var textFormatter = function(value, row) {
	if (value == '') {
		result = '--';
	} else{
		result = value;
	}
	return result;
}
// fomrmatter function end

var loadTree = function(params, url) {
	$.ajax({
		url : url,
		type : "POST",
		data : params,
		headers:{
            "AUTH_ID": sessionStorage.getItem('authId')
		},
		success : function(res) {
			console.log("success: ", res);
			$('#using_json_tree').jstree("destroy");
			$('#using_json_tree').jstree(res);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			BootstrapDialog.show({
				title : '错误信息',
				message : 'ajax请求error'
			});
		}
	});
}

// common button function start
var fnSelectOne = function() {
	var selections = $("#pw_table").bootstrapTable('getSelections');
	if (selections.length == 0) {
		BootstrapDialog.show({
			title : '提示信息',
			message : '未选择编辑行'
		});
		return false;
	}

	if (selections.length > 1) {
		BootstrapDialog.show({
			title : '提示信息',
			message : '只能编辑一行'
		});
		return false;
	}

	return true;
}

var fnResetData = function() {
	$.ajax({
		url : resetDataUrl,
		type : "POST",
		headers:{
            "AUTH_ID": sessionStorage.getItem('authId')
		},
		success : function(res) {
			BootstrapDialog.show({
				title : ' 提示信息',
				message : res.message
			});
			
			$("#pw_table").bootstrapTable("refresh", {
				url : "...",
				query : res
			});
		}
	});
}

var fnOpenTree = function(params) {
	$('#using_json_tree').jstree("open_all");
}

var fnCloseTree = function(params) {
	$('#using_json_tree').jstree("close_all");
}

var fnQueryTable = function() {
	$("#pw_table").bootstrapTable("refresh", {
		url : "..."
	});
}

var fnImportTable = function() {
	BootstrapDialog.show({
		title : '提示信息',
		message : '导入成功'
	});
}

var fnExportTable = function() {
	BootstrapDialog.show({
		title : '提示信息',
		message : '导出成功'
	});
}

var fnResetForm = function() {
	$("#" + form)[0].reset();
}

var fnAddTable = function() {
	$('#myModal').on('show.bs.modal', function() {
		$("#dialogForm")[0].reset();
		$("input[name ='flag']")[0].value = "add";
	});
}
// common button function end

// 将javascript的对象序列化成json字符串；
var toJSONString = function(obj) { 
	var m = {	'\b': '\\b',
						'\t': '\\t',
						'\n': '\\n',
						'\f': '\\f',
						'\r': '\\r',
						'"' : '\\"',
						'\\': '\\\\'	};
	var s = {
		array: function (x) {
			var a = ['['], b, f, i, l = x.length, v;
			for (i = 0; i < l; i += 1) {
				v = x[i];
				f = s[typeof v];
				if (f) {
					v = f(v);
					if (typeof v == 'string') {
						if (b) {
							a[a.length] = ',';
						}
						a[a.length] = v;
						b = true;
					}
				}
			}
			a[a.length] = ']';
			return a.join('');
		},
		'boolean': function (x) {
			return String(x);
		},
		'null': function (x) {
			return "null";
		},
		number: function (x) {
			return isFinite(x) ? String(x) : 'null';
		},
		object: function (x) {
			if (x) {
				if (x instanceof Array) {
					return s.array(x);
				}
				var a = ['{'], b, f, i, v;
				for (i in x) {
					v = x[i];
						f = s[typeof v];
						if (f) {
							v = f(v);
							if (typeof v == 'string') {
								if (b) {
									a[a.length] = ',';
								}
								a.push(s.string(i), ':', v);
								b = true;
							}
						}
					}
				a[a.length] = '}';
				return a.join('');
			}
			return 'null';
		},
		string: function (x) {
			if (/["\\\x00-\x1f]/.test(x)) {
				x = x.replace(/([\x00-\x1f\\"])/g, function(a, b) {
						var c = m[b];
						if (c) {
							return c;
						}
						c = b.charCodeAt();
						return '\\u00' + Math.floor(c / 16).toString(16) + (c % 16).toString(16);
				});
			}
    		return '"' + x + '"';
		}
	};
	return s[typeof obj](obj);
}