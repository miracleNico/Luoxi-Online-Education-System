package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.storeApp.IStoreAppService;
import com.luoxi.api.storeApp.protocol.ReqSearchStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSearchStoreApp.RespSearchStoreApp;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"应用商店"})
@RestController
@RequestMapping("api/storeApp")
public class StoreAppController extends BaseController{
	
	@DubboReference
	private IStoreAppService storeAppService;
	
	@ApiOperation(value="搜索应用")
	@PostMapping("searchStoreApp")
	public Result<RespPaging<RespSearchStoreApp>> searchStoreApp(@Valid @RequestBody ReqSearchStoreApp req) throws Exception {
		req.setPackageName(getPackageName());
		return ResultMessage.SUCCESS.result(storeAppService.searchStoreApp(req));
	}
	
}
