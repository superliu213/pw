var dataLoadUrl = interUrl.basic + interUrl.function.list;
var resetDataUrl = interUrl.basic + interUrl.function.init;
var form = "functionForm";

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

  $('#myModal').on('show.bs.modal', function() {
    $("input[name ='flag']")[0].value = "update";
    $("input[name ='id']")[0].value = selections[0].id;
    $("input[name ='functionId']")[0].value = selections[0].functionId;
    $("input[name ='functionName']")[0].value = selections[0].functionName;
    $("input[name ='functionType']")[0].value = selections[0].functionType;
    $("input[name ='functionParentId']")[0].value = selections[0].functionParentId;
    $("input[name ='functionUrl']")[0].value = selections[0].functionUrl;
    $("input[name ='orderNo']")[0].value = selections[0].orderNo;
    $("input[name ='functionLogo']")[0].value = selections[0].functionLogo;
    $("input[name ='buttonPosition']")[0].value = selections[0].buttonPosition;
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
      url: interUrl.basic + interUrl.function.remove,
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

        //console.log("success: ", res);
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

var fnSaveDialog = function() {
  var saveUrlTemp = "function";
  //console.log($("input[name ='flag']")[0].value);
  $("#dialogForm").validate();
  if ($("#dialogForm").valid()) {
    $.ajax({
      url: interUrl.basic + saveUrlTemp + "/" + $("input[name ='flag']")[0].value,
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

$(document).ready(function() {
  $("#add_table").on("click", fnAddTable);
  $("#update_table").on("click", fnUpdateTable);
  $("#remove_table").on("click", fnRemoveTable);
  $("#query_table").on("click", fnQueryTable);
  $("#save_dialog").on("click", fnSaveDialog);
  $("#reset_data").on("click", fnResetData);
  $("#reset_form").on("click", fnResetForm);

  $("#pw_table").bootstrapTable('hideColumn', 'id');
  pageFunction();
});
