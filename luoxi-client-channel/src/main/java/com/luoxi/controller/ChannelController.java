package com.luoxi.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.luoxi.api.channel.IChannelService;
import com.luoxi.api.channel.protocol.ReqChannelLogin;
import com.luoxi.api.channel.protocol.ReqChannelLogin.RespChannelLogin;
import com.luoxi.api.channel.protocol.ReqUpdChannelPassword;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.utils.HttpServletRequestUtil;

@ApiModule(module = Module.CHANNEL)
@Controller
@RequestMapping("channel")
public class ChannelController extends BaseController{

	@DubboReference
	private IChannelService channelService;
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.LOGIN)
	@ResponseBody
	@RequestMapping("login")
	public Result<?> login(@RequestBody ReqChannelLogin req) throws Exception {
		RespChannelLogin channel = channelService.login(req);
		//登录成功后缓存sessionId,控制后台用户只能登入一端
		channelService.cacheSession(channel.getChannelId(), HttpServletRequestUtil.getRequest().getSession().getId());
		
		HttpServletRequestUtil.getRequest().getSession().setAttribute("channel", channel);
		
		//System.out.println(HttpServletRequestUtil.getRequest().getSession().getId()+"---------"+HttpServletRequestUtil.getRequest().getSession().getServletContext().getSessionTimeout());
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("menu")
	public String menu() {
		return getChannel().getJsonMenus();
	}
	
	@ApiPermission(AUTH.OPEN)
	@RequestMapping("updPwdPage")
	public String updPwdPage() {
		return "page/channel/updPwd";
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.UPDATE_PWD)
	@ResponseBody
	@RequestMapping("updChannelPassword")
	public Result<?> updChannelPassword(@Valid @RequestBody ReqUpdChannelPassword req) throws Exception{
		channelService.updChannelPassword(getChannelId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiPermission(AUTH.OPEN)
	@ApiOperLog(action = ACTION.LOGOUT)
	@RequestMapping("logout")
	public String logout(HttpServletRequest request){
		request.getSession().removeAttribute("channel");
		return "redirect:/";
	}
	
}
