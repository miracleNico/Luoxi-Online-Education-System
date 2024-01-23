package com.luoxi.api.statistical;

import com.luoxi.api.statistical.protocol.ReqDayActiveUser.RespDayActiveUser;
import com.luoxi.api.statistical.protocol.ReqStatistical.RespStatistical;

public interface IStatisticalService {
	
	RespStatistical statistical(String channelId) throws Exception;
	
	/**
	 * @Description: 统计日活用户
	 * @Author wanbo
	 * @DateTime 2020/05/08
	 */
	RespDayActiveUser dayActiveUser(String channelId) throws Exception;
	
}
