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
import com.luoxi.api.sn.ISerialNumberService;
import com.luoxi.api.sn.protocol.ReqSerialNumberList;
import com.luoxi.api.sn.protocol.ReqSerialNumberList.RespSerialNumberList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.SN)
@Controller
@RequestMapping("sn")
public class SerialNumberController extends BaseController{
	
	@DubboReference ISerialNumberService serialNumberService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/sn/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("serialNumberList")
	public Result<RespPaging<RespSerialNumberList>> serialNumberList(@RequestBody ReqSerialNumberList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(serialNumberService.serialNumberList(req));
	}
	
	
}
