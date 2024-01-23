package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.businessApp.IBusinessAppService;
import com.luoxi.api.businessApp.protocol.ReqUpgradeThirdApp;
import com.luoxi.api.businessApp.vo.BusinessAppVo;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"内容商产品"})
@RestController
@RequestMapping("api/businessApp")
public class BusinessAppController extends BaseController{
	
	@DubboReference
	private IBusinessAppService businessAppService;
	
	@ApiOperation(value="第三方APK升级")
	@PostMapping("upgradeThirdApp")
	public Result<BusinessAppVo> upgradeThirdApp(@Valid @RequestBody ReqUpgradeThirdApp req) throws Exception {
		return ResultMessage.SUCCESS.result(businessAppService.upgradeThirdApp(req));
	}
	
}
