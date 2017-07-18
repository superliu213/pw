var fnFormQuery = function() {
  var temp = $("#reportForm").values();
  if (temp.station_id == "") {
    BootstrapDialog.show({
      title: '提示信息',
      message: '请选择车站'
    });
    return;
  }

  var params = [];
  var param = new Object();
  param.name = 'station_id';
  param.value = temp.station_id;
  param.displayValue = temp.station_id;
  params.push(param);
  var paramsInfo = toJSONString(params);
  var urltemp = "";
  var reportId = "I40280e6649ae2101015cc9bc0b770077";
  console.log(paramsInfo);

  $.ajax({
    url: interUrl.basic.replace("/api/", "") + interUrl.common.report,
    success: function(res) {
      console.log("success: ", res);
      urltemp = "/smartbi_proxy/vision/openresource.jsp";
      urltemp += "?paramsInfo=" + paramsInfo;
      urltemp += "&resid=" + reportId;
      urltemp += "&user=user&password=user123456";
      urltemp = encodeURI(urltemp);
      $("#frame").attr("src", urltemp);
    },
    error: function(e) {
      console.log("ERROR: ", e);
      alert("ajax请求error");
    }
  });
}

$(document).ready(function() {
  $("#btn_form_query").on("click", fnFormQuery);

  var config = {
    '.chosen-select': {},
    '.chosen-select-deselect': {
      allow_single_deselect: true
    },
    '.chosen-select-no-single': {
      disable_search_threshold: 10
    },
    '.chosen-select-no-results': {
      no_results_text: 'Oops, nothing found!'
    },
    '.chosen-select-width': {
      width: "95%"
    }
  }
  for (var selector in config) {
    $(selector).chosen(config[selector]);
  }
});
