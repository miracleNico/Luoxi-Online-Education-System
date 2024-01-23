package com.luoxi.api.storeApp;

import java.util.List;

import com.luoxi.api.storeApp.protocol.ReqRemoveStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSaveStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSearchStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSearchStoreApp.RespSearchStoreApp;
import com.luoxi.api.storeApp.protocol.ReqStoreAppInfo;
import com.luoxi.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;
import com.luoxi.api.storeApp.protocol.ReqStoreAppList;
import com.luoxi.api.storeApp.protocol.ReqStoreAppList.RespStoreAppList;
import com.luoxi.base.RespPaging;

public interface IStoreAppService {
	
	/**
	 * @Description: 应用商店搜索
	 * @Author wanbo
	 * @DateTime 2020/05/26
	 */
	RespPaging<RespSearchStoreApp> searchStoreApp(ReqSearchStoreApp req) throws Exception;

	/**
	 * @Description: 应用商店列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespStoreAppList> storeAppList(ReqStoreAppList req) throws Exception;
	
	/**
	 * @Description: 应用商店详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespStoreAppInfo storeAppInfo(ReqStoreAppInfo req) throws Exception;
	
	/**
	 * @Description: 保存应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveStoreApp(ReqSaveStoreApp req) throws Exception;
	
	/**
	 * @Description: 删除应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeStoreApp(List<ReqRemoveStoreApp> req) throws Exception;
	
	
	
	
}
