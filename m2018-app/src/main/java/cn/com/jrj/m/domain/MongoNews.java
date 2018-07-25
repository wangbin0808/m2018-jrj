package cn.com.jrj.m.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import cn.com.jrj.m.utils.DateUtil;
import cn.com.jrj.m.utils.StringUtil;

/**
 * @Description: mongodb新闻实体
 * @author yuhai.li  
 * @date 2018年1月19日 下午3:40:21
 */
@Data
@Document(collection="news")
public class MongoNews {
	/**
	 * 主键
	 */
	@Id
	private String id;
	/**
	 * 新闻iiid
	 */
	private int iiid;
	/**
	 * 评论id
	 */
	private Integer plId;
	/**
	 * 新闻标题
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
	 * 7*24小时滚动要闻 1-是 0-否
	 */
	private int rollImportentNew;
	/**
	 * 媒体来源
	 */
	private String paperMediaSource;
	/**
	 * url
	 */
	private String infoUrl;
	/**
	 * 要闻 0-否 1-一级要闻 2-二级要闻
	 */
	private int importentNew;
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
	 * 设置列表时间戳
	 */
	private Date listDate;
	/**
	 * 频道id
	 */
	private String chanNum;
	/**
	 * 栏目id
	 */
	private String infoCls;
	/**
	 * 摘要
	 */
	private String detail;
	/**
	 * 是否推送手金首页 1-推送 0-不推送
	 */
	private int level;
	/**
	 * 缩略图，之后手金首页新闻才有缩略图
	 */
	private List<String> picThumb;
	/**
	 * app置顶（主编推荐）
	 */
	private int appTop;
	/**
	 * 图片链接，多个图片中间用逗号隔开
	 */
	private List<String> imgUrl;
	/**
	 * M站资讯页对应链接
	 */
	private String mInfoUrl;
	/**
	 * app对应链接
	 */
	private String appInfoUrl;
	/**
	 * 加粗 1-是 0/null-否
	 */
	private int isBlob;
	/**
	 * 加红 1-是 0/null-否
	 */
	private int isRed;
	/**
	 * 评论数
	 */
	private int comment;
	/**
	 * 点击数
	 */
	private int click;
	/**
	 * 资讯类型
	 */
	private int newsType;
	/**
	 * 相关股票
	 */
	private List<InfoStock> infoStocks;
//	/**
//	 * 操作类型 0-增加或更新 1-删除
//	 */
//	private int op;
	/**
	 * 资讯所在目录  makedate yyyyMMdd
	 */
	private String path;
	/**
	 * 频道号+栏目号
	 */
	private String chanCls;
	/**
	 * 推荐 1-是 0/null-否
	 */
	private int recommend;
	/**
	 * 插入数据时间
	 */
	private Date ctime;
	/**
	 * 最新更新时间
	 */
	private Date utime;
	
	@Override
	public String toString(){
		return "\"MongoNews:{\"id\":\""+id+"\",\"iiid\":\""+iiid+"\",\"title\":\""+title+"\"}\"";
	}

	public MongoNews(){}
	
	/**
	 * 初始化
	 */
	public MongoNews(CmsNews news) {
		if(news.getAppTop()==null){
			this.appTop=0;
		}else{
			this.appTop = news.getAppTop();	
		}
		this.author = news.getAuthor();
		this.chanNum = news.getChanNum();
		this.detail = news.getDetail();
		this.iiid = news.getIiid();
		this.imgUrl = StringUtil.splitStr(news.getImgUrl(), ",", false);	//	图片字符串转换为数组，图片为逗点分隔的字符串
		if(news.getImportentNew()==null || news.getImportentNew()==0){
			this.importentNew=0;
		}else{
			this.importentNew = news.getImportentNew();	
		}
		this.infoCls = news.getInfoCls();
		this.infoUrl = news.getInfoUrl();
		if (news.getIsBlod()==null || news.getIsBlod()==0) {
			this.isBlob=0;
		}else{
			this.isBlob = news.getIsBlod();
		}
		if(news.getIsRed()==null || news.getIsRed()==0){
			this.isRed=0;
		}else{
			this.isRed = news.getIsRed();
		}
		this.keyword = news.getKeyword();
		this.leaderEtte = news.getLeaderEtte();
		this.listDate = news.getListDate();
		this.makeDate = news.getMakeDate();
		this.makeMan = news.getMakeMan();
		this.mInfoUrl = news.getMInfoUrl();
		this.appInfoUrl = news.getAppInfoUrl();
		this.paperMediaSource = news.getPaperMediaSource();
		if(news.getLevel()==null || news.getLevel()==0){
			this.level=0;
		}else{
			this.level = news.getLevel();
		}
		this.picThumb = StringUtil.splitStr(news.getPicThumb(), ",", false);
		if(news.getRollImportentNew()==null || news.getRollImportentNew()==0){
			this.rollImportentNew = 0;
			this.recommend = 0;	//	7*24小时新闻为推荐新闻
		}else{
			this.rollImportentNew = news.getRollImportentNew();
			this.recommend = 1;	//	7*24小时新闻为推荐新闻
		}
		this.plId = news.getPlId();
		this.title = news.getTitle();
		
		if(this.chanNum!=null && !this.chanNum.equals("") && this.infoCls!=null && !news.getInfoCls().equals("")){	//	chanNum+infoCls
			this.chanCls = this.chanNum+this.infoCls;
		}
		if(this.makeDate!=null){
			this.path=DateUtil.format(makeDate, "yyyyMMdd");
		}
		//	默认类型
		this.newsType=0;	//	文章页默认为0
		this.utime=new Date();
	}
}
