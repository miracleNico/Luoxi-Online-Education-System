package com.luoxi.server.service;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.aop.LxRedisCache;
import com.luoxi.api.press.IPressService;
import com.luoxi.api.press.protocol.ReqPressList.RespPressList;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.server.dao.PressMapper;

@DubboService
public class PressService implements IPressService{
	
	@Autowired
	private PressMapper pressMapper;
	
	@LxRedisCache(key = ConstCacheKey.PRESS)
	@Override
	public List<RespPressList> pressList() throws Exception {
		return pressMapper.pressList();
	}

}
