var path = require('path');
var dirVars = require('./base/dir-vars.config.js');
module.exports = {
  // 模块别名的配置，为了使用方便，一般来说所有模块都是要配置一下别名的
  alias: {
    /* 各种目录 */
    jquery: 'jquery/dist/jquery.min.js',
    srcDir: dirVars.srcRootDir,

    css: path.resolve(dirVars.staticRootDir, 'css/'),

    js: path.resolve(dirVars.staticRootDir, 'js/'),


  },

  // 当require的模块找不到时，尝试添加这些后缀后进行寻找
  extensions: ['.js', '.css', '.less'],
};
