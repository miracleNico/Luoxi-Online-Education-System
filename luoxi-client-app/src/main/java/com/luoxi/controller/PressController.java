package com.luoxi.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.press.IPressService;
import com.luoxi.api.press.protocol.ReqPressList.RespPressList;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"出版社"})
@RestController
@RequestMapping("api/press")
public class PressController extends BaseController{
	
	@DubboReference
	private IPressService pressService;
	
	@ApiOperation(value="出版社列表")
	@PostMapping("pressList")
	public Result<List<RespPressList>> pressList() throws Exception {
		return ResultMessage.SUCCESS.result(pressService.pressList());
	}
	
}
