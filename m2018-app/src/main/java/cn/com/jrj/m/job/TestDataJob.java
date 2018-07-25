package cn.com.jrj.m.job;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.jrj.m.domain.CmsNews;
import cn.com.jrj.m.domain.KafkaMsg;
import cn.com.jrj.m.domain.MongoNews;
import cn.com.jrj.m.domain.MongoNewsTask;
import cn.com.jrj.m.service.cms.CmsService;
import cn.com.jrj.m.service.mongo.MongoNewsService;
import cn.com.jrj.m.service.mongo.MongoTaskService;
import cn.com.jrj.m.utils.DateUtil;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 资讯导库job
 * @author yuhai.li  
 * @date 2018年1月23日 下午6:21:49
 */
@Component
@Slf4j
public class TestDataJob {
	
	@Autowired
	private CmsService cmsService;
	@Autowired
	private MongoNewsService mongoNewsService;
	@Autowired
	private MongoTaskService mongoTaskService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
    
    /**
     * @Description: 获取mongo新闻库中最新的makedate，查找大于makedate的数据
     * @return void
     * @author yuhai.li
     * @date 2018年2月7日 上午11:48:20
     */
//	@Scheduled(cron="0 0/5 * * * ?")	//	10分钟同步一次最新新闻到任务池
    public void newData(){
    	log.info("***** 新资讯导库任务 start *****");
    	MongoNews latestNews = mongoNewsService.getTopOneByMakeDate();
    	List<Integer> list = cmsService.getNewsIiidLate(latestNews.getMakeDate(), latestNews.getIiid());
    	
    	log.info("***** mongodb中最新新闻makedate：{}, 新的新闻数量：{} ****",DateUtil.format(latestNews.getMakeDate(), "yyyy-MM-dd HH:mm:ss"),list.size());
    	
    	List<MongoNewsTask> taskList = new LinkedList<MongoNewsTask>();
		for(Integer iiid:list){
			MongoNewsTask task = new MongoNewsTask();
			task.setIiid(iiid);
			task.setOp(0);	//	新增或更新
			task.setCtime(new Date());
			taskList.add(task);
		}
		mongoTaskService.insertAll(taskList);
		log.info("***** 新资讯导库任务 end. 新增任务：{} *****",taskList.size());
    }
    
    /**
     * @Description: 一周的资讯放入任务池
     * @return void
     * @author yuhai.li
     * @date 2018年2月7日 上午10:02:31
     */
//    @Scheduled(fixedRate=200000000)	//	启动执行一次，单位ms
    public void testDataOneWeek(){
    	log.info("****** 一周资讯数据加入任务池 start *****");
    	
    	Date now = new Date();
    	int i=6;
    	Date lastDate = addDays(now, 0-i);
    	while(lastDate.before(new Date())){
        	lastDate = addDays(now, 0-i);
        	Date nextDate = addDays(lastDate, 1);
        	List<Integer> list = cmsService.getIiids(lastDate,nextDate);
        	
        	log.info("****** {} 资讯 {} 条插入任务池 start *****",DateUtil.format(nextDate, "yyyy-MM-dd"),list.size());
        	
        	List<MongoNewsTask> taskList = new LinkedList<MongoNewsTask>();
    		for(Integer iiid:list){
    			MongoNewsTask task = new MongoNewsTask();
    			task.setIiid(iiid);
    			task.setOp(0);	//	新增或更新
    			task.setCtime(new Date());
    			taskList.add(task);
    		}
    		mongoTaskService.insertAll(taskList);
    		
    		log.info("****** {} 资讯 {} 条插入任务池 end 成功加入任务池数量：{} *****",DateUtil.format(nextDate, "yyyy-MM-dd"), list.size(), taskList.size());
    		i--;
    	}
    	log.info("****** 一周资讯数据加入任务池 end *****");
    }
    

	/**
	 * @Description: 资讯导库任务池测试数据导入
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月25日 上午9:57:24
	 */
//	@Scheduled(fixedRate=200000000)	//	启动执行一次，单位ms
	public void testDataForNewsTask(){
		log.info("***** 导库测试数据写入，300条满足M站首页显示的数据， 300条最新新闻数据*****");
		
		log.info("************触发数据入库 300条满足M站首页显示的数据   start***********");
		List<CmsNews> indexList = cmsService.getIndexList(300);
		List<MongoNewsTask> taskList1 = new LinkedList<MongoNewsTask>();
		for(CmsNews news:indexList){
			MongoNewsTask task = new MongoNewsTask();
			task.setIiid(news.getIiid());
			task.setOp(0);	//	新增或更新
			task.setCtime(news.getMakeDate());
			taskList1.add(task);
		}
		mongoTaskService.insertAll(taskList1);
		log.info("************触发数据入库  300条满足M站首页显示的数据  end***********{}", taskList1.size());
		
//		log.info("************触发数据入库 300条最新新闻数据   start***********");
//		List<CmsNews> topList = cmsService.getTopList(300);
//		List<MongoNewsTask> taskList2 = new LinkedList<MongoNewsTask>();
//		for(CmsNews news:topList){
//			MongoNewsTask task = new MongoNewsTask();
//			task.setIiid(news.getIiid());
//			task.setOp(0);	//	新增或更新
//			task.setCtime(news.getMakeDate());
//			taskList2.add(task);
//		}
//		mongoTaskService.insertAll(taskList2);
//		log.info("************触发数据入库  300条最新新闻数据 ***********{}",taskList2.size());
	}
	
	/**
	 * @Description: kafka队列数据写入，最近300条新闻，随机给一个点击数或评论数
	 * @return void
	 * @author yuhai.li
	 * @date 2018年2月2日 上午10:19:46
	 */
//	@Scheduled(cron="0 0/30 * * * ?")	//	每30分钟更新一次数据最新300条新闻的点击数
	public void testDataKafkaTask(){
		log.info("****** 300条最新新闻数据，设定一个随机数作为点击数 start ********");
		Random random = new Random(47);
		List<Integer> topList = cmsService.getTopListId(300);
		for(Integer iiid:topList){
			KafkaMsg clickMsg = new KafkaMsg();
			clickMsg.setIiid(iiid);
			int total = random.nextInt(1100);
			clickMsg.setTotal(total);
			log.info("iiid：{}，click：{}",iiid,total);
			kafkaTemplate.send("m.click.topic", JSON.toJSONString(clickMsg));
		}

		log.info("****** 300条最新新闻数据，设定一个随机数作为点击数 start ********");
	}
	
	/**
	 * @Description: 获取当前日期的前一日
	 * @param date 当前日期
	 * @return Date
	 * @author yuhai.li
	 * @date 2018年2月7日 上午10:07:04
	 */
	public Date addDays(Date date, int n){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}
	
}
