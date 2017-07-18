//拦截ajax 模拟数据
$(function(){

    if(mockFlag == true){
        $.mockjax({
          url: "/mock/pw_grid_test.json",
          response: function(res, done) {
            console.log(res);
            var start = res.data["start"];
            var length = res.data["length"];
            var draw = res.data["draw"];
            var tempJson = "";
            if(length === 0){//非server模式
                 tempJson = "mock/pw_grid_test2.json";
            }else{//server模式
              if(length === 10 && start === 0){
                 tempJson = "mock/pw_grid_test_server1.json";
              }else if (length === 10 && start === 10) {
                 tempJson = "mock/pw_grid_test_server2.json"
              }else if ((length === 10 && start === 20)
                      ||(length === 20 && start === 20)
                      ||(length === 20 && start === 1)) {
                 tempJson = "mock/pw_grid_test_server3.json"
              }else if (length === 20 && start === 0) {
                 tempJson = "mock/pw_grid_test_server4.json"
              }else if (length === 50 && start === 0) {
                 tempJson = "mock/pw_grid_test_server5.json"
              }
            }

            var self = this;
            //这里的$是内嵌html
            $.getJSON(tempJson,
              function(data){
                if(typeof(data["draw"]) != "undefined"){
                  data["draw"] = draw;
                }
                self.responseText = data;
                done();
              });
          }
         });
    }

 });
