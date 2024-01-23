package com.luoxi.api.businessApp;

import java.util.List;
import java.util.Map;

import com.luoxi.api.businessApp.protocol.ReqBusinessAppInfo;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppInfo.RespBusinessAppInfo;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppList;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppList.RespBusinessAppList;
import com.luoxi.api.businessApp.protocol.ReqUpgradeThirdApp;
import com.luoxi.api.businessApp.protocol.ReqRemoveBusinessApp;
import com.luoxi.api.businessApp.protocol.ReqSaveBusinessApp;
import com.luoxi.api.businessApp.vo.BusinessAppVo;
import com.luoxi.base.RespPaging;

public interface IBusinessAppService {
	
	/**
	 * @Description: 第三方产品升级
	 * @Author wanbo
	 * @DateTime 2020/04/27
	 */
	BusinessAppVo upgradeThirdApp(ReqUpgradeThirdApp req) throws Exception;
	
	/**
	 * @Description: 内容商应用树
	 * @Author wanbo
	 * @DateTime 2020/03/06
	 */
	List<Map<String, Object>> businessAppTree() throws Exception;

	/**
	 * @Description: 内容商产品列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespBusinessAppList> businessAppList(ReqBusinessAppList req) throws Exception;
	
	/**
	 * @Description: 内容商产品详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespBusinessAppInfo businessAppInfo(ReqBusinessAppInfo req) throws Exception;
	
	/**
	 * @Description: 保存内容商产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveBusinessApp(ReqSaveBusinessApp req) throws Exception;
	
	/**
	 * @Description: 删除内容商产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeBusinessApp(List<ReqRemoveBusinessApp> req) throws Exception;
	
	
}
