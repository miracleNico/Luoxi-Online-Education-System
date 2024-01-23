/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeList;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeList.RespUserScoreExchangeList;
import com.luoxi.model.UserScoreExchange;

import tk.mybatis.mapper.common.Mapper;

public interface UserScoreExchangeMapper extends Mapper<UserScoreExchange>{
	
	List<RespUserScoreExchangeList> userScoreExchangeList(ReqUserScoreExchangeList req);
	
}
