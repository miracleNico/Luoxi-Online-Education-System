package com.luoxi.server.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.api.userAddress.IUserAddressService;
import com.luoxi.api.userAddress.protocol.ReqSaveUserAddress;
import com.luoxi.api.userAddress.protocol.ReqSaveUserAddress.RespSaveUserAddress;
import com.luoxi.api.userAddress.protocol.ReqUserAddressInfo.RespUserAddressInfo;
import com.luoxi.model.UserAddress;
import com.luoxi.server.dao.UserAddressMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@DubboService
public class UserAddressService implements IUserAddressService{
	
	@Autowired
	private UserAddressMapper userAddressMapper;
	
	@Override
	public RespSaveUserAddress saveUserAddress(String userId, ReqSaveUserAddress req) throws Exception {
		UserAddress userAddress = BeanUtil.copyProperties(req, UserAddress.class);
		if(StrUtil.isBlank(req.getUserAddressId())) {
			userAddress.setUserAddressId(IdUtil.fastSimpleUUID());
			userAddress.setUserId(userId);
			userAddressMapper.insertSelective(userAddress);
		}else {
			userAddressMapper.updateByPrimaryKeySelective(userAddress);
		}
		return BeanUtil.copyProperties(userAddress, RespSaveUserAddress.class);
	}
	
	@Override
	public RespUserAddressInfo userAddressInfo(String userId) throws Exception {
		return BeanUtil.copyProperties(userAddressMapper.userAddressInfo(userId), RespUserAddressInfo.class);
	}
	
}
