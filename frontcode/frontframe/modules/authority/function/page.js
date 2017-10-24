var dataLoadUrl = interUrl.basic + interUrl.function.list;
var resetDataUrl = interUrl.basic + interUrl.function.init;
var form = "functionForm";

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

    commonAjax(interUrl.basic + interUrl.function.remove,
               "POST",
              {"id": selections[0].id},
              function(res){
                $('#myModal').modal('hide')
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

var fnSaveDialog = function() {
  var saveUrlTemp = "function";

  $("#dialogForm").validate();
  if ($("#dialogForm").valid()) {
    commonAjax(interUrl.basic + saveUrlTemp + "/" + $("input[name ='flag']")[0].value,
               "POST",
               $("#dialogForm").values(),
              function(res){
                $('#myModal').modal('hide')
                $("#pw_table").bootstrapTable("refresh", {
                  url: "...",
                  query: res
                });
              }
    );
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
