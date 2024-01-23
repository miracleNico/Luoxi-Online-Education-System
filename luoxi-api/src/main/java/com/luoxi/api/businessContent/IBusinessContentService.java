package com.luoxi.api.businessContent;

import java.util.List;

import com.luoxi.api.businessContent.protocol.ReqBusinessContentInfo;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentInfo.RespBusinessContentInfo;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentList;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentList.RespBusinessContentList;
import com.luoxi.api.businessContent.protocol.ReqRemoveBusinessContent;
import com.luoxi.api.businessContent.protocol.ReqSaveBusinessContent;
import com.luoxi.api.businessContent.protocol.ReqSaveBusinessContent.RespSaveBusinessContent;
import com.luoxi.api.businessContent.protocol.ReqUpdBusinessContentParent;

public interface IBusinessContentService {
	
	/**
	 * @Description: 内容商内容列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespBusinessContentList> businessContentList(ReqBusinessContentList req) throws Exception;
	
	/**
	 * @Description: 内容商内容详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespBusinessContentInfo businessContentInfo(ReqBusinessContentInfo req) throws Exception;
	
	/**
	 * @Description: 保存内容商内容
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSaveBusinessContent saveBusinessContent(ReqSaveBusinessContent req) throws Exception;
	
	/**
	 * @Description: 删除内容商内容
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeBusinessContent(List<ReqRemoveBusinessContent> req) throws Exception;
	
	/**
	 * @Description: 修改父级节点
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void updBusinessContentParent(List<ReqUpdBusinessContentParent> req) throws Exception;
}
