//拦截ajax 模拟数据
$(function() {
  if (mockFlag == true) {

    $.mockjax({
      url: "/api/login",
      response: function(res) {
        var userId, password;
        userId = res.data["userId"];
        password = res.data["password"];
        // console.log("userName="+userName+",password="+password);
        if (userId === "admin" && password === "admin") {
          this.responseText = {
            code: 10000,
            data: [1],
            message: "成功"
          };
        } else {
          this.responseText = {
            code: 20000,
            data: [1],
            message: "失败"
          };
        }
      }
    });

    $.mockjax({
      url: "/api/menu",
      response: function(res, done) {
        //console.log(res);
        var self = this;

        var tempJson = "mock/menu.json";
        $.getJSON(tempJson,
          function(data) {
            self.responseText = data;
            done();
          });
      }
    });
  }
});
