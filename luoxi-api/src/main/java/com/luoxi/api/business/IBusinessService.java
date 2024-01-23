package com.luoxi.api.business;

import java.util.List;

import com.luoxi.api.business.protocol.ReqBusinessInfo;
import com.luoxi.api.business.protocol.ReqBusinessInfo.RespBusinessInfo;
import com.luoxi.api.business.protocol.ReqBusinessList;
import com.luoxi.api.business.protocol.ReqRemoveBusiness;
import com.luoxi.api.business.protocol.ReqBusinessList.RespBusinessList;
import com.luoxi.api.business.protocol.ReqSaveBusiness;
import com.luoxi.api.business.protocol.ReqSaveBusiness.RespSaveBusiness;
import com.luoxi.base.RespPaging;

public interface IBusinessService {

	/**
	 * @Description: 内容商列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespBusinessList> businessList(ReqBusinessList req) throws Exception;
	
	/**
	 * @Description: 内容商详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespBusinessInfo businessInfo(ReqBusinessInfo req) throws Exception;
	
	/**
	 * @Description: 保存内容商
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSaveBusiness saveBusiness(ReqSaveBusiness req) throws Exception;
	
	/**
	 * @Description: 删除内容商
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeBusiness(List<ReqRemoveBusiness> req) throws Exception;
	
	
}
