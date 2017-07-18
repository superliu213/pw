var dataLoadUrl = interUrl.basic + interUrl.log.list;
var resetDataUrl = interUrl.basic + interUrl.log.init;
var form = "logForm";

$(document).ready(function() {
  $("#query_table").on("click", fnQueryTable);
  $("#reset_form").on("click", fnResetForm);
  $("#reset_data").on("click", fnResetData);

  $("#pw_table").bootstrapTable('hideColumn', 'id');
  
  pageFunction();
});
