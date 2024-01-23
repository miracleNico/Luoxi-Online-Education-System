package com.luoxi.api.snyc;

import java.util.List;

import com.luoxi.api.snyc.protocol.ReqSyncPull;
import com.luoxi.api.snyc.protocol.ReqSyncPush;

public interface ISyncService {
	
	/**
	 * @Description: 同步拉取
	 * @Author wanbo
	 * @DateTime 2019/12/19
	 */
	List<Object> syncPull(String userId, ReqSyncPull req) throws Exception;
	
	/**
	 * @Description: 同步推送
	 * @Author wanbo
	 * @DateTime 2019/12/19
	 */
	void syncPush(String userId, ReqSyncPush req) throws Exception;
	
}
