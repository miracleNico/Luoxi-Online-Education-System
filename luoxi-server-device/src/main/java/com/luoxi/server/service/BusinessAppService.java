package com.luoxi.server.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.businessApp.IBusinessAppService;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppInfo;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppInfo.RespBusinessAppInfo;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppList;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppList.RespBusinessAppList;
import com.luoxi.api.businessApp.protocol.ReqRemoveBusinessApp;
import com.luoxi.api.businessApp.protocol.ReqSaveBusinessApp;
import com.luoxi.api.businessApp.protocol.ReqUpgradeThirdApp;
import com.luoxi.api.businessApp.vo.BusinessAppVo;
import com.luoxi.base.RespPaging;
import com.luoxi.base.ResultMessage;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.exception.LxException;
import com.luoxi.model.Business;
import com.luoxi.model.BusinessApp;
import com.luoxi.server.dao.BusinessAppMapper;
import com.luoxi.server.dao.BusinessMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@DubboService
public class BusinessAppService implements IBusinessAppService{

	@Autowired
	private BusinessAppMapper businessAppMapper;
	@Autowired
	private BusinessMapper businessMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * @Description: 第三方产品升级
	 * @Author wanbo
	 * @DateTime 2020/04/27
	 */
	@Override
	public BusinessAppVo upgradeThirdApp(ReqUpgradeThirdApp req) throws Exception {
		BusinessAppVo businessAppVo = new BusinessAppVo();
		String cacheKey = ConstCacheKey.UPGRADE_THIRD_APP.cacheKey(req.getBusinessCode(),req.getPackageName());
		if(stringRedisTemplate.hasKey(cacheKey)) {
			businessAppVo = JSONUtil.toBean(new JSONObject(stringRedisTemplate.opsForValue().get(cacheKey)), BusinessAppVo.class);
		}else {
			businessAppVo = businessAppMapper.latestBusinessApp(req.getBusinessCode(),req.getPackageName());
			if(BeanUtil.isNotEmpty(businessAppVo)) {
				stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(businessAppVo));
			}
		}
		if(BeanUtil.isNotEmpty(businessAppVo)) {
			businessAppVo.setUrl(cosUtil.getAccessUrl(businessAppVo.getUrl()));				
		}
		return businessAppVo;
	}
	
	public List<Map<String, Object>> businessAppTree() throws Exception{
		List<Map<String, Object>> tree = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> businessList = businessMapper.getBusinessTree();
		for (Map<String, Object> business : businessList) {
			business.put("chkDisabled", true);
			CollUtil.addAll(tree, businessAppMapper.getBusinessAppTree(MapUtil.builder("businessId", business.get("id")).build()));
		}
		CollUtil.addAll(tree,businessList);
		return tree;
	}
	
	/**
	 * @Description: 内容商产品列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespBusinessAppList> businessAppList(ReqBusinessAppList req) throws Exception {
		RespPaging<RespBusinessAppList> respPaging = new RespPaging<RespBusinessAppList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespBusinessAppList> list = businessAppMapper.businessAppList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespBusinessAppList>(list), respPaging);
		for (RespBusinessAppList businessAppList : respPaging.getList()) {
			businessAppList.setAppUrl(cosUtil.getAccessUrl(businessAppList.getAppUrl()));
		}
		return respPaging;
	}

	/**
	 * @Description: 内容商产品信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespBusinessAppInfo businessAppInfo(ReqBusinessAppInfo req) throws Exception {
		RespBusinessAppInfo resp = businessAppMapper.businessAppInfo(BeanUtil.beanToMap(req));
		resp.setAppUrl(cosUtil.getAccessUrl(resp.getAppUrl()));
		return resp;
	}

	/**
	 * @Description: 保存内容商产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void saveBusinessApp(ReqSaveBusinessApp req) throws Exception {
		BusinessApp businessApp = new BusinessApp();
		BeanUtil.copyProperties(req, businessApp);
		
		businessApp.setAppUrl(cosUtil.filterUrlDomain(businessApp.getAppUrl()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageName", req.getPackageName());
		map.put("versionCode", req.getVersionCode());
		BusinessApp dbBusinessApp = businessAppMapper.getBusinessApp(map);
		
		if(StringUtils.isNotBlank(businessApp.getBusinessAppId())) {
			//修改操作
			if(dbBusinessApp!=null && !dbBusinessApp.getBusinessAppId().equals(businessApp.getBusinessAppId())) 
				throw LxException.of().setMsg("内容商产品已存在");
			if(BeanUtil.isEmpty(dbBusinessApp) || dbBusinessApp.getBusinessAppId().equals(businessApp.getBusinessAppId())) {
				businessAppMapper.updateByPrimaryKeySelective(businessApp);				
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {
			//新增操作
			if(dbBusinessApp!=null)
				throw LxException.of().setMsg("内容商产品已存在");
			businessApp.setBusinessAppId(IdUtil.fastSimpleUUID());
			businessAppMapper.insertSelective(businessApp);
		}
		
		Business business = businessMapper.selectByPrimaryKey(businessApp.getBusinessId());
		String cacheKey = ConstCacheKey.UPGRADE_THIRD_APP.cacheKey(business.getBusinessCode(),businessApp.getPackageName());
		stringRedisTemplate.delete(cacheKey);
	}

	/**
	 * @Description: 删除内容商产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeBusinessApp(List<ReqRemoveBusinessApp> req) throws Exception {
		businessAppMapper.removeBusinessApp(req);
		for (ReqRemoveBusinessApp reqRemoveBusinessApp : req) {
			BusinessApp businessApp = businessAppMapper.selectByPrimaryKey(reqRemoveBusinessApp.getBusinessAppId());
			Business business = businessMapper.selectByPrimaryKey(businessApp.getBusinessId());
			String cacheKey = ConstCacheKey.UPGRADE_THIRD_APP.cacheKey(business.getBusinessCode(),businessApp.getPackageName());
			stringRedisTemplate.delete(cacheKey);
		}
	}
	
	
	
	
	
	
	
	
}
