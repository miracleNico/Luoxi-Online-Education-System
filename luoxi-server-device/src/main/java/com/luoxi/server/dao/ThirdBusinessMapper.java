
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.luoxi.api.thirdBusiness.protocol.ReqThirdBusinessList.RespThirdBusinessList;
import com.luoxi.model.ThirdBusiness;

import tk.mybatis.mapper.common.Mapper;

public interface ThirdBusinessMapper extends Mapper<ThirdBusiness>{
	
	List<RespThirdBusinessList> thirdBusinessList(Map<String, Object> map);
	
	@Select("select * from t_third_business where status=true and third_business_code=#{thirdBusinessCode}")
	ThirdBusiness getThirdBusinessByCode(@Param("thirdBusinessCode")String thirdBusinessCode);
	
}
