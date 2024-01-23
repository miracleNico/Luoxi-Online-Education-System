package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.userScore.IUserScoreService;
import com.luoxi.api.userScore.protocol.ReqLearningChangeUserScore;
import com.luoxi.api.userScoreDetail.IUserScoreDetailService;
import com.luoxi.api.userScoreDetail.protocol.ReqAppUserScoreDetail;
import com.luoxi.api.userScoreDetail.protocol.ReqAppUserScoreDetail.RespAppUserScoreDetail;
import com.luoxi.api.userScoreExchange.IUserScoreExchangeService;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchange;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"用户积分"})
@RestController
@RequestMapping("api/userScore")
public class UserScoreController extends BaseController{
	
	@DubboReference
	private IUserScoreService userScoreService;
	@DubboReference
	private IUserScoreExchangeService userScoreExchangeService;
	@DubboReference
	private IUserScoreDetailService userScoreDetailService;
	
	@ApiOperation(value="学习触发用户积分")
	@PostMapping("learningChangeUserScore")
	public Result<?> learningChangeUserScore(@Valid @RequestBody ReqLearningChangeUserScore req) throws Exception {
		userScoreService.learningChangeUserScore(getUserId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value="兑换")
	@PostMapping("userScoreExchange")
	public Result<?> userScoreExchange(@Valid @RequestBody ReqUserScoreExchange req) throws Exception {
		userScoreExchangeService.userScoreExchange(getUserId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value="积分明细")
	@PostMapping("appUserScoreDetail")
	public Result<RespPaging<RespAppUserScoreDetail>> appUserScoreDetail(@Valid @RequestBody ReqAppUserScoreDetail req) throws Exception {
		req.setUserId(getUserId());
		return ResultMessage.SUCCESS.result(userScoreDetailService.appUserScoreDetail(req));
	}
	
}
