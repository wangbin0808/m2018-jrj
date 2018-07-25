项目说明：
	m站首页改版接口项目，用于提供接口供cms触发写数据到任务池和提供前端调用接口

相关技术如下：
	框架：springboot
	数据库：mongodb
	关系型数据库：sqlserver
	缓存：redis集群
	容器：undertow

代码结构说明：
	cn.com.jrj.m	主函数
	cn.com.jrj.m.config	配置（包括多数据库配置和redis配置等）
	cn.com.jrj.m.domain	实体类
	cn.com.jrj.m.dao.*	多数据源分库dao
	cn.com.jrj.m.aop.*	切面
	cn.com.jrj.m.mapper.*	mybatis配置文件
	cn.com.jrj.m.repository.*	分库相关的Repository
	cn.com.jrj.m.service.*	逻辑处理类
	cn.com.jrj.m.controller.*	控制器
	cn.com.jrj.m.utils。*	工具类
	cn.com.jrj.m.interceptor。*	拦截器
