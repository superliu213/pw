//拦截ajax 模拟数据
$(function(){
    if(mockFlag == true){

        $.mockjax({
          url: "/api/form/query",
          response: function(res) {
            console.log(res);
          }
        });

    }
 });
