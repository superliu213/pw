var dataLoadUrl = interUrl.basic + interUrl.group.list;
var resetDataUrl = interUrl.basic + interUrl.group.init;
var form = "groupForm";

var fnUpdateTable = function() {
  if (!fnSelectOne()) {
    return;
  }

  var selections = $("#pw_table").bootstrapTable('getSelections');

  $('#myModal')
    .on(
      'show.bs.modal',
      function() {
        $("#dialogForm")[0].reset();
        $("#myModal input[name ='flag']")[0].value = "update";
        $("#myModal input[name ='id']")[0].value = selections[0].id;
        $("#myModal input[name ='groupId']")[0].value = selections[0].groupId;
        $("#myModal input[name ='groupName']")[0].value = selections[0].groupName;
        $("#myModal input[name ='groupLever']")[0].value = selections[0].groupLever;
        $("#myModal input[name ='groupParentId']")[0].value = checkNullValue(selections[0].groupParentId);
        $("#myModal input[name ='orderNo']")[0].value = selections[0].orderNo;
        $("#myModal input[name ='remark']")[0].value = checkNullValue(selections[0].remark);
      });

  $('#myModal').modal('show');

}

var fnRemoveTable = function(params) {
	if (!fnSelectOne()) {
		return;
	}

	BootstrapDialog.show({
		title: '删除',
		message: '是否删除该选项？',
		buttons: [{
			label: '取消',
			action: function(dialog) {
				dialog.close();
			}
		}, {
			label: '确认',
			action: function(dialog) {
				dialog.close();
    var selections = $("#pw_table").bootstrapTable('getSelections');

    $.ajax({
      url: interUrl.basic + interUrl.group.remove,
      type: "POST",
      data: {
        "id": selections[0].id
      },
      headers: {
        "AUTH_ID": sessionStorage.getItem('authId')
      },
      success: function(res) {
        if (authorityInterceptorJump(res)) {
          return;
        }

        BootstrapDialog.show({
          title: ' 提示信息',
          message: res.message
        });

        $('#myModal').modal('hide')
        $("#pw_table").bootstrapTable("refresh", {
          url: "...",
          query: res
        });
      },
      error: function(e) {
        console.log("ERROR: ", e);
        BootstrapDialog.show({
          title: '错误信息',
          message: 'ajax请求error'
        });
      }
    });
  }
		}]
	});

}

var fnConfigureRole = function(params) {
  if (!fnSelectOne()) {
    return;
  }
  var selections = $("#pw_table").bootstrapTable('getSelections');
  
  $("#save_tree").unbind("click");
  $("#save_tree").on("click", fnSaveTree);
  
  loadTree({
    "groupId": selections[0].groupId
  }, interUrl.basic + interUrl.group.role);
}

var fnSaveDialog = function() {
  var saveUrlTemp = "group";
  $("#dialogForm").validate();
  if ($("#dialogForm").valid()) {
    $.ajax({
      url: interUrl.basic + saveUrlTemp + "/" +
        $("input[name ='flag']")[0].value,
      type: "POST",
      data: $("#dialogForm").values(),
      headers: {
        "AUTH_ID": sessionStorage.getItem('authId')
      },
      success: function(res) {
        if (authorityInterceptorJump(res)) {
          return;
        }

        BootstrapDialog.show({
          title: ' 提示信息',
          message: res.message
        });

        $('#myModal').modal('hide')
        $("#pw_table").bootstrapTable("refresh", {
          url: "...",
          query: res
        });
      },
      error: function(e) {
        BootstrapDialog.show({
          title: '错误信息',
          message: 'ajax请求error'
        });
      }
    });
  }
}

var fnSaveTree = function() {

  var selections = $("#pw_table").bootstrapTable('getSelections');

  var groupId;

  if (selections.length > 0) {
    groupId = selections[0].groupId;
  }else{
	  return;
  }

  var arr = $('#using_json_tree').jstree("get_checked", true);
  var ids = new Array();
  $.each(arr, function() {
    ids.push(this.data);
  });

  $.ajax({
    url: interUrl.basic + interUrl.group.grouprole,
    type: "POST",
    data: {
      "groupId": groupId,
      "idstr": ids.join()
    },
    headers: {
      "AUTH_ID": sessionStorage.getItem('authId')
    },
    success: function(res) {
      if (authorityInterceptorJump(res)) {
        return;
      }


      BootstrapDialog.show({
        title: ' 提示信息',
        message: '保存成功'
      });
      loadTree({
        "groupId": groupId,
      }, interUrl.basic + interUrl.group.role)
    },
    error: function(e) {
      BootstrapDialog.show({
        title: '错误信息',
        message: 'ajax请求error'
      });
    }
  });

}

$(document).ready(function() {
  $("#add_table").on("click", fnAddTable);
  $("#update_table").on("click", fnUpdateTable);
  $("#remove_table").on("click", fnRemoveTable);
  $("#configure_role").on("click", fnConfigureRole);
  $("#query_table").on("click", fnQueryTable);
  $("#save_dialog").on("click", fnSaveDialog);
  $("#reset_data").on("click", fnResetData);
  $("#reset_form").on("click", fnResetForm);

  $("#pw_table").bootstrapTable('hideColumn', 'id');
  pageFunction();
});
