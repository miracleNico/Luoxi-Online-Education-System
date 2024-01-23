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
import com.luoxi.api.userScore.IUserScoreService;
import com.luoxi.api.userScore.protocol.ReqUserScoreList;
import com.luoxi.api.userScore.protocol.ReqUserScoreList.RespUserScoreList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import cn.hutool.core.util.StrUtil;

@ApiModule(module = Module.USER_SCORE)
@Controller
@RequestMapping("userScore")
public class UserScoreController extends BaseController{
	
	@DubboReference IUserScoreService userScoreService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/userScore/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("userScoreList")
	public Result<RespPaging<RespUserScoreList>> userScoreList(@RequestBody ReqUserScoreList req) throws Exception {
		req.setChannelId(getChannelId());
		RespPaging<RespUserScoreList> respPaging = userScoreService.userScoreList(req);
		for (RespUserScoreList resp : respPaging.getList()) {
			resp.setPhone(StrUtil.replace(resp.getPhone(), 3, 7, '*'));
		}
		return ResultMessage.SUCCESS.result(respPaging);
	}
	
	
}
