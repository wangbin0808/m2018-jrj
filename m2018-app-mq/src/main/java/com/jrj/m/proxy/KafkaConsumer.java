package com.jrj.m.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jrj.m.model.Comment;
import com.jrj.m.model.Click;
import com.jrj.m.service.news.NewsService;

/**
 * @Description: kafka队列消费者，用于同步新闻点击量和评论数
 * @author yuhai.li  
 * @date 2018年1月23日 下午4:47:44
 */
@Component
public class KafkaConsumer {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${click_num}")
	private int click_num;
	@Autowired
    private  NewsService newsService;

	/**
	 * @Description: 点击数同步
	 * @param msg 消息类型与配置文件中value-deserializer 一致
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月23日 下午4:49:16
	 */
    @KafkaListener(topics = {"m.click.topic"})
    public void clickSync(@Payload String msg){
    	log.info("-------m.click.topic 接收到消息：{} -----",msg);
		
		Click news = null;
		try {
			news = JSON.parseObject(msg, Click.class);
		} catch (Exception e) {
			log.warn(" format of msg is not json！！  msg：{}",msg);
			return ;
		}
		
		if(news==null){
			log.warn(" object is null ");
			return ;
		}
		int num = 0;
		//如果点击大于1000 新闻导入
		if(news.getTotal()>=click_num){
			num = newsService.updateNewsClick(news.getIiid(), news.getTotal(),1);
		}else{
			num = newsService.updateNewsClick(news.getIiid(), news.getTotal());
		}
		
		log.info("-------m.click.topic 消息处理完毕！文章iiid：{}，点击数：{}，影响数据条数：{}-------",news.getIiid(),news.getTotal(),num);
    }
    

	/**
	 * @Description: 评论数同步
	 * @param msg 消息类型与配置文件中value-deserializer 一致
	 * @return void
	 * @author yuhai.li
	 * @date 2018年1月23日 下午4:49:16
	 */
    @KafkaListener(topics = {"m.comment.topic"})
    public void commentSync(@Payload String msg){
    	log.info("-------m.comment.topic 接收到消息：{} -----",msg);
    	
    	Comment comment = null;
		try {
			comment = JSON.parseObject(msg, Comment.class);
		} catch (Exception e) {
			log.warn(" format of msg is not json！！  msg：{}",msg);
			return ;
		}
		
		if(comment==null){
			log.warn(" object is null ");
			return ;
		}
		
		int num = newsService.updateNewsComment(comment.getPlid(), comment.getTotal());		
		log.info("-------m.comment.topic 消息处理完毕！ 评论id：{}，评论数：{}，影响数据条数：{}-------",comment.getPlid(), comment.getTotal(), num);
    }

}
