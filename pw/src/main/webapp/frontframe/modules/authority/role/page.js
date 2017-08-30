var dataLoadUrl = interUrl.basic + interUrl.role.list;
var resetDataUrl = interUrl.basic + interUrl.role.init;
var form = "roleForm";

var fnRemoveTable = function(params) {
  if (typeof(params) == "number") {
    removeAjax(params)
  } else {
    if (!fnSelectOne()) {
      return;
    }
    var selections = $("#pw_table").bootstrapTable('getSelections');

    commonAjax(interUrl.basic + interUrl.role.remove,
        "POST",
        {
          "id": selections[0].id
        },
        function(res){
          $('#myModal').modal('hide')
          $("#pw_table").bootstrapTable("refresh", {
            url: "...",
            query: res
          });
        }
    )
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
    commonAjax(interUrl.basic + "role/" + $("input[name ='flag']")[0].value,
        "POST",
        $("#dialogForm").values(),
        function(res){
          $('#myModal').modal('hide')
          $("#pw_table").bootstrapTable("refresh", {
            url: "...",
            query: res
          });
        }
    )
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

  commonAjax(interUrl.basic + interUrl.role.rolefunction,
      "POST",
      {
        "roleId": roleId,
        "idstr": ids.join()
      },
      function(){
        loadTree({
          "roleId": roleId
        }, interUrl.basic + interUrl.role.function)
      },
      false
  );
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
