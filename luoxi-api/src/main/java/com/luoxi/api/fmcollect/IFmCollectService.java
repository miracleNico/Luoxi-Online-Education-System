package com.luoxi.api.fmcollect;

import com.luoxi.api.fmcollect.protocol.ReqAddFmCollect;
import com.luoxi.api.fmcollect.protocol.ReqFmCollectList;
import com.luoxi.api.fmcollect.protocol.ReqFmCollectList.RespFmCollectList;
import com.luoxi.base.RespPaging;

public interface IFmCollectService {

	/**
	 * @Description: 新增收藏
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	void addFmCollect(String userId,ReqAddFmCollect req) throws Exception;
	
	/**
	 * @Description: 资源收藏列表
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	RespPaging<RespFmCollectList> fmCollectList(String userId, ReqFmCollectList req) throws Exception;
	
}
