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
import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.version.IAppVersionService;
import com.luoxi.api.version.protocol.ReqAppVersionInfo;
import com.luoxi.api.version.protocol.ReqAppVersionInfo.RespAppVersionInfo;
import com.luoxi.api.version.protocol.ReqAppVersionList;
import com.luoxi.api.version.protocol.ReqAppVersionList.RespAppVersionList;
import com.luoxi.api.version.protocol.ReqRemoveAppVersion;
import com.luoxi.api.version.protocol.ReqSaveAppVersion;
import com.luoxi.api.version.protocol.ReqValidAppVersionList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.VERSION)
@Controller
@RequestMapping("version")
public class AppVersionController extends BaseController{
	
	@DubboReference IAppVersionService appVersionService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/version/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String versionId) throws Exception {
		RespAppVersionList info = new RespAppVersionList();
		if(StringUtils.isNotBlank(versionId)) {
			info = appVersionService.appVersionInfo(new ReqAppVersionInfo().setVersionId(versionId));
		}
		request.setAttribute("info", info);
		return "page/version/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String versionId) throws Exception {
		RespAppVersionList info = new RespAppVersionList();
		if(StringUtils.isNotBlank(versionId)) {
			info = appVersionService.appVersionInfo(new ReqAppVersionInfo().setVersionId(versionId));
		}
		request.setAttribute("info", info);
		return "page/version/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("versionList")
	public Result<RespPaging<RespAppVersionList>> versionList(@Valid @RequestBody ReqAppVersionList req) throws Exception {
		return ResultMessage.SUCCESS.result(appVersionService.appVersionList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("appVersionSelected")
	public Result<RespPaging<RespAppVersionList>> appVersionSelected(@Valid @RequestBody ReqValidAppVersionList req) throws Exception {
		if(StringUtils.isBlank(req.getApp()))
			return ResultMessage.SUCCESS.result(new RespPaging<RespAppVersionList>());
		return ResultMessage.SUCCESS.result(appVersionService.appVersionList(req));
	}
	
	@ResponseBody
	@RequestMapping("appVersionInfo")
	public Result<RespAppVersionInfo> appVersionInfo(@RequestBody ReqAppVersionInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(appVersionService.appVersionInfo(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveAppVersion")
	public Result<?> saveAppVersion(@RequestBody ReqSaveAppVersion req) throws Exception {
		appVersionService.saveAppVersion(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeAppVersion")
	public Result<?> removeAppVersion(@Valid @RequestBody List<ReqRemoveAppVersion> req) throws Exception {
		appVersionService.removeAppVersion(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
