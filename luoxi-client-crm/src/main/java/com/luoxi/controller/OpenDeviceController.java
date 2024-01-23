package com.luoxi.controller;

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
import com.luoxi.api.openDevice.IOpenDeviceService;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceEnable;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceList;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceRemove;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.model.OpenDevice;
import com.luoxi.utils.ValidList;

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
		return ResultMessage.SUCCESS.result(openDeviceService.deviceList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String openDeviceId) throws Exception {
		OpenDevice info = new OpenDevice();
		if(StringUtils.isNotBlank(openDeviceId)) {
			info = openDeviceService.info(openDeviceId);
		}
		request.setAttribute("info", info);
		return "page/openDevice/edit";
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeDevice")
	public Result<?> removeDevice(@Valid @RequestBody ValidList<ReqOpenDeviceRemove> req) throws Exception {
		openDeviceService.removeDevice(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("enable")
	public Result<?> enable(@Valid @RequestBody ReqOpenDeviceEnable req) throws Exception {
		openDeviceService.enable(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
