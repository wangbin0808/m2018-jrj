package cn.com.jrj.m.controller;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.jrj.m.domain.ReturnWrapper;
import cn.com.jrj.m.service.cms.CmsService;
import cn.com.jrj.m.service.mongo.MongoNewsService;

/**
 * @Description: 前端接口
 * @author yuhai.li  
 * @date 2018年1月25日 下午5:54:23
 */
@RestController
@Slf4j
@RequestMapping("api/news")
public class NewsController {
	
	@Autowired
	private MongoNewsService mongoNewsService;
	@Autowired
	private CmsService cmsService;
	
	/**
	 * @Description: 分页查询首页新闻
	 * @param date 最后一条makeDate
	 * @param cnt 数量
	 * @param d 查询方向，0-时间向后查 1-时间向前查
	 * @return
	 * @return List<MongoNews>
	 * @author yuhai.li
	 * @date 2018年1月25日 下午5:54:46
	 */
	@RequestMapping("indexNews")
	public ReturnWrapper indexNews(
			@RequestParam(value="n", required=false) Integer n, 
			@RequestParam(value="date",required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date,
			@RequestParam(value="id", required=false) String id,
			@RequestParam(value="d",required=false) Integer d ){
		ReturnWrapper rst = new ReturnWrapper();
		if(d==null || d<0){
			d=0;
		}
		if(n==null){
			n=50;
		}else if(n>1000) {	//	限制最大1000条
			n=1000;
		}
		log.info("首页新闻列表   n:{} d:{} date:{} iiid:{}",n,d,date,id);
		rst.setData(mongoNewsService.getIndexNews(date,id,n,d));
		return rst;
	}
	
	/**
	 * @Description: 分页查询首页新闻
	 * @param iiid 最后一条iiid
	 * @param cnt 数量
	 * @param d 查询方向，0-时间向后查 1-时间向前查
	 * @return 
	 * @author yuhai.li
	 * @date 2018年1月25日 下午5:54:46
	 */
	@RequestMapping("indexTop")
	public ReturnWrapper indexTop(){
		log.info("首页主编推荐新闻！");
		ReturnWrapper rst = new ReturnWrapper();
		rst.setData(mongoNewsService.getIndexTop());
		return rst;
	}
	
	/**
	 * @Description: 首页推广位
	 * @return List<String>
	 * @author yuhai.li
	 * @date 2018年1月26日 下午2:30:22
	 */
	@RequestMapping("indexFrag")
	public ReturnWrapper indexFrag(){
		log.info("首页推广位接口");
		ReturnWrapper rst = new ReturnWrapper();
		rst.setData(cmsService.getPromoteFrag());
		return rst;
	}
	
}
