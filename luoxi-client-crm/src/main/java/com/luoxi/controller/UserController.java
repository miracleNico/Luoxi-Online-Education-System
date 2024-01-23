package com.luoxi.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.api.user.IUserService;
import com.luoxi.api.user.protocol.ReqUserList;
import com.luoxi.api.user.protocol.ReqUserList.RespUserList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import cn.hutool.core.util.StrUtil;

@ApiModule(module = Module.USER)
@Controller
@RequestMapping("user")
public class UserController extends BaseController{
	
	@DubboReference IUserService userService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/user/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("userList")
	public Result<RespPaging<RespUserList>> userList(@RequestBody ReqUserList req) throws Exception {
		RespPaging<RespUserList> respPaging = userService.userList(req);
		for (RespUserList resp : respPaging.getList()) {
			resp.setPhone(StrUtil.replace(resp.getPhone(), 3, 7, '*'));
		}
		return ResultMessage.SUCCESS.result(respPaging);
	}
	
	/**
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeUser")
	public Result<?> removeUser(@Valid @RequestBody List<ReqRemoveUser> req) throws Exception {
		userService.removeUser(req);
		return ResultMessage.SUCCESS.result();
	}*/
	
	
}
