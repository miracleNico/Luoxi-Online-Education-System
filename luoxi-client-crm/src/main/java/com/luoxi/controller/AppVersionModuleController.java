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
import com.luoxi.api.versionModule.IAppVersionModuleService;
import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleInfo;
import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleInfo.RespAppVersionModuleInfo;
import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleList;
import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleList.RespAppVersionModuleList;
import com.luoxi.api.versionModule.protocol.ReqRemoveAppVersionModule;
import com.luoxi.api.versionModule.protocol.ReqSaveAppVersionModule;
import com.luoxi.api.versionModule.protocol.ReqUpdAppVersionModuleParent;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.BUSINESS_CONTENT)
@Controller
@RequestMapping("appVersionModule")
public class AppVersionModuleController extends BaseController{
	
	@DubboReference IAppVersionModuleService appVersionModuleService;
	
	@RequestMapping(value = "listPage")
	public String listPage(String versionId,HttpServletRequest request) {
		request.setAttribute("versionId", versionId);
		return "page/versionModule/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appVersionModuleList")
	public Result<List<RespAppVersionModuleList>> appVersionModuleList(@Valid @RequestBody ReqAppVersionModuleList req) throws Exception {
		return ResultMessage.SUCCESS.result(appVersionModuleService.appVersionModuleList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(String versionModuleId,String versionId,String parentId,HttpServletRequest request) throws Exception {
		RespAppVersionModuleInfo info = new RespAppVersionModuleInfo();
		if(StringUtils.isNotBlank(versionModuleId)) {
			info = appVersionModuleService.appVersionModuleInfo(new ReqAppVersionModuleInfo().setVersionModuleId(versionModuleId));
			if(info==null) 
				info = new RespAppVersionModuleInfo();
		}
		if(StringUtils.isEmpty(info.getVersionId())) {
			info.setVersionId(versionId);
		}
		if(StringUtils.isEmpty(info.getParentId())) {
			info.setParentId(parentId);
		}
		request.setAttribute("info", info);
		return "page/versionModule/edit";
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveAppVersionModule")
	public Result<?> saveAppVersionModule(@RequestBody ReqSaveAppVersionModule req) throws Exception {
		appVersionModuleService.saveAppVersionModule(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeAppVersionModule")
	public Result<?> removeAppVersionModule(@Valid @RequestBody List<ReqRemoveAppVersionModule> req) throws Exception {
		appVersionModuleService.removeAppVersionModule(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("updAppVersionModuleParent")
	public Result<?> updAppVersionModuleParent(@Valid @RequestBody List<ReqUpdAppVersionModuleParent> req) throws Exception {
		appVersionModuleService.updAppVersionModuleParent(req);
		return ResultMessage.SUCCESS.result();
	}
	
}
