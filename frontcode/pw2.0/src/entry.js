require('./build-file.config.js');

loadHtml("main","./modules/main/main.html");

function loadHtml(functionId, url) {
    var dtd = $.Deferred();
    $.ajax({
        url: url,
        dataType: "text"
    }).done(function (data) {
        $("#" + functionId).html(data);
        dtd.resolve();
        init();
    });
    return dtd;
}

function init(){
  $("#main").css("display","none");
  $("#login").css("display","block");

  $("#loginInBtn").click(function(){
    $("#login").css("display","none");
    $("#main").css("display","block");
  });

  $("#loginOutBtn").click(function(){
    $("#main").css("display","none");
    $("#login").css("display","block");
  });

  $(".meun-item").click(function() {
    $(".meun-item").removeClass("meun-item-active");
    $(this).addClass("meun-item-active");
    // console.log(this);
  });

  $("#topAD").click(function() {
    $("#topA").toggleClass(" glyphicon-triangle-right");
    $("#topA").toggleClass(" glyphicon-triangle-bottom");
  });
  $("#topBD").click(function() {
    $("#topB").toggleClass(" glyphicon-triangle-right");
    $("#topB").toggleClass(" glyphicon-triangle-bottom");
  });
  $("#topCD").click(function() {
    $("#topC").toggleClass(" glyphicon-triangle-right");
    $("#topC").toggleClass(" glyphicon-triangle-bottom");
  });

  $(".toggle-btn").click(function() {
    $("#leftMeun").toggleClass("show");
    $("#rightContent").toggleClass("pd0px");
  })
}
