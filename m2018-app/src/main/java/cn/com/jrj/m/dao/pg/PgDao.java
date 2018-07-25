package cn.com.jrj.m.dao.pg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import cn.com.jrj.m.domain.Fund;

/**
 * @Description: pg库接口
 * @author yuhai.li  
 * @date 2018年1月19日 下午2:32:04
 */
@Mapper
public interface PgDao {
	
	@Select(" select top 10 FUND_CODE from FND_GEN_INFO ")
	public List<Fund> top5List();
	
	public List<Fund> getTop10Fund();
	public List<Fund> getFundByCode(@Param("fundCode") String fundCode);
}
