package com.luoxi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.luoxi.api.feedback.protocol.ReqFeedbackInfo;
import com.luoxi.api.feedback.protocol.ReqFeedbackInfo.RespFeedbackInfo;
import com.luoxi.api.feedback.protocol.ReqFeedbackList;
import com.luoxi.api.feedback.protocol.ReqFeedbackList.RespFeedbackList;
import com.luoxi.api.feedback.protocol.ReqFeedbakProcessStatus;
import com.luoxi.api.feedback.protocol.ReqRemoveFeedback;
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
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String feedbackId) throws Exception {
		RespFeedbackInfo info = new RespFeedbackInfo();
		if(StringUtils.isNotBlank(feedbackId)) {
			info = feedbackService.feedbackInfo(new ReqFeedbackInfo().setFeedbackId(feedbackId));
		}
		request.setAttribute("info", info);
		return "page/feedback/edit";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("feedbackList")
	public Result<RespPaging<RespFeedbackList>> feedbackList(@RequestBody ReqFeedbackList req) throws Exception {
		return ResultMessage.SUCCESS.result(feedbackService.feedbackList(req));
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeFeedback")
	public Result<?> removeFeedback(@Valid @RequestBody List<ReqRemoveFeedback> req) throws Exception {
		feedbackService.removeFeedback(req);
		return ResultMessage.SUCCESS.result();
	}
	
	/**
	 * @Description: 问题反馈处理
	 * @Author wanbo
	 * @DateTime 2020/03/12
	 */
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("feedbakProcessStatus")
	public Result<?> feedbakProcessStatus(@Valid @RequestBody ReqFeedbakProcessStatus req) throws Exception {
		feedbackService.feedbakProcessStatus(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
