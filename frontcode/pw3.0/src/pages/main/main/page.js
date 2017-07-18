require('css/bootstrap.min.css');
require('css/font-awesome.min.css');
require('css/animate.css');
require('css/style.css');

require('jquery');
require('bootstrap');
require('jquerymockjax');

// require('toastr');


require('js/plugins/metisMenu/jquery.metisMenu.js');
require('js/plugins/slimscroll/jquery.slimscroll.min.js');
require('js/plugins/layer/layer.min.js');
require('js/hplus.js');
require('js/contabs.js');

require('js/tabs.js');
require('js/URL.js');
require('js/mock/mockData.js');
require('js/main.js');












const config = require('configModule');

$(() => {
  /* global IS_PRODUCTION:true */ // 由于ESLint会检测没有定义的变量，因此需要这一个`global`注释声明IS_PRODUCTION是一个全局变量(当然在本例中并不是)来规避warning
  if (!IS_PRODUCTION) {
    console.log('如果你看到这个Log，那么这个版本实际上是开发用的版本');
    console.log(config.API_ROOT);
  }
});
