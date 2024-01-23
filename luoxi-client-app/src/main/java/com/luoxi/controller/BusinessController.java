package com.luoxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.businessSerialNumber.IBusinessSerialNumberService;
import com.luoxi.api.businessSerialNumber.protocol.ReqGetBusinessSn;
import com.luoxi.api.businessSerialNumber.protocol.ReqGetBusinessSn.RespGetBusinessSn;
import com.luoxi.api.businessSerialNumber.protocol.ReqUpdBusinessSnUseStatus;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.utils.ValidList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"内容商"})
@RestController
@RequestMapping("api/business")
public class BusinessController extends BaseController{
	
	@DubboReference
	private IBusinessSerialNumberService businessSerialNumberService;
	
	@ApiOperation(value="获取内容商激活码")
	@PostMapping("getBusinessSn")
	public Result<List<RespGetBusinessSn>> getBusinessSn(@Valid @RequestBody ValidList<ReqGetBusinessSn> req) throws Exception {
		return ResultMessage.SUCCESS.result(businessSerialNumberService.getBusinessSn(getPackageName(),getDeviceId(),req));
	}
	
	@ApiOperation(value="更新激活码使用状态")
	@PostMapping("updBusinessSnUseStatus")
	public Result<?> updBusinessSnUseStatus(@Valid @RequestBody ReqUpdBusinessSnUseStatus req) throws Exception {
		businessSerialNumberService.updBusinessSnUseStatus(getPackageName(),getVersionCode(),getDeviceId(),req);
		return ResultMessage.SUCCESS.result();
	}
	
}
