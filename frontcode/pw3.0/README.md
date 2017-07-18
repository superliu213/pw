# pw3.0（多页面webpack管理半成品，可以运行但不完善）

## 项目介绍
本项目是一个基于webpack架构的**web app**脚手架，其特点如下：
- 可实现前后端分离。
- 编译后的程序不依赖于外部的资源（包括css、font、图片等资源都做了迁移），可以整体放到CDN上。
- 整合Bootstrap3及主题SB-Admin，但其实换掉也很简单，或者干脆不用CSS框架也行。
- 不含Js框架（jQuery不算框架，谢谢）。
- 本项目基于 ***webpack v2*** 和 ***webpack-dev-server v2***。
- 使用ejs模块
- 使用dll插件

## 使用说明
- 本项目使用包管理工具NPM，因此需要先把本项目所依赖的包下载下来：
```
$ npm install
```

- 启动服务器，推荐直接使用webpack-dev-server
```
$ npm run start
```

- 理论上来说，webpack-dev-server会自动帮你打开浏览器并展示示例页面；如果没有的话，请手动打开浏览器，在地址栏里输入`http://localhost:8080`，Duang！页面就出来了！

## CLI命令(npm scripts)
| 命令                     | 作用&效果                                                                                 |
| ----------------------- | ---------------------------------------------------------------------------------------- |
| npm run build-dll       | 生成Dll文件，每次升级第三方库时都需要重新执行一遍 |
| npm run dll             | 生成Dll文件，每次升级第三方库时都需要重新执行一遍 添加了目录清理 （组合命令）|
| npm run build-webpack   | 根据`webpack.config.js`，build出一份生产环境的代码 |
| npm run build           | 根据`webpack.config.js`，build出一份生产环境的代码 添加了目录清理 （组合命令）|
| npm run dev-webpack     | 根据`webpack.dev.config.js`，build出一份开发环境的代码 |
| npm run dev             | 根据`webpack.dev.config.js`，build出一份开发环境的代码 组合命令 添加了目录清理|
| npm run start           | 开启webpack-dev-server并自动打开浏览器，自动监测源码变动并实现LiveReload，**推荐实际开发时使用此项** |
| npm run clean-dll       | 清理dll目录 |
| npm run clean-build     | 清理build目录|
| npm run clean           | 清理dll和build目录（组合命令）|
| npm run profile         | 显示编译过程中每一项资源的耗时，用来调优的 |

## 目录结构说明
```
├─build # 编译后生成的所有代码、资源（图片、字体等，虽然只是简单的从源目录迁移过来）
├─css 样式
├─fonts 字体
├─images 图片
├─js 非npm管理的第三方js和部分公共js
├─node_modules # 利用npm管理的所有包及其依赖
├─node-scripts node脚本
├─src # 当前项目的源码
│   ├─dll # 打包的第三方css或js，提高编译速度
│   ├─modules # 各个模块
│   ├─build-file.comfig.js build目录控制文件
│   ├─entry.js 入口文件
│   └─index.html 主页
|-vendor 第三方包
├─webpack-config # 存放分拆后的webpack配置文件
│   ├─base # 主要是存放一些变量
│   ├─inherit # 存放生产环境和开发环境相同的部分，以供继承
│   └─vendor # 存放webpack兼容第三方库所需的配置文件
├─manifest.json # dll插件打包记录
├─package.json # 项目包
├─README.md # 文档说明
├─webpack-dll.config.js # dll插件打包配置
├─webpack.config.js # 生产环境的webpack配置文件（无实质内容，仅为组织整理）
└─webpack.dev.config.js # 开发环境的webpack配置文件（无实质内容，仅为组织整理）

```
