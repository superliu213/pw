//拦截ajax 模拟数据
$(function() {
  if (mockFlag == true) {

    $.mockjax({
      url: interUrl.basic + interUrl.log.list,
      response: function(res, done) {
        console.log(res);
        var self = this;

        if (typeof(res.data.data) != "undefined") {
          res.data.totalItem = res.data.data.length;
          self.responseText = res.data;
          done();
        } else {
          var tempJson = "mock/log.json";
          $.getJSON(tempJson,
            function(data) {
              self.responseText = data;
              //console.log(data);
              done();
            });
        }
      }
    });

    $.mockjax({
      url: interUrl.basic + interUrl.log.init,
      response: function(res) {
        console.log(res);
        this.responseText = {
          code: 10000,
          data: [],
          message: "重置成功"
        };
      }
    });

    $.mockjax({
      url: interUrl.basic + interUrl.function.pagefunction,
      response: function(res) {
        console.log(res);
        this.responseText = {
          code: 10000,
          data: [],
          message: "成功"
        };
      }
    });

  }
});
