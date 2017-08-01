# pwnm(轻量级非maven版本)

## 项目介绍
本项目是一个基于 spring mvc框架，前后台完全分离的典型实现，特点如下：
- 实现了基本的权限管理、日志管理和smartbi报表结合
- 基于 spring mvc 搭建整个平台，通过json传输前后台数据
- 通过独立的接口设计，实现前后端完全分离
- 通过nodejs，提供前端server，结合mock工具实现ajax拦截和数据模拟，得以实现前端完全脱离后台，实现完整展示功能，以适应快速迭代的敏捷开发和频繁的前端需求变化
- 完备的文档说明和问题帮助说明
- 使用h2嵌入式数据库，可以在不建表的情况下，完成Service逻辑，便于快读迭代开发

## 使用说明
> 如果使用eclipse开发，通过tomcat部署应用后，手动打开url地址 http://localhost:8080/view/login.html
用户名admin 密码admin
这里需要去除server部署的项目名

## 目录结构说明

```
├─doc
│  └─报表服务器部署文档.html
├─resources # 配置文件目录
│      ├─spring
│      │    ├─database.properties # 数据库属性
│      │    ├─proxool.xml # 数据库连接池配置
│      │    ├─spring-apps-service.xml # service配置
│      │    └─spring-datasource.xml # datasource配置
│      ├─log4j.properties # log4j配置
│      ├─spring-config.xml # spring配置入口文件
│      └─springmvc-servlet.xml # spring mvc 配置
├─src # java源码
├─WebContent
│      ├─frontframe
│      │    ├─css # 样式
│      │    ├─fonts # 字体
│      │    ├─img # 图片
│      │    ├─js # 非npm管理的第三方js和部分公共js
│      │    ├─mock # 公共模块mock
│      │    ├─modules # 业务逻辑代码模块
│      │    ├─node_modules # 利用npm管理的所有包及其依赖
│      │    ├─app.js # server入口
│      │    ├─examples.js
│      │    ├─favicon.ico
│      │    ├─index.html # 主页面
│      │    ├─login.html # 登录页面
│      │    ├─package.json # 项目包
│      │    └─README.md # 文档说明
│      └─WEB-INF
│           ├─lib # jar包
│           └─web.xml # web项目入口
└─README.md # 文档说明
```
- 其中modules 业务逻辑代码模块中每一个模块由可以分为mock、page.js文件和html展示页面

## 问答
> 工具
  - 如何去除在eclipse部署项目时，去除部署路径中的项目名
    配置server属性-Modules-Web Module 中的path为/
  - 本地包如何添加？
  以下是一个范例：
  ```xml
  <dependency>
      <groupId>ojdbc</groupId>
      <artifactId>ojdbc</artifactId>
      <version>14</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/ojdbc14.jar</systemPath>
  </dependency>
  ```
  - oracle如何引入
    由于Oracle授权问题，Maven3不提供Oracle JDBC driver，为了在Maven项目中应用Oracle JDBC driver,必须手动添加，可以考虑把oralce驱动包当成本地包引入，也可以考虑手动添加到本地仓库，方法参考 http://www.cnblogs.com/leiOOlei/p/3380568.html
  - 本地包如何正确打入war包
    引入的本地包放在 WEB-INF/lib下
  - nodejs需要安装嘛？
    除了frontcode目录下的纯前端代码，单独运行时需要nodejs以外，其他项目在不单独运行前端代码时是不需要nodejs环境的，不安装nodejs也是可以运行的
  - smartbi报表部署注意事项
    阅读doc目录下的 《报表服务器部署文档.html》
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
> 后台
  - 整体主要框架是什么？
  spring3+springmvc+hibernate4
  - 数据库连接池使用的是哪个？
  proxool
  - web.xml配置哪些内容？
  - spring-config配置哪些内容？
  - OpenSessionInViewFilter是什么？
  OpenSessionInViewFilter的主要功能是用来把一个Hibernate Session和一次完整的请求过程对应的线程相绑定
  - java melody是什么？
  应用程序性能监控
  - smartbi后台如何嵌入？
  主要是通过smartbiSessionMonitorServlet，还需要使用一个代理程序
  - spring mvc 如何实现json数据交互？
  参考springmvc-servet.xml配置
  - 权限如何拦截？
  ```xml
  <mvc:interceptors>
      <mvc:interceptor>
          <mvc:mapping path="/**"/>
          <mvc:exclude-mapping path="/view/**"/>
          <bean class="com.springapp.mvc.AuthorizationInterceptor"/>
      </mvc:interceptor>
  </mvc:interceptors>
  ```
  - sprng mvc resources 如何配置？
  ```xml
  <mvc:resources mapping="/view/**" location="/frontframe/"/>
  ```
  >url以/view开始的话，会读取资源目录frontframe下的代码
  - 拦截器方式配置事务？
  参考spring-datasource.xml配置
  - database property配置
  - DataSource配置
  - sessionfactory配置
  - 日志的配置
  - basehibernateDao配置以及作用
    通过注解绑定到sessionfactory，主要作用是提供dao层接口的实现，在Service中进行调用
  - 常量管理
    ApplicationGlobalNames.java和common/constansts目录
  - 表设计和表关系
    用户，角色，机构，权限(菜单)
  - smartbi linux如何实现部署？
    阅读doc目录下的 《报表服务器部署文档.html》
  - 如何输出日志，如何记录日志到到日志表？
    SyncLogService.java提供统一方法
  - 添加一个菜单或则按钮注意什么？
  首先注意权限之间的上下级关系，其次注意按钮位置会影响按钮显隐控制，按钮位置一般格式为
  ```
  表单formid.按钮id
  ```  
