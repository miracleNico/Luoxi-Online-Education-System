package com.luoxi.api.userScoreExchange;

import com.luoxi.api.userScoreExchange.protocol.ReqSaveUserScoreExchange;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchange;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeInfo.RespUserScoreExchangeInfo;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeList;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeList.RespUserScoreExchangeList;
import com.luoxi.base.RespPaging;

public interface IUserScoreExchangeService {

	/**
	 * @Description: 用户积分兑换
	 * @Author wanbo
	 * @DateTime 2020/06/29
	 */
	void userScoreExchange(String userId, ReqUserScoreExchange req) throws Exception;
	
	/**
	 * @Description: 积分兑换信息
	 * @Author wanbo
	 * @DateTime 2020/09/01
	 */
	RespUserScoreExchangeInfo userScoreExchangeInfo(String userScoreExchangeId) throws Exception;
	
	/**
	 * @Description: 兑换列表
	 * @Author wanbo
	 * @DateTime 2020/08/31
	 */
	RespPaging<RespUserScoreExchangeList> userScoreExchangeList(ReqUserScoreExchangeList req) throws Exception;
	
	void saveUserScoreExchange(ReqSaveUserScoreExchange req) throws Exception;
}
