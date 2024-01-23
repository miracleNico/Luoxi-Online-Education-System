package com.luoxi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.businessApp.IBusinessAppService;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppInfo;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppInfo.RespBusinessAppInfo;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppList;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppList.RespBusinessAppList;
import com.luoxi.api.businessApp.protocol.ReqRemoveBusinessApp;
import com.luoxi.api.businessApp.protocol.ReqSaveBusinessApp;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.BUSINESS_APP)
@Controller
@RequestMapping("businessApp")
public class BusinessAppController{
	
	@DubboReference IBusinessAppService businessAppService;
	
	@RequestMapping("listPage")
	public String toListPage(String businessId,HttpServletRequest request) {
		request.setAttribute("businessId", businessId);
		return "page/businessApp/list";
	}
	
	@RequestMapping("editPage")
	public String toEditPage(HttpServletRequest request,String businessAppId) throws Exception {
		RespBusinessAppInfo info = new RespBusinessAppInfo();
		if(StringUtils.isNotBlank(businessAppId)) {
			info = businessAppService.businessAppInfo(new ReqBusinessAppInfo().setBusinessAppId(businessAppId));
		}
		request.setAttribute("info", info);
		return "page/businessApp/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String businessAppId) throws Exception {
		RespBusinessAppInfo info = new RespBusinessAppInfo();
		if(StringUtils.isNotBlank(businessAppId)) {
			info = businessAppService.businessAppInfo(new ReqBusinessAppInfo().setBusinessAppId(businessAppId));
		}
		request.setAttribute("info", info);
		return "page/businessApp/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("businessAppList")
	public Result<RespPaging<RespBusinessAppList>> deviceList(@RequestBody ReqBusinessAppList req) throws Exception {
		return ResultMessage.SUCCESS.result(businessAppService.businessAppList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("businessAppTree")
	public Result<List<Map<String, Object>>> businessAppTree() throws Exception {
		return ResultMessage.SUCCESS.result(businessAppService.businessAppTree());
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveBusinessApp")
	public Result<?> saveBusinessApp(@RequestBody ReqSaveBusinessApp req) throws Exception {
		businessAppService.saveBusinessApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeBusinessApp")
	public Result<?> removeBusinessApp(@RequestBody List<ReqRemoveBusinessApp> req) throws Exception {
		businessAppService.removeBusinessApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
