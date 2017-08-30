var dataLoadUrl = interUrl.basic + interUrl.group.list;
var resetDataUrl = interUrl.basic + interUrl.group.init;
var form = "groupForm";

var fnRemoveTable = function(params) {
  if (typeof(params) == "number") {
    removeAjax(params)
  } else {
    if (!fnSelectOne()) {
      return;
    };
    var selections = $("#pw_table").bootstrapTable('getSelections');

    commonAjax(interUrl.basic + interUrl.group.remove,
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

  commonAjax(interUrl.basic + interUrl.group.grouprole,
      "POST",
      {
        "groupId": groupId,
        "idstr": ids.join()
      },
      function(){
        loadTree({
          "groupId": groupId,
        }, interUrl.basic + interUrl.group.role)
      }
  )
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
