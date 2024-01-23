package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.openAppBusiness.IOpenAppBusinessService;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessKey;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessKey.RespOpenAppBusinessKey;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"应用"})
@RestController
@RequestMapping("api/app")
public class OpenAppController extends BaseController{
	
	@DubboReference
	private IOpenAppBusinessService openAppThirdService;
	
	@ApiOperation(value="获取业务KEY")
	@PostMapping("getBusinessKey")
	public Result<RespOpenAppBusinessKey> getOpenAppBusinessKey(@Valid @RequestBody ReqOpenAppBusinessKey req) throws Exception {
		return ResultMessage.SUCCESS.result(openAppThirdService.getOpenAppBusinessKey(getAppId(),req.getBusinessCode()));
	}
	
}
