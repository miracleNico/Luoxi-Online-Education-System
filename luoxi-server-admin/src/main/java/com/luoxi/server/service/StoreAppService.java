package com.luoxi.server.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.app.IAppService;
import com.luoxi.api.app.vo.AppVo;
import com.luoxi.api.storeApp.IStoreAppService;
import com.luoxi.api.storeApp.protocol.ReqRemoveStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSaveStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSearchStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSearchStoreApp.RespSearchStoreApp;
import com.luoxi.api.storeApp.protocol.ReqStoreAppInfo;
import com.luoxi.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;
import com.luoxi.api.storeApp.protocol.ReqStoreAppList;
import com.luoxi.api.storeApp.protocol.ReqStoreAppList.RespStoreAppList;
import com.luoxi.api.storeApp.protocol.ReqStoreAppScopeInfo.RespStoreAppScopeInfo;
import com.luoxi.base.RespPaging;
import com.luoxi.base.ResultMessage;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.constant.ConstStoreAppContentType;
import com.luoxi.constant.ConstStoreAppPhaseTag;
import com.luoxi.constant.ConstStoreAppScore;
import com.luoxi.constant.ConstUpgradeScore;
import com.luoxi.exception.LxException;
import com.luoxi.model.StoreApp;
import com.luoxi.model.StoreAppScope;
import com.luoxi.server.dao.StoreAppMapper;
import com.luoxi.util.redis.RedisHelper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@DubboService
public class StoreAppService implements IStoreAppService{

	@Autowired
	private StoreAppMapper storeAppMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisHelper redisHelper;
	@DubboReference
	private IAppService appService;
	
