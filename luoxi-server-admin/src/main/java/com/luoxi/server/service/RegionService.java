package com.luoxi.server.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.luoxi.api.region.IRegionService;
import com.luoxi.constant.ConstCacheKey;

import cn.hutool.json.JSONArray;

@DubboService
public class RegionService implements IRegionService{

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public JSONArray loadAllRegion() throws Exception{
		return new JSONArray(stringRedisTemplate.opsForValue().get(ConstCacheKey.REGION_ALL.cacheKey()));
	}
	
}
