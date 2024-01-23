package com.luoxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.feedback.IFeedbackService;
import com.luoxi.api.feedback.protocol.ReqAddFeedback;
import com.luoxi.api.feedback.protocol.ReqGetFeedbackOption.RespGetFeedbackOption;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"问题反馈"})
@RestController
@RequestMapping("api/feedback")
public class FeedbackController extends BaseController{
	
	@DubboReference
	private IFeedbackService feedbackOptionService;
	
	
	@ApiOperation(value="添加问题反馈")
	@PostMapping("addFeedback")
	public Result<?> addFeedback(@Valid @RequestBody ReqAddFeedback requestParam) throws Exception {
		feedbackOptionService.addFeedback(getUserId(),getPackageName(),getVersionCode(),requestParam);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value=" 获取问题反馈选项")
	@PostMapping("getFeedbackOption")
	public Result<List<RespGetFeedbackOption>> getFeedbackOption() throws Exception {
		return ResultMessage.SUCCESS.result(feedbackOptionService.getFeedbackOption());
	}
	
}
