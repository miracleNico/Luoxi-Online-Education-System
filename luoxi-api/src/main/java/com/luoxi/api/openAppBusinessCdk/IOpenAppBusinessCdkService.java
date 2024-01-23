package com.luoxi.api.openAppBusinessCdk;

import java.util.List;

import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkImport;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkList;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkList.RespOpenAppBusinessCdkList;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkRemove;
import com.luoxi.base.RespPaging;
import com.luoxi.model.OpenAppBusinessCdk;

public interface IOpenAppBusinessCdkService {
	
	OpenAppBusinessCdk info(String openAppBusinessCdkId) throws Exception;
	
	/**
	 * @Description: 获取应用业务cdk
	 * @Author wanbo
	 * @DateTime 2020/10/12
	 */
	String getOpenAppBusinessCdk(String openDeviceId, String appId, String businessCode) throws Exception;
	
	RespPaging<RespOpenAppBusinessCdkList> appBusinessCdkList(ReqOpenAppBusinessCdkList req) throws Exception;
	
	void saveAppBusinessCdk(OpenAppBusinessCdk openAppBusinessCdk) throws Exception;
	
	void removeAppBusinessCdk(List<ReqOpenAppBusinessCdkRemove> req) throws Exception;
	
	void importAppBusinessCdk(ReqOpenAppBusinessCdkImport cdk) throws Exception;
}
