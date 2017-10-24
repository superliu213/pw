var dataLoadUrl = interUrl.basic + interUrl.function.list;
var resetDataUrl = interUrl.basic + interUrl.function.init;
var form = "functionForm";

var fnUpdateTable = function() {
	  if (!fnSelectOne()) {
	    return;
	  }

	  var selections = $("#pw_table").bootstrapTable('getSelections');

	  $('#myModal').on('show.bs.modal', function() {
	    $("#dialogForm")[0].reset();
	    $("#myModal input[name ='flag']")[0].value = "update";
	    $("#myModal input[name ='id']")[0].value = selections[0].id;
	    $("#myModal input[name ='functionId']")[0].value = selections[0].functionId;
	    $("#myModal input[name ='functionName']")[0].value = selections[0].functionName;
	    $("#myModal select[name ='functionType']")[0].value = selections[0].functionType;
	    $("#myModal input[name ='functionParentId']")[0].value = checkNullValue(selections[0].functionParentId);
	    $("#myModal input[name ='functionUrl']")[0].value = checkNullValue(selections[0].functionUrl);
	    $("#myModal input[name ='orderNo']")[0].value = selections[0].orderNo;
	    $("#myModal input[name ='functionLogo']")[0].value = checkNullValue(selections[0].functionLogo);
	    $("#myModal input[name ='buttonPosition']")[0].value = checkNullValue(selections[0].buttonPosition);
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
			}]
		});

	}

var fnSaveDialog = function() {
  var saveUrlTemp = "function";

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
