
项目说明：
	m站首页改版导库任务项目，用于从cms库导新闻数据到mongodb新闻库，并同步评论数和点击数

相关技术如下：
	框架：springboot
	消息队列：kafka（zookeeper?）
	数据库：mongodb
	关系型数据库：sqlserver
	缓存：redis集群（可用于修改缓存数据供接口项目使用）
	定时任务：spring-schedule

代码结构说明：
	cn.com.jrj.m	主函数
	cn.com.jrj.m.config	配置（包括多数据库配置和redis配置等）
	cn.com.jrj.m.domain	实体类
	cn.com.jrj.m.dao.*	多数据源分库dao
	cn.com.jrj.m.aop.*	切面
	cn.com.jrj.m.kafka	kafka消息队列相关
	cn.com.jrj.m.mapper.*	mybatis配置文件
	cn.com.jrj.m.repository.*	分库相关的Repository
	cn.com.jrj.m.service.*	逻辑处理类
	cn.com.jrj.m.utils。*	工具类

	
	
	
	
