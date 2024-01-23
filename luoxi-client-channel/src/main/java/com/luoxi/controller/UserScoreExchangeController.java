package com.luoxi.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.luoxi.api.userScoreExchange.IUserScoreExchangeService;
import com.luoxi.api.userScoreExchange.protocol.ReqSaveUserScoreExchange;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeInfo.RespUserScoreExchangeInfo;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeList;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeList.RespUserScoreExchangeList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.USER_SCORE_EXCHANGE)
@Controller
@RequestMapping("userScoreExchange")
public class UserScoreExchangeController extends BaseController{
	
	@DubboReference IUserScoreExchangeService userScoreExchangeService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/userScoreExchange/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("userScoreExchangeList")
	public Result<RespPaging<RespUserScoreExchangeList>> userScoreExchangeList(@RequestBody ReqUserScoreExchangeList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(userScoreExchangeService.userScoreExchangeList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String userScoreExchangeId) throws Exception {
		RespUserScoreExchangeInfo info = new RespUserScoreExchangeInfo();
		if(StringUtils.isNotBlank(userScoreExchangeId)) {
			info = userScoreExchangeService.userScoreExchangeInfo(userScoreExchangeId);
		}
		request.setAttribute("info", info);
		return "page/userScoreExchange/edit";
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveUserScoreExchange")
	public Result<?> saveUserScoreExchange(@RequestBody ReqSaveUserScoreExchange req) throws Exception {
		userScoreExchangeService.saveUserScoreExchange(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
