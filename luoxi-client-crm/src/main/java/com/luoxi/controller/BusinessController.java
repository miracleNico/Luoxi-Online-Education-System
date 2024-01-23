package com.luoxi.controller;

import java.util.List;

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
import com.luoxi.api.business.IBusinessService;
import com.luoxi.api.business.protocol.ReqBusinessInfo;
import com.luoxi.api.business.protocol.ReqBusinessInfo.RespBusinessInfo;
import com.luoxi.api.business.protocol.ReqBusinessList;
import com.luoxi.api.business.protocol.ReqBusinessList.RespBusinessList;
import com.luoxi.api.business.protocol.ReqRemoveBusiness;
import com.luoxi.api.business.protocol.ReqSaveBusiness;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.BUSINESS)
@Controller
@RequestMapping("business")
public class BusinessController{
	
	@DubboReference IBusinessService businessService;
	
	@RequestMapping("listPage")
	public String toListPage() {
		return "page/business/list";
	}
	
	@RequestMapping("editPage")
	public String toEditPage(HttpServletRequest request,String businessId) throws Exception {
		RespBusinessInfo info = new RespBusinessInfo();
		if(StringUtils.isNotBlank(businessId)) {
			info = businessService.businessInfo(new ReqBusinessInfo().setBusinessId(businessId));
		}
		request.setAttribute("info", info);
		return "page/business/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String businessId) throws Exception {
		RespBusinessInfo info = new RespBusinessInfo();
		if(StringUtils.isNotBlank(businessId)) {
			info = businessService.businessInfo(new ReqBusinessInfo().setBusinessId(businessId));
		}
		request.setAttribute("info", info);
		return "page/business/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("businessList")
	public Result<RespPaging<RespBusinessList>> deviceList(@RequestBody ReqBusinessList req) throws Exception {
		return ResultMessage.SUCCESS.result(businessService.businessList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveBusiness")
	public Result<?> saveBusiness(@RequestBody ReqSaveBusiness req) throws Exception {
		businessService.saveBusiness(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeBusiness")
	public Result<?> removeBusiness(@RequestBody List<ReqRemoveBusiness> req) throws Exception {
		businessService.removeBusiness(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
