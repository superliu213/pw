//拦截ajax 模拟数据
$(function(){
    if(mockFlag == true){
        $.mockjax({
            url: interUrl.basic.replace("/api/","") + interUrl.common.report,
            response: function(res) {
                console.log(res);
            }
        });
    }
});
