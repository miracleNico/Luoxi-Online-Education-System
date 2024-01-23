/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import org.apache.ibatis.annotations.Select;

import com.luoxi.api.fmreward.protocol.ReqAddReward;
import com.luoxi.model.FmReward;

import tk.mybatis.mapper.common.Mapper;

public interface FmRewardMapper extends Mapper<FmReward>{
	
	@Select("select if(count(1)=0,0,1) from t_fm_reward where user_id=#{userId} and resource_id=#{resourceId}")
	boolean existsReward(ReqAddReward req);
	
}
