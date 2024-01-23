package com.luoxi.base;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.region.IRegionService;

import cn.hutool.json.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@ApiModule(module = Module.REGION)
@Api(tags = { "地区" })
@RestController
@RequestMapping("api/region")
public class RegionController {

	@DubboReference
	private IRegionService regionService;
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperation(value="加载所有地区")
	@PostMapping("loadAllRegion")
	public Result<JSONArray> loadAllRegion() throws Exception{
		return ResultMessage.SUCCESS.result(regionService.loadAllRegion());
	}

}
