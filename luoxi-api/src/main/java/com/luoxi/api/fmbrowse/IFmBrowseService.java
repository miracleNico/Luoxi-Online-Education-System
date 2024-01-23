package com.luoxi.api.fmbrowse;

import com.luoxi.api.fmbrowse.protocol.ReqAddFmBrowse;
import com.luoxi.api.fmbrowse.protocol.ReqFmBrowseList;
import com.luoxi.api.fmbrowse.protocol.ReqFmBrowseList.RespFmBrowseList;
import com.luoxi.base.RespPaging;

public interface IFmBrowseService {

	/**
	 * @Description: 新增浏览记录
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	void addFmBrowse(String userId,ReqAddFmBrowse req) throws Exception;
	
	/**
	 * @Description: 资源浏览记录列表
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	RespPaging<RespFmBrowseList> fmBrowseList(String userId, ReqFmBrowseList req) throws Exception;
	
}
