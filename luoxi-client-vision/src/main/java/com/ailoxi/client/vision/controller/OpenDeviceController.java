package com.ailoxi.client.vision.controller;

//import javax.validation.Valid;
//
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.luoxi.api.device.protocol.ReqActivate;
//import com.luoxi.api.device.protocol.ReqActivate.RespActivate;
//import com.luoxi.api.openDevice.IOpenDeviceService;
//import com.luoxi.api.openDevice.protocol.ReqOpenDeviceAuth;
//import com.luoxi.api.openDevice.protocol.ReqOpenDeviceAuth.RespOpenDeviceAuth;
//import com.luoxi.base.Result;
//import com.luoxi.base.ResultMessage;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@Api(tags={"设备"})
//@RestController
//@RequestMapping("api/device")
//public class OpenDeviceController extends BaseController{
//	
//	@DubboReference
//	private IOpenDeviceService openDeviceService;
//	
//	@ApiOperation(value="认证")
//	@PostMapping("auth")
//	public Result<RespOpenDeviceAuth> auth(@Valid @RequestBody ReqOpenDeviceAuth req) throws Exception {
//		return ResultMessage.SUCCESS.result(openDeviceService.auth(req));
//	}
//	
////	@ApiOperation(value="SN码激活")
////	@PostMapping("activate")
////	public Result<RespActivate> activate(@Valid @RequestBody ReqActivate requestParam) throws Exception {
////		requestParam.setPackageName(getPackageName());
////		return ResultMessage.SUCCESS.result(deviceSerivce.activate(requestParam));
////	}
//	
//}
