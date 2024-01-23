package com.luoxi.api.upgradePlan;

import java.util.List;

import com.luoxi.api.upgradePlan.protocol.ReqRemoveUpgradePlan;
import com.luoxi.api.upgradePlan.protocol.ReqSaveUpgradePlan;
import com.luoxi.api.upgradePlan.protocol.ReqSendUpgradePlan;
import com.luoxi.api.upgradePlan.protocol.ReqUpgradePlanInfo;
import com.luoxi.api.upgradePlan.protocol.ReqUpgradePlanInfo.RespUpgradePlanInfo;
import com.luoxi.api.upgradePlan.protocol.ReqUpgradePlanList;
import com.luoxi.api.upgradePlan.protocol.ReqUpgradePlanList.RespUpgradePlanList;
import com.luoxi.base.RespPaging;

public interface IUpgradePlanService {

	/**
	 * @Description: 升级计划列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespUpgradePlanList> upgradePlanList(ReqUpgradePlanList req) throws Exception;
	
	/**
	 * @Description: 升级计划详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespUpgradePlanInfo upgradePlanInfo(ReqUpgradePlanInfo req) throws Exception;
	
	/**
	 * @Description: 保存升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveUpgradePlan(ReqSaveUpgradePlan req) throws Exception;
	
	/**
	 * @Description: 删除升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeUpgradePlan(List<ReqRemoveUpgradePlan> req) throws Exception;
	
	/**
	 * @Description: 发布升级计划
	 * @Author wanbo
	 * @DateTime 2019/11/29
	 */
	void sendUpgradePlan(ReqSendUpgradePlan req) throws Exception;
	
	
	
	
}
