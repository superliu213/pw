var dataLoadUrl = interUrl.basic + interUrl.user.list;
var resetDataUrl = interUrl.basic + interUrl.user.init;
var form = "userForm";
var saveTreeUrl;
var loadTreeUrl;

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
        $("#myModal input[name ='userId']")[0].value = selections[0].userId;
        $("#myModal input[name ='userName']")[0].value = selections[0].userName;
        $("#myModal input[name ='userTelephone']")[0].value = checkNullValue(selections[0].userTelephone);
        $("#myModal select[name ='ifValid']")[0].value = selections[0].ifValid;
        $("#myModal input[name ='userEmail']")[0].value = checkNullValue(selections[0].userEmail);
        $("#myModal input[name ='userBirthday']")[0].value = laydate
          .now(selections[0].userBirthday);
        $("#myModal input[name ='userIdCard']")[0].value = checkNullValue(selections[0].userIdCard);
        $("#myModal input[name ='userValidityPeriod']")[0].value = laydate
          .now(selections[0].userValidityPeriod);
        $("#myModal input[name ='pwValidityPeriod']")[0].value = laydate
          .now(selections[0].pwValidityPeriod);
        $("#myModal input[name ='remark']")[0].value = checkNullValue(selections[0].remark);
      });

  $('#myModal').modal('show');
}

var fnRemoveTable = function() {
  if (!fnSelectOne()) {
    return;
  }

  var selections = $("#pw_table").bootstrapTable('getSelections');

  $.ajax({
    url: interUrl.basic + interUrl.user.remove,
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
      BootstrapDialog.show({
        title: '错误信息',
        message: 'ajax请求error'
      });
    }
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
        $.ajax({
          url: interUrl.basic + interUrl.user.passwordreset,
          data: {
            "id": selections[0].id
          },
          headers: {
            "AUTH_ID": sessionStorage.getItem('authId')
          },
          type: "POST",
          success: function(res) {
            if (authorityInterceptorJump(res)) {
              return;
            }
            BootstrapDialog.show({
              title: ' 提示信息',
              message: res.message
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
    $.ajax({
      url: interUrl.basic + interUrl.user.updatepassword,
      type: "POST",
      data: $("#passwordDialogForm").values(),
      headers: {
        "AUTH_ID": sessionStorage.getItem('authId')
      },
      success: function(res) {
        if (authorityInterceptorJump(res)) {
          return;
        }
        $('#passwordModal').modal('hide')
        BootstrapDialog.show({
          title: ' 提示信息',
          message: res.message
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

var fnSaveDialog = function() {
  var saveUrlTemp = "user";
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

  $.ajax({
    url: saveTreeUrl,
    type: "POST",
    data: params,
    headers: {
      "AUTH_ID": sessionStorage.getItem('authId')
    },
    success: function(res) {
      if (authorityInterceptorJump(res)) {
        return;
      }
      BootstrapDialog.show({
        title: '提示信息',
        message: '保存成功'
      });
      loadTree({
        "userId": userId,
      }, loadTreeUrl)
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
