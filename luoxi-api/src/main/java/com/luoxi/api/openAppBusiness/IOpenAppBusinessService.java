package com.luoxi.api.openAppBusiness;

import java.util.List;

import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessKey.RespOpenAppBusinessKey;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessList;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessList.RespOpenAppBusinessList;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessRemove;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessSave;
import com.luoxi.base.RespPaging;
import com.luoxi.model.OpenAppBusiness;

public interface IOpenAppBusinessService {
	
	OpenAppBusiness getOpenAppBusinessByName(String appId,String businessName) throws Exception;
	
	OpenAppBusiness getOpenAppBusiness(String appId, String businessCode) throws Exception;
	
	RespOpenAppBusinessKey getOpenAppBusinessKey(String appId,String businessCode) throws Exception;
	
	void checkOpenAppBusiness(String appId, String businessCode) throws Exception;
	
	RespPaging<RespOpenAppBusinessList> appBusinessList(ReqOpenAppBusinessList req) throws Exception;
	
	OpenAppBusiness info(String appBusinessId) throws Exception;
	
	void saveAppBusiness(ReqOpenAppBusinessSave req) throws Exception;
	
	void removeAppBusiness(List<ReqOpenAppBusinessRemove> req) throws Exception;
	
	List<OpenAppBusiness> appBusinessSelected(String appId) throws Exception;
}
