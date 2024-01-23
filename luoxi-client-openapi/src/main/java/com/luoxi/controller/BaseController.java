package com.luoxi.controller;

import javax.servlet.http.HttpServletRequest;

import com.luoxi.api.openDevice.vo.OpenDeviceToken;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;
import com.luoxi.utils.HttpServletRequestUtil;
import com.luoxi.utils.token.TokenUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class BaseController {
	
	public OpenDeviceToken getOpenDeviceToken() throws Exception{
		String token = HttpServletRequestUtil.getRequest().getHeader("Authorization");
		return JSONUtil.toBean(TokenUtil.getPayloadSub(token), OpenDeviceToken.class);
	}
	
	public String getAppId() throws Exception {
		String appId = null;
		HttpServletRequest request = HttpServletRequestUtil.getRequest();
		if("/api/device/auth".equals(request.getRequestURI())) {
    		appId = new JSONObject(ServletUtil.getBody(request)).getStr("appId");
    	}else {
    		appId = getOpenDeviceToken().getAppId();
    	}
		if(StrUtil.isBlank(appId)) throw LxException.of().setResult(ResultMessage.FAILURE_REQUEST_PARAM.result().setMsg("appId is not empty"));
		return appId;
	}
	
	public String getDeviceId() throws Exception {
		return getOpenDeviceToken().getDeviceId();
	}
	
}
