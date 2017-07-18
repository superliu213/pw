//拦截ajax 模拟数据
$(function() {
  if (mockFlag == true) {

    $.mockjax({
      url: interUrl.basic + interUrl.group.list,
      response: function(res, done) {
        //console.log(res);
        var self = this;

        if (typeof(res.data.data) != "undefined") {
          res.data.totalItem = res.data.data.length;
          self.responseText = res.data;
          done();
        } else {
          var tempJson = "mock/group.json";
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
      url: interUrl.basic + interUrl.group.add,
      response: function(res, done) {
        //console.log(res);

        var self = this;

        var tempJson = "mock/group.json";
        $.getJSON(tempJson,
          function(data) {
            data.data.push(res.data);
            data.message = '添加成功';
            self.responseText = data;
            //console.log(data);
            done();
          });
      }
    });

    $.mockjax({
      url: interUrl.basic + interUrl.group.update,
      response: function(res, done) {

        var self = this;

        var tempJson = "mock/group.json";
        $.getJSON(tempJson,
          function(data) {
            data.data.splice((res.data.id - 1), 1, res.data);
            data.message = '更新成功';
            self.responseText = data;
            //console.log(data);
            done();
          });
      }
    });

    $.mockjax({
      url: interUrl.basic + interUrl.group.remove,
      response: function(res, done) {

        var self = this;

        var tempJson = "mock/group.json";
        $.getJSON(tempJson,
          function(data) {
            $.each(data.data, function(n, value) {
              if (value.id == res.data.id) {
                data.data.splice(n, 1);
                return false;
              }
            });
            data.message = '删除成功';
            self.responseText = data;
            //console.log(data);
            done();
          });
      }
    });

    $.mockjax({
      url: interUrl.basic + interUrl.group.init,
      response: function(res) {
        //console.log(res);
        this.responseText = {
          code: 10000,
          data: [],
          message: "重置成功"
        };
      }
    });

    $.mockjax({
      url: interUrl.basic + interUrl.group.role,
      response: function(res, done) {
        //console.log(res);
        var self = this;

        var tempJson = "mock/group_role.json";
        $.getJSON(tempJson,
          function(data) {
            if (res.data.group_id !== 1) {
              data.core.data.splice(2, 1);
            }

            self.responseText = data;
            //console.log(data);
            done();
          });
      }
    });

    $.mockjax({
      url: interUrl.basic + interUrl.group.grouprole,
      response: function(res) {
        //console.log(res);
      }
    });

    $.mockjax({
      url: interUrl.basic + interUrl.function.pagefunction,
      response: function(res) {
        //console.log(res);
        this.responseText = {
          code: 10000,
          data: [],
          message: "成功"
        };
      }
    });
  }
});
