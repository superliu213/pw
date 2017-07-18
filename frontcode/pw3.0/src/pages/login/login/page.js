require('css/bootstrap.min.css');
require('css/font-awesome.min.css');
require('css/animate.css');
require('css/style.css');

// require('cp');
require('jquery');

const config = require('configModule');

$(() => {
  /* global IS_PRODUCTION:true */ // 由于ESLint会检测没有定义的变量，因此需要这一个`global`注释声明IS_PRODUCTION是一个全局变量(当然在本例中并不是)来规避warning
  if (!IS_PRODUCTION) {
    console.log('如果你看到这个Log，那么这个版本实际上是开发用的版本');
    console.log(config.API_ROOT);
  }
});


// $("#loginBtn").click(function() {
//   location.href = "main/main/main.html";
//     // $.ajax({
//     //     url: interUrl.basic + interUrl.common.login,
//     //     type: "POST",
//     //     data: $("#loginForm").values(),
//     //     success: function(res) {
//     //         console.log("success: ", res);
//     //         var o;
//     //         if (typeof res === "string") {
//     //             o = JSON.parse(res);
//     //         } else {
//     //             o = res;
//     //         }
//     //         if (o["code"] === 10000) {
//     //             location.href = "index.html";
//     //         } else if (o['code'] === 20000) {
//     //             alert("登录"+o['message']+ ", 请使用【用户名:1,密码:1】登录");
//     //
//     //         }
//     //     },
//     //     error:function(e){
//     //         console.log("ERROR: ", e);
//     //         alert("登录请求error");
//     //     }
//     // });
// });
