package com.luoxi.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.press.IPressService;
import com.luoxi.api.press.protocol.ReqPressList.RespPressList;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.PRESS)
@Controller
@RequestMapping("press")
public class PressController extends BaseController{
	
	@DubboReference IPressService pressService;
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("pressSelected")
	public Result<List<RespPressList>> pressSelected() throws Exception {
		return ResultMessage.SUCCESS.result(pressService.pressList());
	}
	
	
}
