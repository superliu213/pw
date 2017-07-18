# frontframe

## 项目介绍
本项目是一个前端框架项目实现，特点如下：
- 维护了一套数据接口，通过前端通过ajax请求获取数据，通过mock拦截ajax模拟返回
- 使用 nodejs server 辅助开发
- 基于 H+ 后台主题UI框架进行二次开发，并可以自由的扩展
- 菜单支持动态的初始化
- 抽取了权限的业务模型，并将其实现
- 跟一个spring mvc框架的后台系统可以无缝结合

## 使用说明
> 本项目通过nodejs server启动，使用前请安装好nodejs工具：
- 启动项目
```
$ npm run dev
```
> 启动成功会打开http://127.0.0.1:3000/login.html，如果未自动打开请手动打开

## 目录结构说明

```
├─css # 样式
├─fonts # 字体
├─img # 图片
├─js # 非npm管理的第三方js和部分公共js
├─mock # 公共模块mock
├─modules # 业务逻辑代码模块
├─node_modules # 利用npm管理的所有包及其依赖
├─app.js # server入口
├─examples.js
├─favicon.ico
├─index.html # 主页面
├─login.html # 登录页面
├─package.json # 项目包
└─README.md # 文档说明
```
- 其中modules 业务逻辑代码模块中每一个模块由可以分为mock、page.js文件和html展示页面

## 问答
> 前端
  - 前端代码如何单独启动server并展示 ？
  执行命令
  ```
  $ npm run dev
  ```
  打开url地址 http://localhost:3000/login.html
  - 单独运行前端，业务数据从哪里来?
  - 每一个页面的css和js，如何管理？
  - 登录页面有什么特殊逻辑处理？
  - 菜单树如何加载？
  - 每个页面都引入mock进行拦截，如果跟后台进行结合时，如何避免混淆？
  - 前后台分离如何实现的？
  - form表单常用控件有哪些？
  - 表单展示如何实现的？
  - 前台树结构如何展示？
  - 前台的日期如何处理？
  - form表单校验如何处理？
  - 弹出框有没有统一控件？
  - 如何把一个表单数据从前台传给后台？
  - 如何解决前端的代码冗余？
  - datagrid显示列如何隐藏？
  - datagrid单元格如何格式化？
  - 如果嵌入一个页面如何处理？
  - 报表工具smartbi在前端如何进行传参？