	@Override
	public RespPaging<RespSearchStoreApp> searchStoreApp(ReqSearchStoreApp req) throws Exception {
		RespPaging<RespSearchStoreApp> respPaging = new RespPaging<RespSearchStoreApp>();
		AppVo appVo = appService.getAppByPackageName(req.getPackageName());
		req.setChannelId(appVo.getChannelId());
		String cacheKey = ConstCacheKey.STORE_APP.cacheKey(String.valueOf(req.hashCode()));
		Object obj = stringRedisTemplate.opsForValue().get(cacheKey);
		respPaging = JSONUtil.toBean(new JSONObject(obj),RespPaging.class,true);
		if(ObjectUtil.isEmpty(obj)) {
			PageHelper.startPage(req.getPageNum(), req.getPageSize());
			List<RespSearchStoreApp> list = storeAppMapper.searchStoreApp(req);
			for (RespSearchStoreApp resp : list) {
				resp.setIcon(cosUtil.getAccessUrl(resp.getIcon()));
				resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
				if(resp.getFileSize()!=null)resp.setFileSize(resp.getFileSize().divide(BigDecimal.valueOf(1024*1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			BeanUtil.copyProperties(new PageInfo<RespSearchStoreApp>(list), respPaging);
			stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(respPaging), ConstCacheKey.STORE_APP.getExpire(),TimeUnit.SECONDS);
		}
		return respPaging;
	}
	
	/**
	 * @Description: 应用商店列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespStoreAppList> storeAppList(ReqStoreAppList req) throws Exception {
		RespPaging<RespStoreAppList> respPaging = new RespPaging<RespStoreAppList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespStoreAppList> list = storeAppMapper.storeAppList(BeanUtil.beanToMap(req));
		for (RespStoreAppList resp : list) {
			resp.setContentTypeName(EnumUtil.fromStringQuietly(ConstStoreAppContentType.class, resp.getContentType()).getText());
			resp.setAppScopeName(EnumUtil.fromStringQuietly(ConstStoreAppScore.class, StrUtil.concat(true, "_",resp.getAppScope())).getText());
			resp.setIcon(cosUtil.getAccessUrl(resp.getIcon()));
			resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
			if(resp.getFileSize()!=null)resp.setFileSize(resp.getFileSize().divide(BigDecimal.valueOf(1024*1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
			if(StrUtil.isNotBlank(resp.getPhaseTag())) {
				List<String> phaseTags = new ArrayList<String>();
				for (String phaseTag : StrUtil.split(resp.getPhaseTag(), ',')) {
					phaseTag = EnumUtil.fromStringQuietly(ConstStoreAppPhaseTag.class, StrUtil.concat(true, "_",phaseTag)).getText();
					phaseTags.add(phaseTag);
				}
				resp.setPhaseTagName(StrUtil.join(",", phaseTags));
			}
		}
		BeanUtil.copyProperties(new PageInfo<RespStoreAppList>(list), respPaging);
		return respPaging;
	}

	/**
	 * @Description: 应用商店信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespStoreAppInfo storeAppInfo(ReqStoreAppInfo req) throws Exception {
		RespStoreAppInfo resp = storeAppMapper.storeAppInfo(BeanUtil.beanToMap(req));
		resp.setContentTypeName(EnumUtil.fromStringQuietly(ConstStoreAppContentType.class, resp.getContentType()).getText());
		resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
		resp.setIcon(cosUtil.getAccessUrl(resp.getIcon()));
		if(StrUtil.isNotBlank(resp.getImages())) {
			List<String> imgs = new ArrayList<String>();
			for(String img : StrUtil.split(resp.getImages(), ',')) {
				imgs.add(cosUtil.getAccessUrl(img));
			}
			resp.setImages(StrUtil.join(",", imgs));
		}
		if(resp.getAppScope().equals(ConstUpgradeScore.SPECIFIED.getVal())) {
			List<RespStoreAppScopeInfo> storeAppScopeInfos = storeAppMapper.storeAppScopeInfoList(resp.getStoreAppId());
			resp.setStoreAppScopeInfoList(storeAppScopeInfos);
		}
		return resp;
	}

	/**
	 * @Description: 保存应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	@Transactional
	public void saveStoreApp(ReqSaveStoreApp req) throws Exception {
		if(ConstUpgradeScore.SPECIFIED.getVal().equals(req.getAppScope()) && CollectionUtil.isEmpty(req.getStoreAppScopeInfoList())) 
			throw LxException.of().setMsg("指定范围不能为空，请选择！");
		
		StoreApp storeApp = new StoreApp();
		BeanUtil.copyProperties(req, storeApp);
		
		storeApp.setUrl(cosUtil.filterUrlDomain(storeApp.getUrl()));
		storeApp.setIcon(cosUtil.filterUrlDomain(storeApp.getIcon()));
		if(StrUtil.isNotBlank(storeApp.getImages())) {
			List<String> imgs = new ArrayList<String>();
			for(String img : StrUtil.split(storeApp.getImages(), ',')) {
				imgs.add(cosUtil.filterUrlDomain(img));
			}
			storeApp.setImages(StrUtil.join(",", imgs));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageName", req.getPackageName());
		StoreApp dbStoreApp = storeAppMapper.getStoreApp(map);
		
		if(StringUtils.isNotBlank(storeApp.getStoreAppId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbStoreApp) && !dbStoreApp.getStoreAppId().equals(storeApp.getStoreAppId()))
				throw LxException.of().setMsg("包名已存在");
			if(BeanUtil.isEmpty(dbStoreApp) || dbStoreApp.getStoreAppId().equals(storeApp.getStoreAppId())) {
				storeAppMapper.updateByPrimaryKeySelective(storeApp);
				resetStoreAppScope(req.getStoreAppScopeInfoList(), storeApp.getStoreAppId());
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbStoreApp)) throw LxException.of().setMsg("包名已存在");
			String storeAppId = IdUtil.fastSimpleUUID();
			storeApp.setStoreAppId(storeAppId);
			storeAppMapper.insertSelective(storeApp);
			resetStoreAppScope(req.getStoreAppScopeInfoList(), storeAppId);
		}
		
		redisHelper.keys(ConstCacheKey.STORE_APP.cacheKey("*")).forEach(key->{
			stringRedisTemplate.delete(key);
		});
	}
	
	private void resetStoreAppScope(List<RespStoreAppScopeInfo> list,String storeAppId) {
		if(CollectionUtil.isNotEmpty(list)) {
			storeAppMapper.removeStoreAppScope(storeAppId);
			List<StoreAppScope> storeAppScopes = JSONUtil.toList(new JSONArray(list), StoreAppScope.class);
			for (StoreAppScope storeAppScope : storeAppScopes) {
				storeAppScope.setStoreAppScopeId(IdUtil.fastSimpleUUID());
				storeAppScope.setStoreAppId(storeAppId);
			}
			storeAppMapper.addStoreAppScope(storeAppScopes);
		}
	}
	
	/**
	 * @Description: 删除应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeStoreApp(List<ReqRemoveStoreApp> req) throws Exception {
		storeAppMapper.removeStoreApp(req);
		redisHelper.keys(ConstCacheKey.STORE_APP.cacheKey("*")).forEach(key->{
			stringRedisTemplate.delete(key);
		});
	}
	
	
	
	
	
}
