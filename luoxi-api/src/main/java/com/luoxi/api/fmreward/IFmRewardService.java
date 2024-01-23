package com.luoxi.api.fmreward;

import com.luoxi.api.fmreward.protocol.ReqAddReward;

public interface IFmRewardService {

	/**
	 * @Description: 新增奖励
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	void addReward(ReqAddReward req)throws Exception;
	
}
