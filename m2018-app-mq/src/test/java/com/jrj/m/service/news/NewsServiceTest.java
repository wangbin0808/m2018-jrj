/**
 * 
 */
package com.jrj.m.service.news;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jrj.m.model.Click;
import com.jrj.m.model.Task;
import com.jrj.m.service.task.TaskService;

/**
 * @author tao.wang@jrj.com.cn
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {
	@Autowired
    private  NewsService newsService;
	
	@Autowired
    private  TaskService taskService;
	
	@Test
	public void testSaveNews() throws Exception {
		Click news=new Click();
		news.setIiid(112);
		news.setTotal(112221233);
		newsService.saveNews(news);
		
		Task task=new Task();
		task.setPlid(1112);
		task.setTotal(12);
		//taskService.saveTask(task);
	}
	
	public void testUpdateNews(){
		newsService.updateNewsClick(112, 211112);
		System.out.println(".............");
	}
}
