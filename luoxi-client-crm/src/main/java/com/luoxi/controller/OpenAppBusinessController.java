package com.luoxi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.luoxi.api.openAppBusiness.IOpenAppBusinessService;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessList;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessList.RespOpenAppBusinessList;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessRemove;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessSave;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.model.OpenAppBusiness;

@ApiModule(module = Module.OPEN_APP_BUSINESS)
@Controller
@RequestMapping("openAppBusiness")
public class OpenAppBusinessController extends BaseController{
	
	@DubboReference
	private IOpenAppBusinessService openAppBusinessService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/openAppBusiness/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appBusinessList")
	public Result<RespPaging<RespOpenAppBusinessList>> appBusinessList(@RequestBody ReqOpenAppBusinessList req) throws Exception {
		return ResultMessage.SUCCESS.result(openAppBusinessService.appBusinessList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String openAppBusinessId) throws Exception {
		OpenAppBusiness info = new OpenAppBusiness();
		if(StringUtils.isNotBlank(openAppBusinessId)) {
			info = openAppBusinessService.info(openAppBusinessId);
		}
		request.setAttribute("info", info);
		return "page/openAppBusiness/edit";
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveAppBusiness")
	public Result<?> saveAppBusiness(@RequestBody ReqOpenAppBusinessSave req) throws Exception {
		openAppBusinessService.saveAppBusiness(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeAppBusiness")
	public Result<?> removeAppBusiness(@Valid @RequestBody List<ReqOpenAppBusinessRemove> req) throws Exception {
		openAppBusinessService.removeAppBusiness(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("appBusinessSelected")
	public Result<List<OpenAppBusiness>> appBusinessSelected(@RequestBody OpenAppBusiness req) throws Exception {
		String appId = req.getAppId();
		if(StringUtils.isBlank(appId)) 
			return ResultMessage.SUCCESS.result();
		return ResultMessage.SUCCESS.result(openAppBusinessService.appBusinessSelected(appId));
	}
	
	
}
