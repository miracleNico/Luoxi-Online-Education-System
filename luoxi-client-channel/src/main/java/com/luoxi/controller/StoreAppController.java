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
import com.luoxi.api.storeApp.IStoreAppService;
import com.luoxi.api.storeApp.protocol.ReqRemoveStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSaveStoreApp;
import com.luoxi.api.storeApp.protocol.ReqStoreAppInfo;
import com.luoxi.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;
import com.luoxi.api.storeApp.protocol.ReqStoreAppList;
import com.luoxi.api.storeApp.protocol.ReqStoreAppList.RespStoreAppList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.STORE_APP)
@Controller
@RequestMapping("storeApp")
public class StoreAppController extends BaseController{
	
	@DubboReference IStoreAppService storeAppService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/storeApp/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String storeAppId) throws Exception {
		RespStoreAppInfo info = new RespStoreAppInfo();
		if(StringUtils.isNotBlank(storeAppId)) {
			info = storeAppService.storeAppInfo(new ReqStoreAppInfo().setStoreAppId(storeAppId));
		}
		request.setAttribute("info", info);
		return "page/storeApp/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String storeAppId) throws Exception {
		RespStoreAppInfo info = new RespStoreAppInfo();
		if(StringUtils.isNotBlank(storeAppId)) {
			info = storeAppService.storeAppInfo(new ReqStoreAppInfo().setStoreAppId(storeAppId));
		}
		request.setAttribute("info", info);
		return "page/storeApp/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("storeAppList")
	public Result<RespPaging<RespStoreAppList>> deviceList(@RequestBody ReqStoreAppList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(storeAppService.storeAppList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveStoreApp")
	public Result<?> saveStoreApp(@RequestBody ReqSaveStoreApp req) throws Exception {
		req.setChannelId(getChannelId());
		storeAppService.saveStoreApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeStoreApp")
	public Result<?> removeStoreApp(@Valid @RequestBody List<ReqRemoveStoreApp> req) throws Exception {
		storeAppService.removeStoreApp(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
