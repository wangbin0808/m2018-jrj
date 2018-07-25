package cn.com.jrj.m;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.jrj.m.service.cms.CmsService;
import cn.com.jrj.m.service.mongo.MongoNewsService;
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
	@Autowired
	private MongoNewsService newsMongoService;
	
	@Test
	public void contextLoads() {
		
	}

}
