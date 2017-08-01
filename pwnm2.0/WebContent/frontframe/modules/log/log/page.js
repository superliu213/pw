var dataLoadUrl = interUrl.basic + interUrl.log.list;
var resetDataUrl = interUrl.basic + interUrl.log.init;
var form = "logForm";

$(document).ready(function() {
  $("#query_table").on("click", fnQueryTable);
  $("#reset_form").on("click", fnResetForm);
  $("#reset_data").on("click", fnResetData);

  $("#pw_table").bootstrapTable('hideColumn', 'id');

  var startTime = {
    elem: '#startTime',
    format: 'YYYY-MM-DD hh:mm:ss',
    min: '1900-01-01 00:00:00', // 设定最小日期为当前日期
    max: '2099-06-16 23:59:59', // 最大日期
    istime: true,
    istoday: false,
    isclear: false,
    choose: function(datas) {
      endTime.min = datas; // 开始日选好后，重置结束日的最小日期
      endTime.start = datas // 将结束日的初始值设定为开始日
    }
  };
  var endTime = {
    elem: '#endTime',
    format: 'YYYY-MM-DD hh:mm:ss',
    min: '1900-01-01 00:00:00',
    max: '2099-06-16 23:59:59',
    istime: true,
    istoday: false,
    isclear: false,
    choose: function(datas) {
      startTime.max = datas; // 结束日选好后，重置开始日的最大日期
    }
  };
  laydate(startTime);
  laydate(endTime);
  $('#startTime').val(laydate.now(0, 'YYYY-MM-DD 00:00:00'));
  $('#endTime').val(laydate.now(0, 'YYYY-MM-DD 23:59:59'));

  pageFunction();
});
