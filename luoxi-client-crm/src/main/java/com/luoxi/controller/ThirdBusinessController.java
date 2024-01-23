package com.luoxi.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.thirdBusiness.IThirdBusinessService;
import com.luoxi.api.thirdBusiness.protocol.ReqThirdBusinessList;
import com.luoxi.api.thirdBusiness.protocol.ReqThirdBusinessList.RespThirdBusinessList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.THIRD_BUSINESS)
@Controller
@RequestMapping("thirdBusiness")
public class ThirdBusinessController extends BaseController{
	
	@DubboReference IThirdBusinessService thirdBusinessService;
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("thirdBusinessSelected")
	public Result<RespPaging<RespThirdBusinessList>> thirdBusinessSelected(@RequestBody ReqThirdBusinessList req) throws Exception {
		return ResultMessage.SUCCESS.result(thirdBusinessService.thirdBusinessList(req));
	}
	
	
}
