
## 前言

　　

## 项目介绍

　通用平台接口系统，主要负责打通各系统之间壁障 ，实现各系统之间数据互通

### 组织结构

```
esb
├── esb-core -- 数据存储、转发
├── esb-platform -- 模拟接口web平台
├── esb-protal -- 负责接口调用
├── esb-query --提供日志、异常、统计等查询功能
├── uippush-client-api -- api接口模块

```

### 技术选型

#### 前端技术:


#### 后端技术:
-spring 
-springmvc 
-mongoDB  数据库
-kafka   消息中间件
-zookeeper 注册中心
-dubbo  微服务


#### 架构图

系统架构：参考document/通用平台架构图.png
接口设计：参考document/通用平台接口示意图.png

#### 模块依赖
esb-core  esb-platform  esb-protal  esb-query 四个模块依赖 uippush-client-api


#### 模块介绍

> esb-core
	esb核心模块，负责异步接口的数据的存储转发。
> esb-platform
	esb 平台模块，模拟提供 esb依赖的 uip_trade 的相关服务
> esb-protal
	负责缓存接口信息，提供各类接口（主动、被动、同步、异步、输入、输出）调用服务。
> esb-query
	esb 查询模块，主要提供系统监控数据，环境信息，接口调用相关报表，系统调用接口日记查询
> uippush-client-api
	各模块，包含uip-trade 的公共api


## 环境搭建


#### 开发工具:
- IDEA 
- Tomcat: 应用服务器
- Git: 版本管理
- maven jar包管理

#### 开发环境：
- Jdk8+
- windows10/mac os X


### 工具安装
-安装idea 选择合适版本
idea ：https://www.jetbrains.com/idea/download/#section=windows

-安装jdk:选择1.8+
jDK:https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

-安装tomcat 选择合适版本
tomcat:https://tomcat.apache.org/download-80.cgi

-安装 maven选择合适版本：

maven:https://maven.apache.org/download.cgi

-安装docker

环境搭建主要是docker环境搭建 ，使用docker-compose管理docker image,目前kafka,mogondb,zookeeper ，dubbo-admin 都安装在docker内，使用容器来管理;

首先需要安装docker 然后安装docker-compose两者安装完毕后（提供ubuntu安装步骤文档：document/ubuntu安装dorcker.txt）
如果需要更加详细的文档，请参考docker官方文档
docker:https://docs.docker.com/v17.09/engine/installation/linux/docker-ce/ubuntu/ （有windows安装文档）
docker-compose：https://docs.docker.com/compose/install/  （有windows安装文档）

## 开发指南:

### 编译流程
idea Terminal 内输入命令 ：mvn clean install

### maven profile

local: 本地开发环境，使用esb-core模块 docker 目录中container zk mongodb kafka等环境运行
2.111: 公司测试环境，使用部署在北京办公室网络中的（2.125， 2.111，2.19）上面部署的 zk mongodb kafka
prod: 生产环境，使用部署在腾讯云（49.235.92.182）上的 zk mongodb kafka运行

### profile 配置
如果以上profile有变动，要以改动根目录下面的 pom.xml文件中的以下配置：
<profiles>
   <profile>
        <properties>
更改以上元素中的相关属性以更改profile配置

### 启动顺序

本地开发：

*选择 local profile*

启动docker: cd path_to_project_root/esb-core/docker docker-compose up

启动esb-core 直接运行EsbCoreApplication.java 

启动 esb-query 直接运行 EsbQueryApplication.java

启动esb-platform 直接运行EsbPlatformApplication.java

启动esb-protal tomcat启动,idea内直接启动工程

测试部署：

### 部署方式
选择 2.111 profile 打包 *mvn install -P 2.111*

然后将打包文件上传到云服务器相关目录替换运行


### 框架规范约定

约定优于配置(convention over configuration)，此框架约定了很多编程规范，下面一一列举：

```

- service类，需要在叫名`service`的包下，并以`Service`结尾，如`CmsArticleServiceImpl`

- controller类，需要在以`controller`结尾的包下，类名以Controller结尾，如`CmsArticleController.java`，并继承`BaseController`

- spring task类，需要在叫名`task`的包下，并以`Task`结尾，如`TestTask.java`

- mapper.xml，需要在名叫`mapper`的包下，并以`Mapper.xml`结尾，如`CmsArticleMapper.xml`

- mapper接口，需要在名叫`mapper`的包下，并以`Mapper`结尾，如`CmsArticleMapper.java`

- model实体类，需要在名叫`model`的包下，命名规则为数据表转驼峰规则，如`CmsArticle.java`

- spring配置文件，命名规则为`applicationContext-*.xml`

- 类名：首字母大写驼峰规则；方法名：首字母小写驼峰规则；常量：全大写；变量：首字母小写驼峰规则，尽量非缩写

- springmvc配置加到对应模块的`springMVC-servlet.xml`文件里

- 配置文件放到`src/main/resources`目录下

- 静态资源文件放到`src/main/webapp/resources`目录下

- jsp文件，需要在`/WEB-INF/jsp`目录下

- `RequestMapping`和返回物理试图路径的url尽量写全路径，如：`@RequestMapping("/manage")`、`return "/manage/index"`

- `RequestMapping`指定method

- 模块命名为`项目`-`子项目`-`业务`，如`esb-cms-admin`

- 数据表命名为：`子系统`_`表`，如`cms_article`

- 更多规范，参考[[阿里巴巴Java开发手册]

```


