var path = require('path');
var moduleExports = {};

// 源文件目录
moduleExports.staticRootDir = path.resolve(__dirname, '../../'); // 项目根目录
moduleExports.srcRootDir = path.resolve(moduleExports.staticRootDir, './src'); // 项目业务代码根目录
moduleExports.dllDir = path.resolve(moduleExports.srcRootDir, './dll'); // 存放由各种不常改变的js/css打包而来的dll
moduleExports.modulesDir = path.resolve(moduleExports.srcRootDir, './modules'); // 存放各个页面独有的部分，如入口文件、只有该页面使用到的css、模板文件等
moduleExports.nodeModulesDir = path.resolve(moduleExports.staticRootDir, './node_modules'); // nodeModules目录


// 生成文件目录
moduleExports.buildDir = path.resolve(moduleExports.staticRootDir, './build'); // 存放编译后生成的所有代码、资源（图片、字体等，虽然只是简单的从源目录迁移过来）

module.exports = moduleExports;
