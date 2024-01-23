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
import com.luoxi.api.model.IModelService;
import com.luoxi.api.model.protocol.ReqBrandModelList;
import com.luoxi.api.model.protocol.ReqModelInfo;
import com.luoxi.api.model.protocol.ReqModelInfo.RespModelInfo;
import com.luoxi.api.model.protocol.ReqModelList;
import com.luoxi.api.model.protocol.ReqModelList.RespModelList;
import com.luoxi.api.model.protocol.ReqRemoveModel;
import com.luoxi.api.model.protocol.ReqSaveModel;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.MODEL)
@Controller
@RequestMapping("model")
public class ModelController extends BaseController{
	
	@DubboReference IModelService modelService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/model/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String modelId) throws Exception {
		RespModelList info = new RespModelList();
		if(StringUtils.isNotBlank(modelId)) {
			info = modelService.modelInfo(new ReqModelInfo().setModelId(modelId));
		}
		request.setAttribute("info", info);
		return "page/model/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String modelId) throws Exception {
		RespModelList info = new RespModelList();
		if(StringUtils.isNotBlank(modelId)) {
			info = modelService.modelInfo(new ReqModelInfo().setModelId(modelId));
		}
		request.setAttribute("info", info);
		return "page/model/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("modelList")
	public Result<RespPaging<RespModelList>> modelList(@Valid @RequestBody ReqModelList req) throws Exception {
		return ResultMessage.SUCCESS.result(modelService.modelList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("brandModelSelected")
	public Result<RespPaging<RespModelList>> brandModelSelected(@Valid @RequestBody ReqBrandModelList req) throws Exception {
		if(StringUtils.isBlank(req.getBrand())) 
			return ResultMessage.SUCCESS.result(new RespPaging<RespModelList>());
		return ResultMessage.SUCCESS.result(modelService.modelList(req));
	}
	
	@ResponseBody
	@RequestMapping("modelInfo")
	public Result<RespModelInfo> modelInfo(@RequestBody ReqModelInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(modelService.modelInfo(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveModel")
	public Result<?> saveModel(@RequestBody ReqSaveModel req) throws Exception {
		modelService.saveModel(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeModel")
	public Result<?> removeModel(@Valid @RequestBody List<ReqRemoveModel> req) throws Exception {
		modelService.removeModel(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
