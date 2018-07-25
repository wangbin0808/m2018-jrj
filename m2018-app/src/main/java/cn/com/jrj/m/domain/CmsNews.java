package cn.com.jrj.m.domain;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;

import lombok.Data;
/**
 * @Description: 新闻实体
 * @author yuhai.li  
 * @date 2018年1月18日 下午5:53:30
 */
@Data
@Document(collection="news")
public class CmsNews {
		
	/**
	 * iiid
	 */
	private Integer iiid;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 关键词
	 */
	private String keyword;
	/**
	 * 7*24小时滚动要闻 1-是 0/null-否
	 */
	private Integer rollImportentNew;
	/**
	 * 媒体来源
	 */
	private String paperMediaSource;
	/**
	 * 资讯urlpc
	 */
	private String infoUrl;
	/**
	 * 要闻 0-否 1-一级要闻 2-二级要闻
	 */
	private Integer importentNew;
	/**
	 * 责任编辑（签发人）
	 */
	private String makeMan;
	/**
	 * 新闻怎么来的（比如你自动发稿的  是机器人）
	 */
	private String leaderEtte;
	/**
	 * 发布时间
	 */
	private Date makeDate;
	/**
	 * 列表时间
	 */
	private Date listDate;
	/**
	 * 频道号
	 */
	private String chanNum;
	/**
	 * 栏目号
	 */
	private String infoCls;
	/**
	 * 摘要
	 */
	private String detail;
	/**
	 * 是否推送手金首页 1-推送 0/null-不推送
	 */
	private Integer level;
	/**
	 * 缩略图
	 */
	private String picThumb;
	/**
	 * 0-不置顶、1-置顶
	 */
	private Integer appTop;
	/**
	 * 图片url，多个图片url中间用逗点,隔开
	 */
	private String imgUrl;
	/**
	 * 资讯对应M站url
	 */
	private String mInfoUrl;
	/**
	 * 资讯对应的APP上url
	 */
	private String appInfoUrl;
	/**
	 * 加粗
	 */
	private Integer isBlod;
	/**
	 * 加红
	 */
	private Integer isRed;
	/**
	 * 资讯对应的评论id
	 */
	private Integer plId;
	/**
	 * 1-未审核 2-已发布1 3-已签发1 4-已签发 5-已发布 6-已发布待删除 7-已发布待修改 8-拒绝签发 9-已撤稿 10-视频
	 */
	private Integer infoStation;
	
	@Override
	public String toString(){
//		return "hello";
		return JSON.toJSONString(this);
	}
}
