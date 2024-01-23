package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.model.IModelService;
import com.luoxi.api.model.protocol.ReqBrandModelList;
import com.luoxi.api.model.protocol.ReqModelList.RespModelList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.MODEL)
@Controller
@RequestMapping("model")
public class ModelController extends BaseController{
	
	@DubboReference IModelService modelService;
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("brandModelSelected")
	public Result<RespPaging<RespModelList>> brandModelSelected(@Valid @RequestBody ReqBrandModelList req) throws Exception {
		if(StringUtils.isBlank(req.getBrand())) 
			return ResultMessage.SUCCESS.result(new RespPaging<RespModelList>());
		return ResultMessage.SUCCESS.result(modelService.modelList(req));
	}
	
	
}
