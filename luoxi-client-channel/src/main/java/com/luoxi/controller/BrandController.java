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
import com.luoxi.api.brand.IBrandService;
import com.luoxi.api.brand.protocol.ReqBrandList;
import com.luoxi.api.brand.protocol.ReqBrandList.RespBrandList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.BRAND)
@Controller
@RequestMapping("brand")
public class BrandController extends BaseController{
	
	@DubboReference IBrandService brandService;
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("brandSelected")
	public Result<RespPaging<RespBrandList>> brandSelected(@RequestBody ReqBrandList req) throws Exception {
		return ResultMessage.SUCCESS.result(brandService.brandList(req));
	}
	
	
	
}
