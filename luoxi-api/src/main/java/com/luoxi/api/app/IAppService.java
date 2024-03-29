package com.luoxi.api.app;

import java.util.List;

import com.luoxi.api.app.protocol.ReqAppInfo;
import com.luoxi.api.app.protocol.ReqAppInfo.RespAppInfo;
import com.luoxi.api.app.protocol.ReqAppList;
import com.luoxi.api.app.protocol.ReqAppList.RespAppList;
import com.luoxi.api.app.protocol.ReqEnableSn;
import com.luoxi.api.app.protocol.ReqRemoveApp;
import com.luoxi.api.app.protocol.ReqSaveApp;
import com.luoxi.api.app.vo.AppKeyVo;
import com.luoxi.api.app.vo.AppVo;
import com.luoxi.api.upgradePlan.protocol.ReqUpgrade;
import com.luoxi.api.upgradePlan.protocol.ReqUpgrade.RespUpgrade;
import com.luoxi.base.RespPaging;

public interface IAppService {
	
	/**
	 * @Description: 清除产品升级缓存
	 * @Author wanbo
	 * @DateTime 2020/05/22
	 */
	void clearAppUpgradeCache(String packageName) throws Exception;
	
	AppVo getAppByPackageName(String packageName) throws Exception;
	
	AppVo getAppByAppId(String appId) throws Exception;

	/**
	 * @Description: 产品列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespAppList> appList(ReqAppList req) throws Exception;
	
	/**
	 * @Description: 产品详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespAppInfo appInfo(ReqAppInfo req) throws Exception;
	
	/**
	 * @Description: 保存产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveApp(ReqSaveApp req) throws Exception;
	
	/**
	 * @Description: 删除产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeApp(List<ReqRemoveApp> req) throws Exception;
	
	/**
	 * @Description: 启用激活码激活
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	void enableSn(ReqEnableSn req) throws Exception;
	
	
	/**
	 * @Description: 产品升级
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	RespUpgrade upgrade(ReqUpgrade requestParam,String versionCode,String packageName) throws Exception;
	
	/**
	 * @Description: 产品升级v2
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	RespUpgrade upgrade_v2(ReqUpgrade requestParam,String versionCode,String packageName) throws Exception;
	
	AppKeyVo appKey(String appId) throws Exception;
	
	List<AppVo> appVoList(String channelId) throws Exception;
	
	
}
