package com.luoxi.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.api.openDevice.IOpenDeviceService;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceList;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.OPEN_DEVICE)
@Controller
@RequestMapping("openDevice")
public class OpenDeviceController extends BaseController{
	
	@DubboReference
	private IOpenDeviceService openDeviceService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/openDevice/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("deviceList")
	public Result<RespPaging<RespOpenDeviceList>> deviceList(@RequestBody ReqOpenDeviceList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(openDeviceService.deviceList(req));
	}
	
}
