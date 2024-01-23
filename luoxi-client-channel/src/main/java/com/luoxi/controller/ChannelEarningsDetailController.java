package com.luoxi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.api.channelEarningsDetail.IChannelEarningsDetailService;
import com.luoxi.api.channelEarningsDetail.protocol.ReqChannelEarningsDetailList;
import com.luoxi.api.channelEarningsDetail.protocol.ReqChannelEarningsDetailList.RespChannelEarningsDetailList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.APP_EARNINGS_DETAIL)
@Controller
@RequestMapping("channelEarningsDetail")
public class ChannelEarningsDetailController extends BaseController{
	
	@DubboReference 
	private IChannelEarningsDetailService channelEarningsDetailService;
	
	@RequestMapping("listPage")
	public String listPage(String channelEarningsId,HttpServletRequest request) {
		request.setAttribute("channelEarningsId", channelEarningsId);
		return "page/channelEarningsDetail/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("channelEarningsDetailList")
	public Result<RespPaging<RespChannelEarningsDetailList>> channelEarningsDetailList(@RequestBody ReqChannelEarningsDetailList req) throws Exception {
		return ResultMessage.SUCCESS.result(channelEarningsDetailService.channelEarningsDetailList(req));
	}
	
	
}
