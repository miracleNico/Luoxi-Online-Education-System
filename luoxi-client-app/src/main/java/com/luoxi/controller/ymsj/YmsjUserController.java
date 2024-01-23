package com.luoxi.controller.ymsj;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.user.IUserService;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.controller.ymsj.protocol.ReqSyncUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"御目世家用户同步"})
@RestController
@RequestMapping("openapi/user")
public class YmsjUserController {
	
	@DubboReference
	private IUserService userService;
	
	@ApiOperation(value="syncUser")
	@PostMapping("syncUser")
	public Result<?> syncUser(@Valid @RequestBody ReqSyncUser req) throws Exception{
		return ResultMessage.SUCCESS.result(userService.syncUser(req.getPhone()));
	}
	
}
