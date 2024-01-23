package com.luoxi.base;

import javax.servlet.http.HttpServletRequest;

import com.luoxi.api.channel.protocol.ReqChannelLogin.RespChannelLogin;
import com.luoxi.utils.HttpServletRequestUtil;

public class BaseController {
	
	public String getChannelId(){
		return getChannel()!=null?getChannel().getChannelId():null;
	}
	
	public String getUsername(){
		return getChannel()!=null?getChannel().getUsername():null;
	}
	
	public RespChannelLogin getChannel(){
		HttpServletRequest request = HttpServletRequestUtil.getRequest();
		Object object = request.getSession().getAttribute("channel");
		if(object!=null) {
			RespChannelLogin channel = (RespChannelLogin) object;
			return channel;
		}
		return null;
	}
	
}
