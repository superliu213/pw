require('!!file-loader?name=index.html!./index.html');
require('!!file-loader?name=modules/main/main.html!./modules/main/main.html');

module.exports = {
  js: {
    // html5shiv: require('!!file-loader?name=static/js/[name].[ext]!../../../vendor/ie-fix/html5shiv.min.js'),
    // respond: require('!!file-loader?name=static/js/[name].[ext]!../../../vendor/ie-fix/respond.min.js'),
    // jquery: require('!!file-loader?name=static/js/[name].[ext]!jquery/dist/jquery.min.js'),
    // jqueryvalues: require('!!file-loader?name=static/js/[name].[ext]!../../../vendor/jquery.values.js')
  },
  images: {
    // 'login-bg': require('!!file-loader?name=static/images/[name].[ext]!../imgs/login-bg.jpg'),
    // 'profile_small': require('!!file-loader?name=static/images/[name].[ext]!../imgs/profile_small.jpg'),
    // 'a1': require('!!file-loader?name=static/images/[name].[ext]!../imgs/a1.jpg'),
    // 'a2': require('!!file-loader?name=static/images/[name].[ext]!../imgs/a2.jpg'),
    // 'a3': require('!!file-loader?name=static/images/[name].[ext]!../imgs/a3.jpg'),
    // 'a4': require('!!file-loader?name=static/images/[name].[ext]!../imgs/a4.jpg'),
    // 'a7': require('!!file-loader?name=static/images/[name].[ext]!../imgs/a7.jpg'),
    // 'a8': require('!!file-loader?name=static/images/[name].[ext]!../imgs/a8.jpg'),
    // 'qr_code': require('!!file-loader?name=static/images/[name].[ext]!../imgs/qr_code.png'),
    // 'pay': require('!!file-loader?name=static/images/[name].[ext]!../imgs/pay.png'),
  },
  dll: {
    js: require('!!file-loader?name=dll/dll.js!./dll/dll.js'),
    css: require('!file-loader?name=dll/dll.css!./dll/dll.css'),
  },
};
