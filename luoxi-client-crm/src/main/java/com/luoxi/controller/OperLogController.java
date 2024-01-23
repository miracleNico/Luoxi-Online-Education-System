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
import com.luoxi.api.log.ISystemLogService;
import com.luoxi.api.log.protocol.ReqOperLogList;
import com.luoxi.api.log.protocol.ReqOperLogList.RespOperLogList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.LOG)
@Controller
@RequestMapping("log")
public class OperLogController extends BaseController{
	
	@DubboReference
	private ISystemLogService systemLogService;
	
	@RequestMapping("operLogPage")
	public String operLogPage() {
		return "page/log/operLog";
	}
	
	@ApiOperLog(action = ACTION.SELECT, desc = "操作日志")
	@ResponseBody
	@RequestMapping("operLogList")
	public Result<RespPaging<RespOperLogList>> operLogList(@RequestBody ReqOperLogList req) throws Exception {
		return ResultMessage.SUCCESS.result(systemLogService.operLogList(req));
	}
	
}
