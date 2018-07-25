package cn.com.jrj.m.controller;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.jrj.m.domain.MongoNewsTask;
import cn.com.jrj.m.domain.ReturnWrapper;
import cn.com.jrj.m.service.mongo.MongoNewsService;
import cn.com.jrj.m.service.mongo.MongoTaskService;

/**
 * @Description: 代理接口，用于写新闻导库任务到mongo任务池
 * @author yuhai.li  
 * @date 2018年1月30日 下午4:42:28
 */
@RestController
@Slf4j
@RequestMapping("api/task")
public class NewsTaskController {
	
	@Autowired
	private MongoNewsService mongoNewsService;
	@Autowired
	private MongoTaskService mongoTaskService;
	
//	@CrossOrigin(value="http://127.0.0.1")	//	cors跨域，打开注释生效
	@RequestMapping("news")
	public ReturnWrapper cmsNewsChg(@RequestParam("iiid") int iiid, @RequestParam("op") int op){
		log.info("-----触发接口写数据到新闻导库mongo任务池----- iiid：{}，op：{}",iiid,op);
		ReturnWrapper rst = new ReturnWrapper();
		if(iiid<0 || op<0){
			rst.setData(null);
		}else{
			MongoNewsTask task = new MongoNewsTask();
			task.setIiid(iiid);
			task.setOp(op);
			task.setCtime(new Date());
			try {
				rst.setData(mongoTaskService.save(task));
			} catch (Exception e) {
				rst.setRet(500);
				rst.setMsg(e.getMessage());
			}
		}
		return rst;
	}
	
}
