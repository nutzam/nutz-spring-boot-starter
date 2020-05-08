# nutz-spring-boot-starter

## 概述

nutz-spring-boot-starter 是提供在 spring-boot 环境下使用 nutz 相关功能的插件，使用 spring-boot auto configuration 机制实现，引入 jar 即可使用。so easy！！！

## 开始使用

使用之前请确保熟悉使用 spring-boot 进行项目开发，本文档仅限于 nutz-spring-boot-starter 插件的相关描述，如有 spring-boot 相关问题请查阅 https://spring.io/projects/spring-boot 。

### 依赖添加

nutz-spring-boot-starter release 版本已发布至 maven 中央仓库，使用 maven 进行依赖管理请添加如下依赖信息：

```xml
<dependency>
	<groupId>org.nutz</groupId>
	<artifactId>nutz-spring-boot-starter</artifactId>
	<version>2.2.7.RELEASE</version>
</dependency>
```

snapshot 版本发布至 https://jfrog.nutz.cn/artifactory/snapshots, 如需使用请添加如下仓库设置内容：

```xml
<repositories>
	<repository>
		<id>nutz</id>
		<url>https://jfrog.nutz.cn/artifactory/jcenter</url>
	</repository>
	<repository>
		<id>nutz-snapshots</id>
		<url>https://jfrog.nutz.cn/artifactory/snapshots</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
		<releases>
			<enabled>false</enabled>
		</releases>
	</repository>
</repositories>
```

使用其他依赖管理工具请根据 maven 配置进行调整。

### 使用 dao

引入 jar 包在配置了数据源的前提下将自动注入 Dao 对象，nutz-dao 的具体使用请参考 https://www.nutzam.com

#### 配置

使用过程中可以对 nutz-dao 的自动建表和变更、自定义 sql 和 sql template 相关信息进行定制化配置，具体参照下文详细说明。

#### 自动建表

自动建表和变更是 nutz 提供的自动 ddl 功能，解决在开发过程中数据表频繁表更导致代码和数据库定义的频繁更改问题。因可能进行字段删除等相关操作导致数据丢失，建议在生成环境禁用相关功能。相关配置项目详细说明如下：

```yml
nutz:
  dao:
    runtime:
      create: true #是否自动建表 默认true
      migration: true #是否自动变更 默认true
      add-column: true # 是否添加列 默认true
      delete-column: true # 是否删除列 默认true
      foce-create: false # 是否删表重建，注意此功能会删除全部表及数据，一般应用于demo或测试 默认false
      check-index: true # 是否检查索引 默认true
      basepackage: # 相关实体所在包
        - org.nutz.demo.bean
```

根据 nutz 官方描述，相关包下配置了@Table 注解的实体均会自动创建表，自动建表有以下局限性：

- 不生成外键,我们也不推荐用外键
- 只能解决一般建表需求,复杂的表结构请通过自定义 sql 完成
- 由于 spring-boot 资源扫描原因，如打包后使用需要在包名配置前加上 BOOT-INF.classes.

#### 自定义 sql

> Nutz.Dao 提供了大多数简单的操作，在 80%以上的情况下，你并不需要编写 SQL，因为 Nutz.Dao 会自动替你 生成可以使用的 SQL。但是，在某些特殊的情况下，尤其是考虑到效率等问题，直接写作 SQL 仍然是程序员们 的一个杀手锏，有了这个杀手锏，程序员们永远可以针对任何数据库做他们想要的任何操作。

nutz-dao 默认扫描 sqls 目录下的全部.sql/.sqls/.sqlx 文件相关，自定义配置及说明如下：

```yml
nutz:
  dao:
    sql-manager:
      mode: file #自定义sql管理模式 file和xml
      paths: # 自定义sql文件路径
        - sqls
```

#### sql template

sql template 是使用模版引擎在 nutz-dao 的自定义 sql 相关参数注入之前对原始 sql 进行处理的机制，能实现类似 mybatis 的动态 sql 效果。目前内置支持 BEETL、 FREEMARKER、JETBRICK 和 VELOCITY 四种模版引擎。

使用前需要自行引入相关模板引擎依赖，同时不提供对模板引擎本身的任何配置支持。相关配置及说明如下：

```yml
nutz:
  dao:
    sql-template:
      enable: true #是否启用 默认false
      type: beetl #模板引擎类型，默认beetl
```

### 使用 json

json 支持是使用 nutz-json 对 spring-mvc HttpMessageConverter 扩展实现对象序列化和反序列化的的功能，相关功能默认启用。

#### 定制 json

使用过程中可以对 json 格式进行自定义，相关配置说明如下：

```yml
nutz:
  json:
    enabled: true #是否启用，默认true
    mode: compact #json模式，默认compact
    ignore-null: true # 是否忽略空对象
    compact: true # 是否启用压缩模式
    date-format: yyyy-MM-dd Hh:mm:ss # 日期格式化
    ... #更多相关配置参照ide提示进行即可
```

### 使用 ngrok

ngrok 是由 https://nutz.cn 提供的内网穿透支持，可以用于本地项目的演示或者类似微信开发类公网接口的调试。相关配置如下：

```yml
nutz:
  ngrok:
    port: 8080 #代理的本地端口，默认使用server.port信息
    token: abcdef #ngrok token 请到 https://nutz.cn 申请
```

## 支持和维护

相关技术支持请联系作者：kerbores#gmail.com
