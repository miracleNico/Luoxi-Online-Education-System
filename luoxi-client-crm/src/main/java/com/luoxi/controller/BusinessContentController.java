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
import com.luoxi.api.businessContent.IBusinessContentService;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentInfo;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentInfo.RespBusinessContentInfo;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentList;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentList.RespBusinessContentList;
import com.luoxi.api.businessContent.protocol.ReqRemoveBusinessContent;
import com.luoxi.api.businessContent.protocol.ReqSaveBusinessContent;
import com.luoxi.api.businessContent.protocol.ReqUpdBusinessContentParent;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.BUSINESS_CONTENT)
@Controller
@RequestMapping("businessContent")
public class BusinessContentController extends BaseController{
	
	@DubboReference IBusinessContentService businessContentService;
	
	@RequestMapping(value = "listPage")
	public String listPage(String businessId,HttpServletRequest request) {
		request.setAttribute("businessId", businessId);
		return "page/businessContent/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("businessContentList")
	public Result<List<RespBusinessContentList>> businessContentList(@Valid @RequestBody ReqBusinessContentList req) throws Exception {
		return ResultMessage.SUCCESS.result(businessContentService.businessContentList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(String businessId,String businessContentId,String parentId,HttpServletRequest request) throws Exception {
		RespBusinessContentInfo info = new RespBusinessContentInfo();
		if(StringUtils.isNotBlank(businessContentId)) {
			info = businessContentService.businessContentInfo(new ReqBusinessContentInfo().setBusinessContentId(businessContentId));
			if(info==null) 
				info = new RespBusinessContentInfo();
		}
		if(StringUtils.isEmpty(info.getBusinessId())) {
			info.setBusinessId(businessId);
		}
		if(StringUtils.isEmpty(info.getParentId())) {
			info.setParentId(parentId);
		}
		request.setAttribute("info", info);
		return "page/businessContent/edit";
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveBusinessContent")
	public Result<?> saveBusinessContent(@RequestBody ReqSaveBusinessContent req) throws Exception {
		businessContentService.saveBusinessContent(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeBusinessContent")
	public Result<?> removeBusinessContent(@Valid @RequestBody List<ReqRemoveBusinessContent> req) throws Exception {
		businessContentService.removeBusinessContent(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("updBusinessContentParent")
	public Result<?> updBusinessContentParent(@Valid @RequestBody List<ReqUpdBusinessContentParent> req) throws Exception {
		businessContentService.updBusinessContentParent(req);
		return ResultMessage.SUCCESS.result();
	}
	
	/**
	
	@ApiResource(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String businessContentId) throws Exception {
		RespBusinessContentInfo info = new RespBusinessContentInfo();
		if(StringUtils.isNotBlank(businessContentId)) {
			info = businessContentService.businessContentInfo(new ReqBusinessContentInfo().setBusinessContentId(businessContentId));
		}
		request.setAttribute("info", info);
		return "page/businessContent/info";
	}
	
	@ResponseBody
	@RequestMapping("getFirstBusinessContentList")
	public Result<List<RespBusinessContentList>> getFirstBusinessContentList(@Valid @RequestBody ReqBusinessContentList req) throws Exception {
		return ResultMessage.SUCCESS.result(businessContentService.getFirstBusinessContentList(req.getBusinessId()));
	}
	
	@ResponseBody
	@RequestMapping("businessContentInfo")
	public Result<RespBusinessContentInfo> businessContentInfo(@RequestBody ReqBusinessContentInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(businessContentService.businessContentInfo(req));
	}
	
	*/
	
}
