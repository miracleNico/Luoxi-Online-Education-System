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
import com.luoxi.api.sn.ISerialNumberService;
import com.luoxi.api.sn.protocol.ReqRemoveSerialNumber;
import com.luoxi.api.sn.protocol.ReqSaveSerialNumber;
import com.luoxi.api.sn.protocol.ReqSerialNumberInfo;
import com.luoxi.api.sn.protocol.ReqSerialNumberInfo.RespSerialNumberInfo;
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
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String snCode) throws Exception {
		RespSerialNumberInfo info = new RespSerialNumberInfo();
		if(StringUtils.isNotBlank(snCode)) {
			info = serialNumberService.serialNumberInfo(new ReqSerialNumberInfo().setSnCode(snCode));
		}
		request.setAttribute("info", info);
		return "page/sn/edit";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("serialNumberList")
	public Result<RespPaging<RespSerialNumberList>> serialNumberList(@RequestBody ReqSerialNumberList req) throws Exception {
		return ResultMessage.SUCCESS.result(serialNumberService.serialNumberList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveSerialNumber")
	public Result<?> saveSerialNumber(@Valid @RequestBody ReqSaveSerialNumber req) throws Exception {
		serialNumberService.saveSerialNumber(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeSerialNumber")
	public Result<?> removeSerialNumber(@Valid @RequestBody List<ReqRemoveSerialNumber> req) throws Exception {
		serialNumberService.removeSerialNumber(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
