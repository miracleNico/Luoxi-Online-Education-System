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
import com.luoxi.api.userScoreDetail.IUserScoreDetailService;
import com.luoxi.api.userScoreDetail.protocol.ReqUserScoreDetailList;
import com.luoxi.api.userScoreDetail.protocol.ReqUserScoreDetailList.RespUserScoreDetailList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.USER_SCORE_DETAIL)
@Controller
@RequestMapping("userScoreDetail")
public class UserScoreDetailController extends BaseController{
	
	@DubboReference IUserScoreDetailService userScoreDetailService;
	
	@RequestMapping("listPage")
	public String listPage(String userScoreId,HttpServletRequest request) {
		request.setAttribute("userScoreId", userScoreId);
		return "page/userScoreDetail/list";
	}
	
	/**
	 * @Description: 积分明细列表
	 * @Author wanbo
	 * @DateTime 2020/07/08
	 */
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("userScoreDetailList")
	public Result<RespPaging<RespUserScoreDetailList>> userScoreDetailList(@RequestBody ReqUserScoreDetailList req) throws Exception {
		return ResultMessage.SUCCESS.result(userScoreDetailService.userScoreDetailList(req));
	}
	
	
}
