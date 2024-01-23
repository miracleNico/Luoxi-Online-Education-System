package com.luoxi.api.log;

import com.luoxi.api.log.protocol.ReqOperLogList;
import com.luoxi.api.log.protocol.ReqOperLogList.RespOperLogList;
import com.luoxi.api.log.protocol.ReqSaveOperLog;
import com.luoxi.base.RespPaging;

public interface ISystemLogService {
	
	void saveOperLog(ReqSaveOperLog req) throws Exception;
	
	/**
	 * @Description: 操作日志列表
	 * @Author wanbo
	 * @DateTime 2019/12/09
	 */
	RespPaging<RespOperLogList> operLogList(ReqOperLogList req) throws Exception;
	
}
