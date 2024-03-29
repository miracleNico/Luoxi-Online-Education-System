package com.luoxi.server.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.businessSerialNumber.IBusinessSerialNumberService;
import com.luoxi.api.businessSerialNumber.protocol.ReqBusinessSerialNumberList;
import com.luoxi.api.businessSerialNumber.protocol.ReqBusinessSerialNumberList.RespBusinessSerialNumberList;
import com.luoxi.api.businessSerialNumber.protocol.ReqGetBusinessSn;
import com.luoxi.api.businessSerialNumber.protocol.ReqGetBusinessSn.RespGetBusinessSn;
import com.luoxi.api.businessSerialNumber.protocol.ReqImportBusinessSerialNumber;
import com.luoxi.api.businessSerialNumber.protocol.ReqRemoveBusinessSerialNumber;
import com.luoxi.api.businessSerialNumber.protocol.ReqRestoreSn;
import com.luoxi.api.businessSerialNumber.protocol.ReqUpdBusinessSnUseStatus;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.ConstBusinessSnCallType;
import com.luoxi.constant.ConstBusinessSnCodeUseStatus;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.constant.Constant;
import com.luoxi.exception.LxException;
import com.luoxi.model.App;
import com.luoxi.model.AppVersion;
import com.luoxi.model.Business;
import com.luoxi.model.BusinessSerialNumber;
import com.luoxi.model.DeviceApp;
import com.luoxi.model.DeviceAppBusinessSnCode;
import com.luoxi.server.dao.AppMapper;
import com.luoxi.server.dao.AppVersionMapper;
import com.luoxi.server.dao.BusinessMapper;
import com.luoxi.server.dao.BusinessSerialNumberMapper;
import com.luoxi.server.dao.DeviceAppBusinessSnCodeMapper;
import com.luoxi.server.dao.DeviceAppMapper;
import com.luoxi.util.SmsUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;

@DubboService
public class BusinessSerialNumberService implements IBusinessSerialNumberService{
	
	@Autowired private BusinessSerialNumberMapper businessSerialNumberMapper;
	@Autowired private BusinessMapper businessMapper;
	@Autowired private AppVersionMapper appVersionMapper;
	@Autowired private DeviceAppMapper deviceAppMapper;
	@Autowired private DeviceAppBusinessSnCodeMapper deviceAppBusinessSnCodeMapper;
	@Autowired private StringRedisTemplate stringRedisTemplate;
	@Resource private RedisTemplate<String, Integer> redisTemplate;
	@Autowired private Constant constant;
	@Autowired private SmsUtil smsUtil;
	@Autowired private AppMapper appMapper;
	
	
	/**
	 * @Description: 内容商激活码列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespBusinessSerialNumberList> businessSerialNumberList(ReqBusinessSerialNumberList req) throws Exception {
		RespPaging<RespBusinessSerialNumberList> respPaging = new RespPaging<RespBusinessSerialNumberList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespBusinessSerialNumberList> list = businessSerialNumberMapper.businessSerialNumberList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespBusinessSerialNumberList>(list), respPaging);
		for (RespBusinessSerialNumberList sn : respPaging.getList()) {
			sn.setUseStatus(EnumUtil.likeValueOf(ConstBusinessSnCodeUseStatus.class, sn.getUseStatus()).getText());
		}
		return respPaging;
	}
	
	/**
	 * @Description: 内容商激活码导入
	 * @Author wanbo
	 * @DateTime 2019/12/03
	 */
	@Override
	@Transactional
	public void importBusinessSerialNumber(ReqImportBusinessSerialNumber req) throws Exception {
		Business business = businessMapper.getBusiness(BeanUtil.beanToMap(req));
		if(business==null)
			throw LxException.of().setMsg("内容商不存在");
		
		BusinessSerialNumber businessSerialNumber = businessSerialNumberMapper.getBusinessSerialNumber(BeanUtil.beanToMap(req));
		
		if(businessSerialNumber==null) {
			businessSerialNumber = new BusinessSerialNumber()
					.setBusinessSerialNumberId(IdUtil.fastSimpleUUID())
					.setBusinessSnCode(req.getBusinessSnCode())
					.setCallType(req.getCallType())
					.setMaxUseNumber(req.getMaxUseNumber())
					.setBusinessId(business.getBusinessId())
					.setRemark(req.getRemark());
			businessSerialNumberMapper.insertSelective(businessSerialNumber);			
		}else {
			if(businessSerialNumber.getUseNumber()>=req.getMaxUseNumber()) 
				throw LxException.of().setMsg("激活码已达到最大使用次数");
			businessSerialNumber.setUseStatus(ConstBusinessSnCodeUseStatus.NO_USE.name());
			businessSerialNumberMapper.updateByPrimaryKeySelective(businessSerialNumber);
		}
		//删除获取空激活码的缓存
		redisTemplate.delete(ConstCacheKey.BUSINESS_SN_CODE_GET_NULL_COUNT.cacheKey(business.getBusinessCode()));
		//添加到缓存
		stringRedisTemplate.opsForSet().add(ConstCacheKey.BUSINESS_SN_CODE.cacheKey(business.getBusinessCode(),req.getCallType()), businessSerialNumber.getBusinessSnCode());
		
	}
	
