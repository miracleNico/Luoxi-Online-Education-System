/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.luoxi.api.userScore.protocol.ReqUserScoreList;
import com.luoxi.api.userScore.protocol.ReqUserScoreList.RespUserScoreList;
import com.luoxi.model.UserScore;

import io.lettuce.core.dynamic.annotation.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserScoreMapper extends Mapper<UserScore>{
	
	@Select("select * from t_user_score where user_id=#{userId}")
	UserScore getUserScore(@Param("userId")String userId);
	
	List<RespUserScoreList> userScoreList(ReqUserScoreList req);
	
	Map<String, Object> userScoreListSum(ReqUserScoreList req);
	
}
