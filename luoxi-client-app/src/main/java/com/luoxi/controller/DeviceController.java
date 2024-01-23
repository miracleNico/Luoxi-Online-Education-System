package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.device.IDeviceService;
import com.luoxi.api.device.protocol.ReqActivate;
import com.luoxi.api.device.protocol.ReqActivate.RespActivate;
import com.luoxi.api.device.protocol.ReqDeviceAuth;
import com.luoxi.api.device.protocol.ReqDeviceAuth.RespDeviceAuth;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"设备"})
@RestController
@RequestMapping("api/device")
public class DeviceController extends BaseController{
	
	@DubboReference
	private IDeviceService deviceSerivce;
	
	@ApiOperation(value="设备认证")
	@PostMapping("deviceAuth")
	public Result<RespDeviceAuth> deviceAuth(@Valid @RequestBody ReqDeviceAuth requestParam) throws Exception {
		requestParam.setPackageName(getPackageName());
		return ResultMessage.SUCCESS.result(deviceSerivce.deviceAuth(requestParam));
	}
	
	
	@ApiOperation(value="SN码激活")
	@PostMapping("activate")
	public Result<RespActivate> activate(@Valid @RequestBody ReqActivate requestParam) throws Exception {
		requestParam.setPackageName(getPackageName());
		return ResultMessage.SUCCESS.result(deviceSerivce.activate(requestParam));
	}
	
}
