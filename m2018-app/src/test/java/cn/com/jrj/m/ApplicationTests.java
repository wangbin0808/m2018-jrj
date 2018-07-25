package cn.com.jrj.m;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.jrj.m.service.cms.CmsService;
import cn.com.jrj.m.service.pg.PgService;


/**
 * @Description: 测试类入口
 * @author yuhai.li  
 * @date 2018年1月19日 下午2:36:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTests {

	@Autowired
	private CmsService cmsService;
	@Autowired
	private PgService pgService;
	
	@Test
	public void contextLoads() {
		
//		log.info("*******pg start*************");
//		log.info(new Integer(pgService.top5Fund().size()).toString());
//		List<Fund> list = pgService.top5Fund();
//		for(Fund fund:list){
//			System.out.println(fund.getFundCode());
//		}
//		log.info("*******pg end*************");
		
//		log.info("*******cms start*************");
//		List<CmsNews> list = cmsService.getTop10List();
//		log.info(list.size()+"");
//		for(CmsNews news:list){
//			log.info(news.getTitle());
//		}
		
//		MongoNews news = newsMongoService.saveNews2();
//		System.out.println("保存新闻id："+news.getId());
		
//		List<MongoNews> list = newsMongoService.getTop10List();
//		System.out.println("新闻数量："+list.size());
//		for(MongoNews mn:list){
//			if(mn!=null){
//				System.out.println(mn.getIiid());				
//			}
//		}
		
//		log.info("*******cms end*************");
		
	}

}
