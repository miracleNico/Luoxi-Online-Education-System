package com.luoxi.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.api.feedback.IFeedbackService;
import com.luoxi.api.feedback.protocol.ReqFeedbackList;
import com.luoxi.api.feedback.protocol.ReqFeedbackList.RespFeedbackList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.FEEDBACK)
@Controller
@RequestMapping("feedback")
public class FeedbackController extends BaseController{
	
	@DubboReference IFeedbackService feedbackService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/feedback/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("feedbackList")
	public Result<RespPaging<RespFeedbackList>> feedbackList(@RequestBody ReqFeedbackList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(feedbackService.feedbackList(req));
	}
	
}
