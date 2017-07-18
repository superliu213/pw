# pwnm(非maven版本)

## 项目介绍
本项目是一个基于 spring mvc框架，前后台完全分离的典型实现，特点如下：
- 实现了基本的权限管理、日志管理和smartbi报表结合
- 基于 spring mvc 搭建整个平台，通过json传输前后台数据
- 通过独立的接口设计，实现前后端完全分离
- 通过nodejs，提供前端server，结合mock工具实现ajax拦截和数据模拟，得以实现前端完全脱离后台，实现完整展示功能，以适应快速迭代的敏捷开发和频繁的前端需求变化
- 完备的文档说明和问题帮助说明

## 使用说明
> 本项目提供oracle版本数据库脚本，运行项目前请配置数据库

> 如果使用eclipse开发，通过tomcat部署应用后，手动打开url地址 http://localhost:8080/view/login.html
用户名admin 密码admin
这里需要去除server部署的项目名

## 配置数据库
本项目提供orale版本表脚本以及相应的初始化脚本
- 安装一个orlce数据库，并选择一个用户，配置相应的表空间和权限
- 执行全量脚本
- 修改resources/spring目录下，proxool.xml数据库配置文件

## 目录结构说明

```
├─doc
│  └─全量脚本.sql
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
  - 本地包如何添加？
  - oracle如何引入
  - 本地包如何正确打入war包
  - nodejs需要安装嘛？
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
> 后台
  - 整体主要框架是什么？
  - 数据库连接池使用的是哪个？
  - web.xml配置哪些内容？
  - spring-config配置哪些内容？
  - OpenSessionInViewFilter是什么？
  - java melody是什么？
  - smartbi后台如何嵌入？
  - spring mvc message contents配置什么？
  - 权限如何拦截？
  - sprng mvc resources 如何配置？
  - 拦截器方式配置事务？
  - database property配置
  - DataSource配置
  - sessionfactory配置
  - session包扫描
  - 日志的配置
  - basehibernateDao配置以及作用
  - 常量管理
  - 表设计和表关系
  - smartbi linux如何实现部署？
  - 如何输出日志，如何记录日志到到日志表？
