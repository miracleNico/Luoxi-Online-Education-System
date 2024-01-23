package com.luoxi.api.channelEarningsDetail;

import com.luoxi.api.channelEarningsDetail.protocol.ReqChannelEarningsDetailList;
import com.luoxi.api.channelEarningsDetail.protocol.ReqChannelEarningsDetailList.RespChannelEarningsDetailList;
import com.luoxi.base.RespPaging;

public interface IChannelEarningsDetailService {

	RespPaging<RespChannelEarningsDetailList> channelEarningsDetailList(ReqChannelEarningsDetailList req) throws Exception;
	
}
