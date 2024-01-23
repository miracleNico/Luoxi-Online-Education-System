package com.luoxi.controller;

import java.util.HashMap;
import java.util.List;

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
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(openAppService.appList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("channelAppSelected")
	public Result<List<OpenApp>> channelAppSelected(@RequestBody OpenApp req) throws Exception {
		String channelId = getChannelId();
		return ResultMessage.SUCCESS.result(openAppService.appSelected(MapUtil.builder(new HashMap<String,Object>()).put("channelId", channelId).build()));
	}
	
}
