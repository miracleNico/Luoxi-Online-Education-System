package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.prize.IPrizeService;
import com.luoxi.api.prize.protocol.ReqPrizeStore;
import com.luoxi.api.prize.protocol.ReqPrizeStore.RespPrizeStore;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"奖品商城"})
@RestController
@RequestMapping("api/prize")
public class PrizeController extends BaseController{
	
	@DubboReference
	private IPrizeService prizeService;
	
	@ApiOperation(value="奖品列表")
	@PostMapping("prizeStore")
	public Result<RespPaging<RespPrizeStore>> prizeStore(@Valid @RequestBody ReqPrizeStore req) throws Exception {
		req.setPackageName(getPackageName());
		return ResultMessage.SUCCESS.result(prizeService.prizeStore(req));
	}
	
}
