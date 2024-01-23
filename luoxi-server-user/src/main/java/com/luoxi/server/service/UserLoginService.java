package com.luoxi.server.service;


import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.luoxi.api.user.IUserLoginService;
import com.luoxi.model.UserLogin;
import com.luoxi.server.dao.UserLoginMapper;

import cn.hutool.core.util.IdUtil;

@DubboService
public class UserLoginService implements IUserLoginService{
	
	@Autowired
	private UserLoginMapper userLoginMapper;
	
	@Async
	@Override
	public void loginRecord(String userId, String deviceId, String versionId) {
		UserLogin userLogin = new UserLogin()
				.setUserLoginId(IdUtil.fastSimpleUUID())
				.setUserId(userId)
				.setDeviceId(deviceId)
				.setVersionId(versionId);
		userLoginMapper.insertSelective(userLogin);
	}
	
	
}
