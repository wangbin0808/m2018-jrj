package com.jrj.wx.json.dao;

import com.jrj.wx.json.bean.PrizePool;
import com.jrj.wx.json.bean.User;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
/**
 * 
 * @author bin.wang
 * @date 2018.6.1
 *
 */
public abstract interface LuckDrawDao {
	/**
	 * 
	 * @param paramString 手机号
	 * @return
	 */
	@Select({ "select * from t_user where phonenum=#{encode}" })
	public abstract User getUserByPhoneNum(@Param("encode") String paramString);

	/**
	 * 保存用户
	 * @param paramUser
	 */
	@Insert({ "insert into t_user(phonenum,grandprize,createtime) values(#{phonenum},#{grandprize},#{createtime})" })
	public abstract void addUser(User paramUser);

	/**
	 * 
	 * @param paramInt 1,2,3分别表示查找一二三等奖
	 * @return 返回要查找的中奖的人员信息
	 */
	@Select({ "select * from t_user where grandprize=#{grandprize} order by createtime " })
	public abstract List<User> getUserDraw(@Param("grandprize") int paramInt);

	/**
	 * 
	 * @return 查找所用中奖的用户
	 */
	@Select({ "select * from t_user order by createtime , grandprize" })
	public abstract List<User> getAllUser();

	/**
	 * 得到奖品的集合
	 * @return 
	 */
	@Select({"select * from prize_pool"})
	public abstract List<PrizePool> getPrizePool();
	
	/**
	 * 更新奖品池
	 * @return 
	 */
	@Update({"update prize_pool set currentnum=#{num} where prizelevel=#{key}"})
	public abstract void updatePrizePool(@Param("key")int key,@Param("num")int num);

	/**
	 * 初始化奖品表
	 * @param i 
	 * @param j
	 */
	@Insert({"insert into prize_pool(prizelevel,currentnum) values(#{i},#{j})"})
	public abstract void initPrize(@Param("i")int i, @Param("j")int j);
}
