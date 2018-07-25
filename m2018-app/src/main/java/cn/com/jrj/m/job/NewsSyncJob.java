package cn.com.jrj.m.job;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.jrj.m.domain.CmsNews;
import cn.com.jrj.m.domain.InfoStock;
import cn.com.jrj.m.domain.MongoNews;
import cn.com.jrj.m.domain.MongoNewsTask;
import cn.com.jrj.m.service.cms.CmsService;
import cn.com.jrj.m.service.mongo.MongoNewsService;
import cn.com.jrj.m.service.mongo.MongoTaskService;
import cn.com.jrj.m.utils.Constant;

/**
 * @Description: 资讯导库job
 * @author yuhai.li  
 * @date 2018年1月23日 下午6:21:49
 */
@Component
@Slf4j
public class NewsSyncJob {
	
	@Autowired
	private CmsService cmsService;
	@Autowired
	private MongoNewsService mongoNewsService;
	@Autowired
	private MongoTaskService mongoTaskService;
	
	/**
	 * @Description: 任务池（mongodb task库）导库，入库（mongodb news库）
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月23日 下午6:22:03
	 */
	@Scheduled(cron="0/5 * * * * ?")	//	5s一次
	public void newsSync(){
		log.info("-------资讯导库任务 start--------");
		Long start = System.currentTimeMillis();
		int sucNum = 0;	//	成功导库数量
		// 池中取数据
		List<MongoNewsTask> tasks = mongoTaskService.getTasks(Constant.SYNC_NEWS_NNM);
		if(tasks!=null && tasks.size()>0){
			log.info("任务池取出{}条导库任务",tasks.size());
		}else{
			log.info("任务池暂无导库任务 task pool is empty now! ");
			return;
		}
		
		int updateNum = 0;	//	更新数量
		int insertNum = 0;	//	新增数量
		
		/**
		 * 执行导库任务，可改为批量操作
		 */
		for(MongoNewsTask task:tasks){
			int iiid = task.getIiid();
			int op = task.getOp();
			//	正常id
			if(iiid>0){
				if(op==1){	//	删除数据库
					mongoNewsService.deteleByIiid(iiid);
					mongoTaskService.deleteById(task.getId());
					sucNum++;
					log.info("iiid:{} op:{} 删除mongo资讯成功！",iiid,op);
				}else if(op==0){	//	新增或更新
					//	导库
					CmsNews news = cmsService.getNewsByIiid(iiid);
					if(news==null){	//	没有对应的资讯，暂不进行操作
						log.warn("iiid:{} op:{} 没有对应的资讯！",iiid,op);
						continue ;
					}else if(news.getInfoStation()!=5){
						log.warn("iiid:{} 文章状态不是已发布状态，状态为:{}",iiid,news.getInfoStation());
						mongoTaskService.deleteById(task.getId());
						continue ;
					}
					MongoNews mongoNews = mongoNewsService.getNewsByIiid(iiid);
					List<InfoStock> stockList = cmsService.getInfoStockByIiid(iiid);
					if(mongoNews!=null){	//	更新操作
						String id = mongoNews.getId();
						MongoNews mongoNews2 = new MongoNews(news);	//	由于不知道哪些字段更新，需要重新初始化
						mongoNews2.setId(id);
						mongoNews2.setClick(mongoNews.getClick());
						//	评论数改为从接口中获取
						mongoNews2.setComment(mongoNewsService.getComment(news.getPlId()));
						mongoNews2.setCtime(mongoNews.getCtime());
						mongoNews2.setInfoStocks(stockList);
						mongoNewsService.saveNews(mongoNews2);
						updateNum++;
						log.info("iiid:{} already in mongodb. update this record. id:{}",iiid,id);
					}else{
						MongoNews rst = mongoNewsService.saveNews(task, news, stockList);
						insertNum++;
						log.info("iiid:{} is stored to mongodb. id:{}",iiid, rst.getId());
					}
					mongoTaskService.deleteById(task.getId());
					sucNum++;
					log.info("iiid:{} op:{} 资讯导库成功！",iiid,op);
				}
			}
		}

		log.info("--------资讯导库任务 end. 任务：{}条，成功导库{}条，其中新增{}条，更新{}条， cost {}ms--------", tasks.size(), sucNum, insertNum, updateNum, (System.currentTimeMillis()-start));
	}
}
