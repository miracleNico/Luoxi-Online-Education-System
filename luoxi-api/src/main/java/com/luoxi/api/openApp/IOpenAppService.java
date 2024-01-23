package com.luoxi.api.openApp;

import java.util.List;
import java.util.Map;

import com.luoxi.api.openApp.protocol.ReqOpenAppList;
import com.luoxi.api.openApp.protocol.ReqOpenAppList.RespOpenAppList;
import com.luoxi.api.openApp.protocol.ReqOpenAppRemove;
import com.luoxi.api.openApp.protocol.ReqOpenAppSave;
import com.luoxi.base.RespPaging;
import com.luoxi.model.OpenApp;

public interface IOpenAppService {
	
	OpenApp getOpenAppByName(String channelId,String appName) throws Exception;

	OpenApp openApp(String appId) throws Exception;
	
	List<OpenApp> appSelected(Map<String, Object> map) throws Exception;;
	
	RespPaging<RespOpenAppList> appList(ReqOpenAppList req) throws Exception;
	
	OpenApp info(String appId) throws Exception;
	
	void saveApp(ReqOpenAppSave req) throws Exception;
	
	void removeApp(List<ReqOpenAppRemove> req) throws Exception;
}
