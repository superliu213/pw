var dataLoadUrl = interUrl.basic + interUrl.role.list;
var resetDataUrl = interUrl.basic + interUrl.role.init;
var form = "roleForm";

var fnUpdateTable = function() {
  if (!fnSelectOne()) {
    return;
  }

  var selections = $("#pw_table").bootstrapTable('getSelections');

  $('#myModal').on('show.bs.modal', function() {
    $("#dialogForm")[0].reset();
    $("#myModal input[name ='flag']")[0].value = "update";
    $("#myModal input[name ='id']")[0].value = selections[0].id;
    $("#myModal input[name ='roleId']")[0].value = selections[0].roleId;
    $("#myModal input[name ='roleDesc']")[0].value = selections[0].roleDesc;
    $("#myModal input[name ='remark']")[0].value = checkNullValue(selections[0].remark);
  });

  $('#myModal').modal('show');
}

var fnRemoveTable = function(params) {
  if (typeof(params) == "number") {
    removeAjax(params)
  } else {
    if (!fnSelectOne()) {
      return;
    }
    var selections = $("#pw_table").bootstrapTable('getSelections');

    $.ajax({
      url: interUrl.basic + interUrl.role.remove,
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
}

var fnConfigureFunction = function(params) {
  if (!fnSelectOne()) {
    return;
  }
  var selections = $("#pw_table").bootstrapTable('getSelections');
  loadTree({
    "roleId": selections[0].roleId
  }, interUrl.basic + interUrl.role.function);
}

var fnSaveDialog = function() {
  $("#dialogForm").validate();
  if ($("#dialogForm").valid()) {
    $.ajax({
      url: interUrl.basic + "role/" + $("input[name ='flag']")[0].value,
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

  var roleId;

  if (selections.length > 0) {
    roleId = selections[0].roleId;
  }

  var arr = $('#using_json_tree').jstree("get_checked", true);
  var ids = new Array();
  $.each(arr, function() {
    ids.push(this.data);
  });

  $.ajax({
    url: interUrl.basic + interUrl.role.rolefunction,
    type: "POST",
    async: false,
    data: {
      "roleId": roleId,
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
        "roleId": roleId
      }, interUrl.basic + interUrl.role.function)
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
  $("#configure_function").on("click", fnConfigureFunction);
  $("#query_table").on("click", fnQueryTable);
  $("#save_dialog").on("click", fnSaveDialog);
  $("#save_tree").on("click", fnSaveTree);
  $("#open_tree").on("click", fnOpenTree);
  $("#close_tree").on("click", fnCloseTree);
  $("#reset_data").on("click", fnResetData);
  $("#reset_form").on("click", fnResetForm);

  $("#pw_table").bootstrapTable('hideColumn', 'id');

  pageFunction();
});
