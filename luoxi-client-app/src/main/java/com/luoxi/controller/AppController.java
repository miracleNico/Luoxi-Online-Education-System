package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.app.IAppService;
import com.luoxi.api.device.IDeviceService;
import com.luoxi.api.upgradePlan.protocol.ReqUpgrade;
import com.luoxi.api.upgradePlan.protocol.ReqUpgrade.RespUpgrade;
import com.luoxi.api.versionModule.IAppVersionModuleService;
import com.luoxi.api.versionModule.protocol.ReqViewAppVersionModule.RespViewAppVersionModule;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"产品"})
@RestController
@RequestMapping("api/app")
public class AppController extends BaseController{
	
	@DubboReference
	private IAppService appService;
	@DubboReference
	private IDeviceService deviceService;
	@DubboReference
	private IAppVersionModuleService appVersionModuleService;
	
	@ApiOperation(value="产品升级")
	@PostMapping("upgrade")
	public Result<RespUpgrade> upgrade(@Valid @RequestBody ReqUpgrade req) throws Exception {
		//luoxi-temp完善旧数据缺失导致认证失败问题
		if(StrUtil.isNotBlank(req.getWirelessMac()) && StrUtil.isNotBlank(req.getSerialNumber())) {
			deviceService.repairDeviceSerialNumber(req.getWirelessMac(), req.getSerialNumber());
		}
		if(StrUtil.isNotBlank(req.getDeviceId())) {
			return ResultMessage.SUCCESS.result(appService.upgrade(req, getVersionCode(), getPackageName()));			
		}else {
			return ResultMessage.SUCCESS.result(appService.upgrade_v2(req, getVersionCode(), getPackageName()));
		}
	}
	
	@ApiOperation(value="展示产品模块")
	@PostMapping("viewAppVersionModule")
	public Result<RespViewAppVersionModule> viewAppVersionModule() throws Exception {
		return ResultMessage.SUCCESS.result(appVersionModuleService.viewAppVersionModule(getPackageName(),getVersionCode()));
	}
	
}
