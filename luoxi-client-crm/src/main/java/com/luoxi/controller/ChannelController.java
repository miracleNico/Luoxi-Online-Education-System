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
import com.luoxi.api.channel.IChannelService;
import com.luoxi.api.channel.protocol.ReqChannelInfo;
import com.luoxi.api.channel.protocol.ReqChannelInfo.RespChannelInfo;
import com.luoxi.api.channel.protocol.ReqChannelList;
import com.luoxi.api.channel.protocol.ReqChannelList.RespChannelList;
import com.luoxi.api.channel.protocol.ReqRemoveChannel;
import com.luoxi.api.channel.protocol.ReqResetChannelPassword;
import com.luoxi.api.channel.protocol.ReqSaveChannel;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.CHANNEL)
@Controller
@RequestMapping("channel")
public class ChannelController{
	
	@DubboReference IChannelService channelService;
	
	@RequestMapping("listPage")
	public String toListPage() {
		return "page/channel/list";
	}
	
	@RequestMapping("editPage")
	public String toEditPage(HttpServletRequest request,String channelId) throws Exception {
		RespChannelInfo info = new RespChannelInfo();
		if(StringUtils.isNotBlank(channelId)) {
			info = channelService.channelInfo(new ReqChannelInfo().setChannelId(channelId));
		}
		request.setAttribute("info", info);
		return "page/channel/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String channelId) throws Exception {
		RespChannelInfo info = new RespChannelInfo();
		if(StringUtils.isNotBlank(channelId)) {
			info = channelService.channelInfo(new ReqChannelInfo().setChannelId(channelId));
		}
		request.setAttribute("info", info);
		return "page/channel/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("channelList")
	public Result<RespPaging<RespChannelList>> deviceList(@RequestBody ReqChannelList req) throws Exception {
		return ResultMessage.SUCCESS.result(channelService.channelList(req));
	}
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("channelSelected")
	public Result<RespPaging<RespChannelList>> channelSelected(@RequestBody ReqChannelList req) throws Exception {
		return ResultMessage.SUCCESS.result(channelService.channelList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveChannel")
	public Result<?> saveChannel(@Valid @RequestBody ReqSaveChannel req) throws Exception {
		channelService.saveChannel(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeChannel")
	public Result<?> removeChannel(@RequestBody List<ReqRemoveChannel> req) throws Exception {
		channelService.removeChannel(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.UPDATE_PWD)
	@ResponseBody
	@RequestMapping("resetChannelPassword")
	public Result<?> resetChannelPassword(@Valid @RequestBody ReqResetChannelPassword req) throws Exception {
		channelService.resetChannelPassword(req.getChannelId());
		return ResultMessage.SUCCESS.result();
	}
	
	
}
