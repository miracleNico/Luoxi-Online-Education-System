package com.luoxi.server.service;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.luoxi.api.eduDirectory.protocol.ReqEduCondition;
import com.luoxi.api.eduDirectory.protocol.ReqEduCondition.RespEduCondition;
import com.luoxi.api.eduDirectory.protocol.ReqEduCondition.RespEduConditionKV;
import com.luoxi.api.eduResource.IEduConditionService;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.constant.ConstEduResourceMediaType;
import com.luoxi.server.dao.EduDirectoryMapper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

@DubboService
public class EduConditionService implements IEduConditionService{
	@Autowired
	private EduDirectoryMapper eduDirectoryMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * @Description: 筛选条件
	 * @Author wanbo
	 * @DateTime 2020/04/02
	 */
	@Override
	public List<RespEduCondition> eduCondition() throws Exception {
		List<RespEduCondition> respList = new ArrayList<RespEduCondition>();
		String key = ConstCacheKey.EDU_CONDITION.cacheKey();
		if(stringRedisTemplate.hasKey(key)) {
			respList = JSONUtil.toList(new JSONArray(stringRedisTemplate.opsForValue().get(key)), RespEduCondition.class);
		}else {
			List<RespEduConditionKV> ageList = CollUtil.newArrayList(
					new RespEduConditionKV().setKey("0-2").setVal("0-2"),
					new RespEduConditionKV().setKey("3-4").setVal("3-4"),
					new RespEduConditionKV().setKey("5-6").setVal("5-6"),
					new RespEduConditionKV().setKey("7-10").setVal("7-10")
					);
			List<RespEduConditionKV> languageList = CollUtil.newArrayList(
					new RespEduConditionKV().setKey("中文").setVal("cn"),
					new RespEduConditionKV().setKey("英文").setVal("en")
					);
			
			List<RespEduConditionKV> themeList = eduDirectoryMapper.eduThemeList();
			
			respList = CollUtil.newArrayList(
					new RespEduCondition().setType("age").setList(ageList),
					new RespEduCondition().setType("language").setList(languageList),
					new RespEduCondition().setType("theme").setList(themeList)
					);
			
			stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(respList),Duration.ofSeconds(ConstCacheKey.EDU_CONDITION.getExpire()));
		}
		return respList;
	}
	
	@Override
	public List<RespEduCondition> eduConditionOther() throws Exception {
		List<RespEduCondition> respList = new ArrayList<RespEduCondition>();
		String key = ConstCacheKey.EDU_CONDITION_OTHER.cacheKey();
		if(stringRedisTemplate.hasKey(key)) {
			respList = JSONUtil.toList(new JSONArray(stringRedisTemplate.opsForValue().get(key)), RespEduCondition.class);
		}else {
			List<RespEduConditionKV> ageList = CollUtil.newArrayList(
					new RespEduConditionKV().setKey("0-2").setVal("0-2"),
					new RespEduConditionKV().setKey("3-4").setVal("3-4"),
					new RespEduConditionKV().setKey("5-6").setVal("5-6"),
					new RespEduConditionKV().setKey("7-10").setVal("7-10")
					);
			List<RespEduConditionKV> languageList = CollUtil.newArrayList(
					new RespEduConditionKV().setKey("中文").setVal("cn"),
					new RespEduConditionKV().setKey("英文").setVal("en")
					);
			
			respList = CollUtil.newArrayList(
					new RespEduCondition().setType("age").setList(ageList),
					new RespEduCondition().setType("language").setList(languageList)
					);
			
			stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(respList),Duration.ofSeconds(ConstCacheKey.EDU_CONDITION_OTHER.getExpire()));
		}
		return respList;
	}
	
	@Override
	public List<RespEduCondition> eduConditionTheme(ReqEduCondition req) throws Exception {
		List<RespEduCondition> respList = new ArrayList<RespEduCondition>();
		String key = ConstCacheKey.EDU_CONDITION_THEME.cacheKey(req.getMediaType());
		if(stringRedisTemplate.hasKey(key)) {
			respList = JSONUtil.toList(new JSONArray(stringRedisTemplate.opsForValue().get(key)), RespEduCondition.class);
		}else {
			List<RespEduConditionKV> themeList = eduDirectoryMapper.eduThemeListByMediaType(req.getMediaType());
			respList = CollUtil.newArrayList(new RespEduCondition().setType("theme").setList(themeList));
			stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(respList),Duration.ofSeconds(ConstCacheKey.EDU_CONDITION_THEME.getExpire()));
		}
		return respList;
	}
	
	@Override
	public List<RespEduCondition> eduConditionDynamicAll(ReqEduCondition req) throws Exception {
		List<RespEduCondition> respList = new ArrayList<RespEduCondition>();
		ConstCacheKey constCacheKey = ConstCacheKey.EDU_CONDITION_DYNAMIC_ALL;
		String key = constCacheKey.cacheKey(req.getMediaType());
		if(stringRedisTemplate.hasKey(key)) {
			respList = JSONUtil.toList(new JSONArray(stringRedisTemplate.opsForValue().get(key)), RespEduCondition.class);
		}else {
			if(EnumUtil.equals(ConstEduResourceMediaType.IMGBOOK, req.getMediaType())) {
				List<RespEduConditionKV> imgBookTypeList = CollUtil.newArrayList(
						new RespEduConditionKV().setKey("互动绘本").setVal("AMT"),
						new RespEduConditionKV().setKey("电子绘本").setVal("ETC"),
						new RespEduConditionKV().setKey("英语分级").setVal("LVRD"),
						new RespEduConditionKV().setKey("游戏绘本").setVal("GAME")
						);
				respList.add(new RespEduCondition().setType("imgBookType").setList(imgBookTypeList));				
			}
			List<RespEduConditionKV> ageList = CollUtil.newArrayList(
					new RespEduConditionKV().setKey("0-2").setVal("0-2"),
					new RespEduConditionKV().setKey("3-4").setVal("3-4"),
					new RespEduConditionKV().setKey("5-6").setVal("5-6"),
					new RespEduConditionKV().setKey("7-10").setVal("7-10")
					);
			List<RespEduConditionKV> languageList = CollUtil.newArrayList(
					new RespEduConditionKV().setKey("中文").setVal("cn"),
					new RespEduConditionKV().setKey("英文").setVal("en")
					);
			List<RespEduConditionKV> themeList = eduDirectoryMapper.eduThemeListByMediaType(req.getMediaType());
			
			CollUtil.addAll(respList, Arrays.asList(
					new RespEduCondition().setType("age").setList(ageList),
					new RespEduCondition().setType("language").setList(languageList),
					new RespEduCondition().setType("theme").setList(themeList)
					));
			
			stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(respList),Duration.ofSeconds(constCacheKey.getExpire()));
		}
		return respList;
	}
	
	
}
