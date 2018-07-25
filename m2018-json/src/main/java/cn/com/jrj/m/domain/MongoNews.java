package cn.com.jrj.m.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import cn.com.jrj.m.utils.DateUtil;
import cn.com.jrj.m.utils.StringUtil;

import com.alibaba.fastjson.annotation.JSONField;

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
	private int plId;
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
	@JSONField(serialize=false)
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
	@JSONField(serialize=false)
	private String appInfoUrl;
	/**
	 * 加粗 1-是 0/null-否
	 */
	@JSONField(serialize=false)
	private int isBlob;
	/**
	 * 加红 1-是 0/null-否
	 */
	@JSONField(serialize=false)
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
	/**
	 * 操作类型
	 */
	@JSONField(serialize=false)
	private int op;
	/**
	 * 资讯所在目录  makedate yyyyMMdd
	 */
	@JSONField(serialize=false)
	private String path;
	/**
	 * 频道号+栏目号
	 */
	@JSONField(serialize=false)
	private String chanCls;
	/**
	 * 推荐 1-是 0/null-否
	 */
	@JSONField(serialize=false)
	private int recommend;
	/**
	 * 插入数据时间
	 */
	@JSONField(serialize=false)
	private Date ctime;
}
