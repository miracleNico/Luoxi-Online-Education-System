package com.luoxi.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.statistical.IStatisticalService;
import com.luoxi.api.statistical.protocol.ReqDayActiveUser.RespDayActiveUser;
import com.luoxi.api.statistical.protocol.ReqStatistical.RespStatistical;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

/**
 * @Description: 统计
 * @Author wanbo
 * @DateTime 2019/12/24
 */
@ApiModule(module = Module.STATISTICAL)
@Controller
@RequestMapping("statistical")
public class StatisticalController extends BaseController{
	
	@DubboReference 
	private IStatisticalService statisticalService;
	
	@ApiPermission(AUTH.OPEN)
	@RequestMapping("statisticalPage")
	public String statisticalPage() {
		return "page/statistical/statistical";
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("dayActiveUser")
	public Result<RespDayActiveUser> dayActiveUser() throws Exception {
		return ResultMessage.SUCCESS.result(statisticalService.dayActiveUser(getChannelId()));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("statistical")
	public Result<RespStatistical> statistical() throws Exception {
		return ResultMessage.SUCCESS.result(statisticalService.statistical(getChannelId()));
	}
	
	
}
