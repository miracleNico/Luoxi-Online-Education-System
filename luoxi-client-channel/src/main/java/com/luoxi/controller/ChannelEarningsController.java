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
import com.luoxi.api.channelEarnings.IChannelEarningsService;
import com.luoxi.api.channelEarnings.protocol.ReqChannelEarningsList;
import com.luoxi.api.channelEarnings.protocol.ReqChannelEarningsList.RespChannelEarningsList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.APP_EARNINGS)
@Controller
@RequestMapping("channelEarnings")
public class ChannelEarningsController extends BaseController{
	
	@DubboReference IChannelEarningsService channelEarningsService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/channelEarnings/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("channelEarningsList")
	public Result<RespPaging<RespChannelEarningsList>> channelEarningsList(@RequestBody ReqChannelEarningsList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(channelEarningsService.channelEarningsList(req));
	}
	
	
}
