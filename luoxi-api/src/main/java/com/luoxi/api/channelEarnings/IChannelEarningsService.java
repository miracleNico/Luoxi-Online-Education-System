package com.luoxi.api.channelEarnings;

import com.luoxi.api.channelEarnings.protocol.ReqChannelEarningsList;
import com.luoxi.api.channelEarnings.protocol.ReqChannelEarningsList.RespChannelEarningsList;
import com.luoxi.api.channelEarnings.protocol.ReqImportChannelEarnings;
import com.luoxi.base.RespPaging;

public interface IChannelEarningsService {

	/**
	 * @Description: 渠道收益列表
	 * @Author wanbo
	 * @DateTime 2020/06/05
	 */
	RespPaging<RespChannelEarningsList> channelEarningsList(ReqChannelEarningsList req) throws Exception;
	
	/**
	 * @Description: 渠道收益导入
	 * @Author wanbo
	 * @DateTime 2020/06/05
	 */
	void importChannelEarnings(ReqImportChannelEarnings req) throws Exception;
}
