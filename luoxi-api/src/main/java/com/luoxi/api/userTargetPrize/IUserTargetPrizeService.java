package com.luoxi.api.userTargetPrize;

import java.math.BigDecimal;

import com.luoxi.api.userTargetPrize.protocol.ReqSaveUserTargetPrize;
import com.luoxi.api.userTargetPrize.protocol.ReqUserTargetPrizeInfo.RespUserTargetPrizeInfo;

public interface IUserTargetPrizeService {

	/**
	 * @Description: 用户目标奖品信息
	 * @Author wanbo
	 * @DateTime 2020/06/29
	 */
	RespUserTargetPrizeInfo userTargetPrizeInfo(String userId) throws Exception;
	
	/**
	 * @Description: 保存用户目标奖品信息
	 * @Author wanbo
	 * @DateTime 2020/06/29
	 */
	void saveUserTargetProze(String userId, ReqSaveUserTargetPrize req) throws Exception;
	
	
	/**
	 * @Description: 用户目标奖品完成率
	 * @Author wanbo
	 * @DateTime 2020/08/31
	 */
	BigDecimal prizeCompletion(String userId) throws Exception;
	
}
