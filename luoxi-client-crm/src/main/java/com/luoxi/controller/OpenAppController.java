package com.luoxi.controller;

import java.util.HashMap;
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
import com.luoxi.api.openApp.IOpenAppService;
import com.luoxi.api.openApp.protocol.ReqOpenAppList;
import com.luoxi.api.openApp.protocol.ReqOpenAppList.RespOpenAppList;
import com.luoxi.api.openApp.protocol.ReqOpenAppRemove;
import com.luoxi.api.openApp.protocol.ReqOpenAppSave;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.model.OpenApp;

import cn.hutool.core.map.MapUtil;

@ApiModule(module = Module.OPEN_APP)
@Controller
@RequestMapping("openApp")
public class OpenAppController extends BaseController{
	
	@DubboReference
	private IOpenAppService openAppService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/openApp/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appList")
	public Result<RespPaging<RespOpenAppList>> appList(@RequestBody ReqOpenAppList req) throws Exception {
		return ResultMessage.SUCCESS.result(openAppService.appList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String appId) throws Exception {
		OpenApp info = new OpenApp();
		if(StringUtils.isNotBlank(appId)) {
			info = openAppService.info(appId);
		}
		request.setAttribute("info", info);
		return "page/openApp/edit";
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveApp")
	public Result<?> saveApp(@RequestBody ReqOpenAppSave req) throws Exception {
		openAppService.saveApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("appSelected")
	public Result<List<OpenApp>> appSelected() throws Exception {
		return ResultMessage.SUCCESS.result(openAppService.appSelected(null));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("channelAppSelected")
	public Result<List<OpenApp>> channelAppSelected(@RequestBody OpenApp req) throws Exception {
		String channelId = req.getChannelId();
		if(StringUtils.isBlank(channelId)) 
			return ResultMessage.SUCCESS.result();
		return ResultMessage.SUCCESS.result(openAppService.appSelected(MapUtil.builder(new HashMap<String,Object>()).put("channelId", channelId).build()));
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeApp")
	public Result<?> removeApp(@Valid @RequestBody List<ReqOpenAppRemove> req) throws Exception {
		openAppService.removeApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
