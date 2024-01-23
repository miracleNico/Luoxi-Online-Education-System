
package com.ailoxi.client.vision.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.api.channel.protocol.ReqChannelList;
import com.luoxi.api.channel.protocol.ReqChannelList.RespChannelList;
import com.luoxi.api.vision.IEyeService;
import com.luoxi.api.vision.IVisionTest;
import com.luoxi.api.vision.protocol.ReqRemoveVisionTest;
import com.luoxi.api.vision.protocol.ReqSaveVisionTest;
import com.luoxi.api.vision.protocol.ReqSaveVisionTest.RespSaveVisionTest;
import com.luoxi.api.vision.protocol.ReqVisionTestInfo;
import com.luoxi.api.vision.protocol.ReqVisionTestInfo.RespVisionTestInfo;
import com.luoxi.api.vision.protocol.ReqVisionTestList;
import com.luoxi.api.vision.protocol.ReqVisionTestList.RespVisionTestList;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * @Description 控制器：视力测试活动
 * @author EdisonFeng
 * @DateTime 2021年4月25日
 * Copyright(c) 2021. All Rights Reserved
 */
@Api(tags={"视力测试活动"})
@RestController
@RequestMapping("api/visionTest")
public class VisionTestController extends BaseController {
	@DubboReference
	private IEyeService eyeService;
	@DubboReference
	private IVisionTest visionTestService;

	@ApiOperation(value="视力测试活动详情")
	@PostMapping("visionTestInfo")
	public Result<RespVisionTestInfo> getVisionTestInfo(@Valid @RequestBody ReqVisionTestInfo req) throws Exception {
		RespVisionTestInfo resp = null;
		try {
			resp = visionTestService.getVisionTestInfo(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="保存视力测试活动（新增、修改）")
	@PostMapping("saveVisionTest")
	public Result<RespSaveVisionTest> saveVisionTest(@RequestBody ReqSaveVisionTest req) throws Exception {
		RespSaveVisionTest resp = null;
		try {
			resp = visionTestService.saveVisionTest(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		
		return ResultMessage.SUCCESS.result(resp);
	}
	
	@ApiOperation(value="删除视力测试活动（需授权）")
	@PostMapping("removeVisionTest")
	public Result<String> removeVisionTest(@Valid @RequestBody List<ReqRemoveVisionTest> req) throws Exception {
		String resp = null;
		try {
			resp = visionTestService.removeVisionTest(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}

	@ApiOperation(value="获取视力测试活动列表")
	@PostMapping("getVisionTestList")
	public Result<RespPaging<RespVisionTestList>> getVisionTestList(@RequestBody ReqVisionTestList req) throws Exception {
		RespPaging<RespVisionTestList> resp = null;
		try {
			resp = visionTestService.getVisionTestList(req);
		}
		catch(Exception e) {
			String msg = e.getMessage();
			if(StringUtils.isBlank(msg)) msg = "未知异常";
			return ResultMessage.FAILURE.result(resp).setMsg(msg);
		}
		return ResultMessage.SUCCESS.result(resp);
	}
}
