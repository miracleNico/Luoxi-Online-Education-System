package com.luoxi.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.userTargetPrize.IUserTargetPrizeService;
import com.luoxi.api.userTargetPrize.protocol.ReqSaveUserTargetPrize;
import com.luoxi.api.userTargetPrize.protocol.ReqUserTargetPrizeInfo.RespUserTargetPrizeInfo;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"用户目标奖品"})
@RestController
@RequestMapping("api/userTargetPrize")
public class UserTargetPrizeController extends BaseController{
	
	@DubboReference
	private IUserTargetPrizeService userTargetPrizeService;
	
	@ApiOperation(value="用户目标奖品信息")
	@PostMapping("userTargetPrizeInfo")
	public Result<RespUserTargetPrizeInfo> userTargetPrizeInfo() throws Exception {
		return ResultMessage.SUCCESS.result(userTargetPrizeService.userTargetPrizeInfo(getUserId()));
	}
	
	@ApiOperation(value="保存用户目标奖品")
	@PostMapping("saveUserTargetProze")
	public Result<?> saveUserTargetProze(@Valid @RequestBody ReqSaveUserTargetPrize req) throws Exception {
		userTargetPrizeService.saveUserTargetProze(getUserId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value="目标奖品完成进度(%)")
	@PostMapping("prizeCompletion")
	public Result<BigDecimal> prizeCompletion() throws Exception {
		return ResultMessage.SUCCESS.result(userTargetPrizeService.prizeCompletion(getUserId()));
	}
	
}
