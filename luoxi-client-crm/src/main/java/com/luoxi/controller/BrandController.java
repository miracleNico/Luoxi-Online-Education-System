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
import com.luoxi.api.brand.IBrandService;
import com.luoxi.api.brand.protocol.ReqBrandInfo;
import com.luoxi.api.brand.protocol.ReqBrandInfo.RespBrandInfo;
import com.luoxi.api.brand.protocol.ReqBrandList;
import com.luoxi.api.brand.protocol.ReqBrandList.RespBrandList;
import com.luoxi.api.brand.protocol.ReqRemoveBrand;
import com.luoxi.api.brand.protocol.ReqSaveBrand;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.BRAND)
@Controller
@RequestMapping("brand")
public class BrandController extends BaseController{
	
	@DubboReference IBrandService brandService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/brand/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String brandId) throws Exception {
		RespBrandInfo info = new RespBrandInfo();
		if(StringUtils.isNotBlank(brandId)) {
			info = brandService.brandInfo(new ReqBrandInfo().setBrandId(brandId));
		}
		request.setAttribute("info", info);
		return "page/brand/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String brandId) throws Exception {
		RespBrandInfo info = new RespBrandInfo();
		if(StringUtils.isNotBlank(brandId)) {
			info = brandService.brandInfo(new ReqBrandInfo().setBrandId(brandId));
		}
		request.setAttribute("info", info);
		return "page/brand/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("brandList")
	public Result<RespPaging<RespBrandList>> brandList(@RequestBody ReqBrandList req) throws Exception {
		return ResultMessage.SUCCESS.result(brandService.brandList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("brandSelected")
	public Result<RespPaging<RespBrandList>> brandSelected(@RequestBody ReqBrandList req) throws Exception {
		return ResultMessage.SUCCESS.result(brandService.brandList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveBrand")
	public Result<?> saveBrand(@RequestBody ReqSaveBrand req) throws Exception {
		brandService.saveBrand(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeBrand")
	public Result<?> removeBrand(@Valid @RequestBody List<ReqRemoveBrand> req) throws Exception {
		brandService.removeBrand(req);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