	/**
	 * @Description: 删除内容商激活码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeBusinessSerialNumber(List<ReqRemoveBusinessSerialNumber> list) throws Exception {
		for (ReqRemoveBusinessSerialNumber reqRemoveBusinessSerialNumber : list) {
			BusinessSerialNumber businessSerialNumber = businessSerialNumberMapper.selectByPrimaryKey(reqRemoveBusinessSerialNumber.getBusinessSerialNumberId());
			Business business = businessMapper.selectByPrimaryKey(businessSerialNumber.getBusinessId());
			if(!ConstBusinessSnCodeUseStatus.SUCCESS.name().equals(businessSerialNumber.getUseStatus())) {
				businessSerialNumberMapper.removeBusinessSerialNumber(list);
				stringRedisTemplate.opsForSet().remove(ConstCacheKey.BUSINESS_SN_CODE.cacheKey(business.getBusinessCode(),businessSerialNumber.getCallType()),businessSerialNumber.getBusinessSnCode());
				//删除激活失败次数的缓存
				redisTemplate.delete(ConstCacheKey.BUSINESS_SN_CODE_USE_FAIL_COUNT.cacheKey(business.getBusinessCode()));
			}
		}
	}
	
	/**
	 * @Description: 修复内容商激活码
	 * @Author wanbo
	 * @DateTime 2020/03/11
	 */
	@Override
	public void restoreSn(List<ReqRestoreSn> list) throws Exception {
		for (ReqRestoreSn reqRestoreSn : list) {
			BusinessSerialNumber businessSerialNumber = businessSerialNumberMapper.selectByPrimaryKey(reqRestoreSn.getBusinessSerialNumberId());
			Business business = businessMapper.selectByPrimaryKey(businessSerialNumber.getBusinessId());
			if(!ConstBusinessSnCodeUseStatus.SUCCESS.name().equals(businessSerialNumber.getUseStatus())) {
				businessSerialNumber.setUseStatus(ConstBusinessSnCodeUseStatus.NO_USE.name());
				businessSerialNumberMapper.updateByPrimaryKeySelective(businessSerialNumber);
				//添加到缓存
				stringRedisTemplate.opsForSet().add(ConstCacheKey.BUSINESS_SN_CODE.cacheKey(business.getBusinessCode(),businessSerialNumber.getCallType()), businessSerialNumber.getBusinessSnCode());
				//删除获取空激活码的缓存
				redisTemplate.delete(ConstCacheKey.BUSINESS_SN_CODE_GET_NULL_COUNT.cacheKey(business.getBusinessCode()));
				//删除激活失败次数的缓存
				redisTemplate.delete(ConstCacheKey.BUSINESS_SN_CODE_USE_FAIL_COUNT.cacheKey(business.getBusinessCode()));
			}
		}
	}
	
