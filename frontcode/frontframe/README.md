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
  执行命令后，如果未打开浏览器页面，则手动打开url地址 http://localhost:3000/login.html
  ```
  $ npm run dev
  ```
  - 单独运行前端，业务数据从哪里来?
    mockData.js用来拦截ajax请求，业务数据写在本地json文件中
  - 每一个页面的css和js，如何管理？
    css和js都遵循一个标准，先基础的再第三方的后自定义的
  - 登录页面有什么特殊逻辑处理？
    登录页面使用sessionStorage缓存了用户信息
  - 菜单树如何加载？
    main.js菜单实现，菜单数据只需要是固定格式的json对象即可
  - 每个页面都引入mock进行拦截，如果跟后台进行结合时，如何避免混淆？
    在URL.js中定义了一个全局变量mockFlag，如果mockFLag为false，则前台mock不会拦截使用后台springmvc拦截
  - 前后台分离如何实现的？
    在URL.js中有唯一的api接口，前后台都遵照交互的接口，将前后台完全解耦，实现分离
  - form表单常用控件有哪些？
    codedemo/form/form.html 有常用控件demo
  - table数据页面如何实现的？
    主要使用bootstrap table，在codedemo/datatables中有另一种dataTables表单实现，支持动态列
  - 前台树结构如何展示？
    使用jsTree控件
  - 前台的日期如何处理？
    使用laydate控件
  - form表单校验如何处理？
    使用jquery.validate控件
  - 弹出框有没有统一控件？
    使用bootstrap-dialog
  - 如何把一个表单数据从前台传给后台？
    通过ajax请求，其中jquery.values.js是用来从一个form表单中获取json，用于传输的
  - 如何解决前端的代码冗余？
    前端的公共代码位于common.js中，通过提取公共代码减少冗余
  - datagrid显示列如何隐藏？
    ```js
    $("#pw_table").bootstrapTable('hideColumn', 'id');
    ```
  - datagrid单元格如何格式化？
   ```html
    <th data-field="ifValid" data-formatter="ifValidFormatter">是否有效</th>
   ```
   >ifValidFormatter方法位于common.js中
  - 如果嵌入一个页面如何处理？
  通过iframe实现，report/demo/report.html中实现了嵌入一个报表页面
  - 报表工具smartbi在前端如何进行传参？
  ```js
  var params = [];
  var param = new Object();
  param.name = 'station_id';
  param.value = temp.station_id;
  param.displayValue = temp.station_id;
  params.push(param);
  var paramsInfo = toJSONString(params);
  ```
  >name用来绑定报表组件id，value是真实值，displayValue用来页面中获取显示值
