var dataLoadUrl = interUrl.basic + interUrl.user.list;
var resetDataUrl = interUrl.basic + interUrl.user.init;
var form = "userForm";
var saveTreeUrl;
var loadTreeUrl;

var fnRemoveTable = function() {
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

        commonAjax(interUrl.basic + interUrl.user.remove,
          "POST", {
            "id": selections[0].id
          },
          function(res) {
            $('#myModal').modal('hide');
            $("#pw_table").bootstrapTable("refresh", {
              url: "...",
              query: res
            });
          }
        );
			}
		}]
	});

}

var fnPasswordReset = function() {
  if (!fnSelectOne()) {
    return;
  }

  BootstrapDialog.show({
    title: '密码重置',
    message: '是否重置该用户密码？',
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

        commonAjax(
          interUrl.basic + interUrl.user.passwordreset,
          "POST", {
            "id": selections[0].id
          }
        )
      }
    }]
  });
}


var fnUpdatePassword = function() {
  if (!fnSelectOne()) {
    return;
  }

  var selections = $("#pw_table").bootstrapTable('getSelections');

  $('#passwordModal')
    .on(
      'show.bs.modal',
      function() {
        $('#passwordDialogForm')[0].reset();
        $("#passwordModal input[name ='id']")[0].value = selections[0].id;
        $("#passwordModal input[name ='userId']")[0].value = selections[0].userId;
      });
  $('#passwordModal').modal('show');
}

var fnSavePasswordDialog = function() {
  var saveUrlTemp = "user";
  $("#passwordDialogForm").validate();

  if ($("#passwordDialogForm").values().oldPassword === $("#passwordDialogForm").values().newPassword) {
    BootstrapDialog.show({
      title: ' 提示信息',
      message: '新旧密码一致，不允许修改，请重新输入'
    });
    return
  }

  if ($("#passwordDialogForm").valid()) {
    commonAjax(interUrl.basic + interUrl.user.updatepassword,
      "POST",
      $("#passwordDialogForm").values(),
      function() {
        $('#passwordModal').modal('hide')
      }
    )
  }
}

var fnSaveDialog = function() {
  var saveUrlTemp = "user";
  $("#dialogForm").validate();
  if ($("#dialogForm").valid()) {
    commonAjax(interUrl.basic + saveUrlTemp + "/" + $("input[name ='flag']")[0].value,
      "POST",
      $("#dialogForm").values(),
      function(res) {
        $('#myModal').modal('hide')
        $("#pw_table").bootstrapTable("refresh", {
          url: "...",
          query: res
        });
      }
    )
  }
}

var fnConfigureGroup = function(params) {
  if (!fnSelectOne()) {
    return;
  }

  var selections = $("#pw_table").bootstrapTable('getSelections');

  loadTree({
    "userId": selections[0].userId
  }, interUrl.basic + interUrl.user.group);

  $("#save_tree").unbind("click");
  $("#save_tree").on("click", fnSaveTree);

  saveTreeUrl = interUrl.basic + interUrl.user.usergroup;
  loadTreeUrl = interUrl.basic + interUrl.user.group;
}

var fnConfigureRole = function(params) {
  if (!fnSelectOne()) {
    return;
  }
  var selections = $("#pw_table").bootstrapTable('getSelections');

  loadTree({
    "userId": selections[0].userId
  }, interUrl.basic + interUrl.user.role);

  $("#save_tree").unbind("click");
  $("#save_tree").on("click", fnSaveTree);

  saveTreeUrl = interUrl.basic + interUrl.user.userrole;
  loadTreeUrl = interUrl.basic + interUrl.user.role;
}

var fnSaveTree = function() {
  var selections = $("#pw_table").bootstrapTable('getSelections');

  var userId;

  if (selections.length > 0) {
    userId = selections[0].userId;
  }

  var arr = $('#using_json_tree').jstree("get_checked", true);
  var ids = new Array();
  $.each(arr, function() {
    ids.push(this.data);
  });

  var params = {
    "userId": userId,
    "idstr": ids.join()
  };

  commonAjax(saveTreeUrl, "POST", params, function() {
    loadTree({
      "userId": userId,
    }, loadTreeUrl)
  })
}

$(document).ready(function() {
  $("#add_table").on("click", fnAddTable);
  $("#update_table").on("click", fnUpdateTable);
  $("#remove_table").on("click", fnRemoveTable);
  $("#password_reset").on("click", fnPasswordReset);
  $("#update_password").on("click", fnUpdatePassword);
  $("#save_password_dialog").on("click", fnSavePasswordDialog);
  $("#query_table").on("click", fnQueryTable);
  $("#save_dialog").on("click", fnSaveDialog);
  $("#reset_form").on("click", fnResetForm);
  $("#reset_data").on("click", fnResetData);
  $("#configure_group").on("click", fnConfigureGroup);
  $("#configure_role").on("click", fnConfigureRole);

  $("#pw_table").bootstrapTable('hideColumn', 'id');
  $("#pw_table").bootstrapTable('hideColumn', 'userPassWord');
  $("#pw_table").bootstrapTable('hideColumn', 'userEmail');
  $("#pw_table").bootstrapTable('hideColumn', 'userBirthday');
  $("#pw_table").bootstrapTable('hideColumn', 'userIdCard');
  $("#pw_table").bootstrapTable('hideColumn', 'userValidityPeriod');
  $("#pw_table").bootstrapTable('hideColumn', 'pwValidityPeriod');
  $("#pw_table").bootstrapTable('hideColumn', 'remark');

  pageFunction();

});