	/**
	 * @Description: 获取内容商激活码
	 * @Author wanbo
	 * @DateTime 2020/03/02
	 */
	@Override
	public List<RespGetBusinessSn> getBusinessSn(String packageName,String deviceId,List<ReqGetBusinessSn> req) throws Exception {
		List<RespGetBusinessSn> businessSns = new ArrayList<ReqGetBusinessSn.RespGetBusinessSn>();
		for (ReqGetBusinessSn reqGetBusinessSn : req) {
			Business business = businessMapper.getBusiness(BeanUtil.beanToMap(reqGetBusinessSn));
			if(business==null) throw LxException.of().setMsg("内容商错误");
			
			RespGetBusinessSn respGetBusinessSn  = new RespGetBusinessSn();
			BeanUtil.copyProperties(reqGetBusinessSn, respGetBusinessSn);
			
			if(reqGetBusinessSn.getCallType().equals(ConstBusinessSnCallType.APK.name())) {
				//获取此设备之前激活成功的激活码
				String useSuccessSnCode = deviceAppBusinessSnCodeMapper.getUseSuccessDeviceAppBusinessSnCode(reqGetBusinessSn.getBusinessCode(),packageName, deviceId);
				if(StringUtils.isNotBlank(useSuccessSnCode)) {
					respGetBusinessSn.setBusinessSnCode(useSuccessSnCode);
					respGetBusinessSn.setIsOld(true);
				}
				//获取缓存的激活码
				if(StringUtils.isBlank(respGetBusinessSn.getBusinessSnCode())) {
					respGetBusinessSn.setBusinessSnCode(stringRedisTemplate.opsForSet().pop(ConstCacheKey.BUSINESS_SN_CODE.cacheKey(reqGetBusinessSn.getBusinessCode(),reqGetBusinessSn.getCallType())));
					respGetBusinessSn.setIsOld(false);
					//更新数据库激活码状态为使用中
					if(StringUtils.isNotBlank(respGetBusinessSn.getBusinessSnCode())) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("businessCode", reqGetBusinessSn.getBusinessCode());
						map.put("businessSnCode", respGetBusinessSn.getBusinessSnCode());
						BusinessSerialNumber businessSerialNumber = businessSerialNumberMapper.getBusinessSerialNumber(map);
						if(BeanUtil.isNotEmpty(businessSerialNumber)) {
							businessSerialNumber.setUseStatus(ConstBusinessSnCodeUseStatus.USEING.name());
							businessSerialNumberMapper.updateByPrimaryKeySelective(businessSerialNumber);							
						}
					}
				}
			}
			
			//SDK类型直接获取缓存
			if(reqGetBusinessSn.getCallType().equals(ConstBusinessSnCallType.SDK.name())) {
				respGetBusinessSn.setBusinessSnCode(stringRedisTemplate.opsForSet().randomMember(ConstCacheKey.BUSINESS_SN_CODE.cacheKey(reqGetBusinessSn.getBusinessCode(),reqGetBusinessSn.getCallType())));
			}
			
			//获取内容商激活码为空的次数达到预设值则发送短信提醒
			if(StringUtils.isBlank(respGetBusinessSn.getBusinessSnCode())) {
				Integer failCount = redisTemplate.opsForValue().get(ConstCacheKey.BUSINESS_SN_CODE_GET_NULL_COUNT.cacheKey(reqGetBusinessSn.getBusinessCode()));
				failCount = failCount==null?1:failCount + 1;
				redisTemplate.opsForValue().set(ConstCacheKey.BUSINESS_SN_CODE_GET_NULL_COUNT.cacheKey(reqGetBusinessSn.getBusinessCode()), failCount,Duration.ofSeconds(ConstCacheKey.BUSINESS_SN_CODE_GET_NULL_COUNT.getExpire()));
				//发送短信提醒
				if(failCount==constant.getMaxFailCount()) {
					App app = appMapper.getApp(MapUtil.builder("packageName", packageName).build());
					smsUtil.sendSmsWarning(constant.getAdminPhone(), app.getAppName(), DateUtil.formatChineseDate(new Date(), false), "内容商激活码不足");
				}
				throw LxException.of().setMsg("内容商激活码不足");
			}
			
			businessSns.add(respGetBusinessSn);
		}
		return businessSns;
	}
	
	/**
	 * @Description: 内容商激活码使用状态
	 * @Author wanbo
	 * @DateTime 2020/01/10
	 */
	@Transactional
	@Override
	public void updBusinessSnUseStatus(String packageName,String versionCode,String deviceId,ReqUpdBusinessSnUseStatus req) throws Exception {
		String useSuccessSnCode = deviceAppBusinessSnCodeMapper.getUseSuccessDeviceAppBusinessSnCode(req.getBusinessCode(),packageName, deviceId);
		//如果已经是激活成功的则不执行任何操作
		if(StringUtils.isNotBlank(useSuccessSnCode)) {
			return;
		}
		AppVersion appVersion = appVersionMapper.getAppVersion(MapUtil.builder(new HashMap<String, Object>()).put("packageName", packageName).put("versionCode", versionCode).build());
		if(appVersion==null)
			throw LxException.of().setMsg("版本信息错误");
		
		DeviceApp deviceApp = deviceAppMapper.getDeviceApp(MapUtil.builder().put("deviceId", deviceId).put("appId", appVersion.getAppId()).build());
		if(deviceApp==null)
			throw LxException.of().setMsg("设备产品信息错误");
		
		BusinessSerialNumber businessSerialNumber = businessSerialNumberMapper.getBusinessSerialNumber(BeanUtil.beanToMap(req));
		if(businessSerialNumber==null)
			throw LxException.of().setMsg("内容商激活码错误");
		if(businessSerialNumber.getUseNumber()>=businessSerialNumber.getMaxUseNumber()) 
			throw LxException.of().setMsg("内容商激活码已达到最大使用次数");
		//未使用则不记录使用记录-恢复缓存激活码
		if(ConstBusinessSnCodeUseStatus.NO_USE.name().equals(req.getUseStatus())) {
			List<ReqRestoreSn> reqRestoreSns = CollUtil.newArrayList(new ReqRestoreSn().setBusinessSerialNumberId(businessSerialNumber.getBusinessSerialNumberId()));
			restoreSn(reqRestoreSns);
			return;
		}
		
		//更新SN码使用次数(成功才记录使用次数)
		if(ConstBusinessSnCodeUseStatus.SUCCESS.name().equals(req.getUseStatus())) {
			businessSerialNumber.setUseNumber(businessSerialNumber.getUseNumber()+1);
		}
		businessSerialNumber.setUseStatus(req.getUseStatus());
		businessSerialNumberMapper.updateByPrimaryKeySelective(businessSerialNumber);
		
		//新增内容商激活码设备产品记录
		deviceAppBusinessSnCodeMapper.insertSelective(
				new DeviceAppBusinessSnCode()
				.setUnionId(IdUtil.fastSimpleUUID())
				.setDeviceAppId(deviceApp.getDeviceAppId())
				.setBusinessSerialNumberId(businessSerialNumber.getBusinessSerialNumberId())
				.setUseStatus(req.getUseStatus())
				);
		
		//激活错误次数达到预设值则发送短信提醒
		if(ConstBusinessSnCodeUseStatus.FAIL.name().equals(req.getUseStatus())) {
			Integer failCount = redisTemplate.opsForValue().get(ConstCacheKey.BUSINESS_SN_CODE_USE_FAIL_COUNT.cacheKey(req.getBusinessCode()));
			failCount = failCount==null?1:failCount + 1;
			redisTemplate.opsForValue().set(ConstCacheKey.BUSINESS_SN_CODE_USE_FAIL_COUNT.cacheKey(req.getBusinessCode()), failCount,Duration.ofSeconds(ConstCacheKey.BUSINESS_SN_CODE_USE_FAIL_COUNT.getExpire()));
			//发送短信提醒
			if(failCount==constant.getMaxFailCount()) {
				App app = appMapper.getApp(MapUtil.builder("packageName", packageName).build());
				smsUtil.sendSmsWarning(constant.getAdminPhone(), app.getAppName(), DateUtil.formatChineseDate(new Date(), false), "内容商激活码失败次数过多");
			}
		}
		
	}

}
