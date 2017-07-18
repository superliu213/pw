var dataLoadUrl = interUrl.basic + interUrl.group.list;
var resetDataUrl = interUrl.basic + interUrl.group.init;
var form = "groupForm";

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
        $("input[name ='groupId']")[0].value = selections[0].groupId;
        $("input[name ='groupName']")[0].value = selections[0].groupName;
        $("input[name ='groupLever']")[0].value = selections[0].groupLever;
        $("input[name ='groupParentId']")[0].value = selections[0].groupParentId;
        $("input[name ='orderNo']")[0].value = selections[0].orderNo;
        $("input[name ='remark']")[0].value = selections[0].remark;
      });

  $('#myModal').modal('show');

}

var fnRemoveTable = function(params) {
  if (typeof(params) == "number") {
    removeAjax(params)
  } else {
    if (!fnSelectOne()) {
      return;
    };
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

var fnConfigureRole = function(params) {
  if (!fnSelectOne()) {
    return;
  }
  var selections = $("#pw_table").bootstrapTable('getSelections');
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

var fnSaveTree = function() {

  var selections = $("#pw_table").bootstrapTable('getSelections');

  var groupId;

  if (selections.length > 0) {
    groupId = selections[0].groupId;
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

      //console.log("success:", res);

      BootstrapDialog.show({
        title: ' 提示信息',
        message: '保存成功'
      });

      loadTree({
        "groupId": groupId,
      }, interUrl.basic + interUrl.group.role)
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
  $("#configure_role").on("click", fnConfigureRole);
  $("#query_table").on("click", fnQueryTable);
  $("#save_dialog").on("click", fnSaveDialog);
  $("#save_tree").on("click", fnSaveTree);
  $("#reset_data").on("click", fnResetData);
  $("#reset_form").on("click", fnResetForm);

  $("#pw_table").bootstrapTable('hideColumn', 'id');
  pageFunction();
});
