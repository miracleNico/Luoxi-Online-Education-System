package com.luoxi.controller;

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
import com.luoxi.api.app.IAppService;
import com.luoxi.api.app.protocol.ReqAppInfo;
import com.luoxi.api.app.protocol.ReqAppInfo.RespAppInfo;
import com.luoxi.api.app.protocol.ReqAppList;
import com.luoxi.api.app.protocol.ReqAppList.RespAppList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.APP)
@Controller
@RequestMapping("app")
public class AppController extends BaseController{
	
	@DubboReference IAppService appService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/app/list";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String appId) throws Exception {
		RespAppInfo info = new RespAppInfo();
		if(StringUtils.isNotBlank(appId)) {
			info = appService.appInfo(new ReqAppInfo().setAppId(appId));
		}
		request.setAttribute("info", info);
		return "page/app/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appList")
	public Result<RespPaging<RespAppList>> appList(@RequestBody ReqAppList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(appService.appList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("appSelected")
	public Result<RespPaging<RespAppList>> appSelected(@RequestBody ReqAppList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(appService.appList(req));
	}
	
	
}
