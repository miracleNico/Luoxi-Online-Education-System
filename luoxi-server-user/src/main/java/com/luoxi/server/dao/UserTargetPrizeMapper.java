/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.luoxi.api.userTargetPrize.protocol.ReqUserTargetPrizeInfo.RespUserTargetPrizeInfo;
import com.luoxi.model.UserTargetPrize;

import tk.mybatis.mapper.common.Mapper;

public interface UserTargetPrizeMapper extends Mapper<UserTargetPrize>{
	
	RespUserTargetPrizeInfo userTargetPrizesInfo(@Param("userId")String userId);
	
	@Select("select * from t_user_target_prize where user_id=#{userId} limit 1")
	UserTargetPrize userTargetPrize(String userId);
	
	BigDecimal prizeCompletion(@Param("userId")String userId);
}
