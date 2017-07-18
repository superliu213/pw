var dataLoadUrl = interUrl.basic + interUrl.user.list;
var resetDataUrl = interUrl.basic + interUrl.user.init;
var form = "userForm";
var saveTreeUrl;
var loadTreeUrl;

var fnUpdateTable = function() {
  var selections = $("#pw_table").bootstrapTable('getSelections');
  if (selections.length == 0) {
    BootstrapDialog.show({
      title: '提示信息',
      message: '未选择编辑行'
    });
    return;
  }

  if (selections.length > 1) {
    BootstrapDialog.show({
      title: '提示信息',
      message: '只能编辑一行'
    });
    return;
  }

  $('#myModal')
    .on(
      'show.bs.modal',
      function() {
        $("input[name ='flag']")[0].value = "update";
        $("input[name ='id']")[0].value = selections[0].id;
        $("input[name ='userId']")[0].value = selections[0].userId;
        $("input[name ='userName']")[0].value = selections[0].userName;
        $("input[name ='userTelephone']")[0].value = selections[0].userTelephone;
        $("select[name ='ifValid']")[0].value = selections[0].ifValid;
        $("input[name ='userEmail']")[0].value = selections[0].userEmail;
        $("input[name ='userBirthday']")[0].value = laydate
          .now(selections[0].userBirthday);
        $("input[name ='userIdCard']")[0].value = selections[0].userIdCard;
        $("input[name ='userValidityPeriod']")[0].value = laydate
          .now(selections[0].userValidityPeriod);
        $("input[name ='pwValidityPeriod']")[0].value = laydate
          .now(selections[0].pwValidityPeriod);
        $("input[name ='remark']")[0].value = selections[0].remark;
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
      //console.log("success: ", res);

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
      //console.log("ERROR: ", e);
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
        setTimeout(function() {
          BootstrapDialog.show({
            title: '提示信息',
            message: '密码重置成功!'
          });
        }, 1000);
      }
    }]
  });
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
        //console.log("success: ", res);

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
        //console.log("ERROR: ", e);
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
      //console.log("success:", res);
      BootstrapDialog.show({
        title: '提示信息',
        message: '保存成功'
      });
      loadTree({
        "userId": userId,
      }, loadTreeUrl)
    },
    error: function(e) {
      //console.log("error:", e);
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
  $("#import_table").on("click", fnImportTable);
  $("#export_table").on("click", fnExportTable);
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
