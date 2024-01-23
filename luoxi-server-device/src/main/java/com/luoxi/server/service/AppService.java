package com.luoxi.server.service;


import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.app.IAppService;
import com.luoxi.api.app.protocol.ReqAppInfo;
import com.luoxi.api.app.protocol.ReqAppInfo.RespAppInfo;
import com.luoxi.api.app.protocol.ReqAppList;
import com.luoxi.api.app.protocol.ReqAppList.RespAppList;
import com.luoxi.api.app.protocol.ReqEnableSn;
import com.luoxi.api.app.protocol.ReqRemoveApp;
import com.luoxi.api.app.protocol.ReqSaveApp;
import com.luoxi.api.app.vo.AppEarningsSettingVo;
import com.luoxi.api.app.vo.AppKeyVo;
import com.luoxi.api.app.vo.AppVo;
import com.luoxi.api.upgradePlan.protocol.ReqUpgrade;
import com.luoxi.api.upgradePlan.protocol.ReqUpgrade.RespUpgrade;
import com.luoxi.base.RespPaging;
import com.luoxi.base.ResultMessage;
import com.luoxi.constant.ConstAppType;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.constant.ConstDeviceAuthType;
import com.luoxi.constant.ConstUrlType;
import com.luoxi.exception.LxException;
import com.luoxi.model.App;
import com.luoxi.model.AppEarningsSetting;
import com.luoxi.server.dao.AppEarningsSettingMapper;
import com.luoxi.server.dao.AppMapper;
import com.luoxi.util.redis.RedisHelper;
import com.luoxi.utils.CosUtil;
import com.luoxi.utils.LxKeyUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

@DubboService
public class AppService implements IAppService{
	
	@Autowired
	private AppMapper appMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private AppEarningsSettingMapper appEarningsSettingMapper;
	
	@Override
	public void clearAppUpgradeCache(String packageName) throws Exception{
		redisHelper.keys(ConstCacheKey.UPGRADE.cacheKey(packageName,"*")).forEach(key->{
			stringRedisTemplate.delete(key);
		});
	}
	
	@Override
	public List<AppVo> appVoList(String channelId) throws Exception{
		return appMapper.appVoList(channelId);
	}
	
	@Override
	public AppVo getAppByPackageName(String packageName) throws Exception{
		App app = appMapper.getApp(MapUtil.builder("packageName", packageName).build());
		if(app==null)throw LxException.of().setMsg("产品信息错误");
		AppVo appVo = new AppVo();
		BeanUtil.copyProperties(app, appVo);
		return appVo;
	}
	
	@Override
	public AppVo getAppByAppId(String appId) throws Exception {
		App app = appMapper.selectOne(new App().setAppId(appId).setStatus(true));
		if(app==null)throw LxException.of().setMsg("产品信息错误");
		AppVo appVo = new AppVo();
		BeanUtil.copyProperties(app, appVo);
		return appVo;
	}
	
	/**
	 * @Description: 产品列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespAppList> appList(ReqAppList req) throws Exception {
		RespPaging<RespAppList> respPaging = new RespPaging<RespAppList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespAppList> list = appMapper.appList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespAppList>(list), respPaging);
		for (RespAppList resp : respPaging.getList()) {
			resp.setAppTypeName(EnumUtil.likeValueOf(ConstAppType.class, resp.getAppType()).getText());
			resp.setAuthTypeName(EnumUtil.likeValueOf(ConstDeviceAuthType.class, resp.getAuthType()).getText());
		}
		return respPaging;
	}
	
	/**
	 * @Description: 产品信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespAppInfo appInfo(ReqAppInfo req) throws Exception {
		RespAppInfo resp = appMapper.appInfo(BeanUtil.beanToMap(req));
		if(resp!=null) {
			resp.setAppTypeName(EnumUtil.likeValueOf(ConstAppType.class, resp.getAppType()).getText());
			resp.setAuthTypeName(EnumUtil.likeValueOf(ConstDeviceAuthType.class, resp.getAuthType()).getText());
			resp.setAppEarningsSettings(appEarningsSettingMapper.appEarningsSettings(resp.getAppId()));
		}
		return resp;
	}
	
	/**
	 * @Description: 保存产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Transactional
	@Override
	public void saveApp(ReqSaveApp req) throws Exception {
		App app = new App();
		BeanUtil.copyProperties(req, app);
		
		App dbApp = appMapper.getApp(MapUtil.builder("packageName",req.getPackageName()).build());
		
		if(StringUtils.isNotBlank(app.getAppId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbApp) && !dbApp.getAppId().equals(app.getAppId()))
				throw LxException.of().setMsg("包名已存在");
			if(BeanUtil.isEmpty(dbApp) || dbApp.getAppId().equals(app.getAppId())) {
				app.setAppKey(null);//不要更新
				app.setAppSecret(null);//不要更新
				appMapper.updateByPrimaryKeySelective(app);
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbApp))
				throw LxException.of().setMsg("包名已存在");
			app.setAppId(IdUtil.fastSimpleUUID());
			app.setAppKey(LxKeyUtil.appKey());
			app.setAppSecret(LxKeyUtil.appSecret());
			appMapper.insertSelective(app);
			stringRedisTemplate.opsForHash().put(ConstCacheKey.APP_SECRET.cacheKey(), app.getAppId(), JSONUtil.toJsonStr(new AppKeyVo().setAppKey(app.getAppKey()).setAppSecret(app.getAppSecret())));
		}
		
		/*************************设置产品第三方内容商******************************/
		{
			List<AppEarningsSetting> dbList = appEarningsSettingMapper.appEarningsSettingList(app.getAppId());
			List<AppEarningsSettingVo> reqList = req.getAppEarningsSettings();
			Map<String, AppEarningsSetting> dbMap = dbList.stream().collect(Collectors.toMap(AppEarningsSetting::getThirdBusinessId,Function.identity()));
			Map<String, AppEarningsSettingVo> reqMap = reqList.stream().collect(Collectors.toMap(AppEarningsSettingVo::getThirdBusinessId,Function.identity()));
			
			dbList.forEach(dbAppEarningsSetting->{
				AppEarningsSettingVo reqAppEarningsSetting = reqMap.get(dbAppEarningsSetting.getThirdBusinessId());
				//删除
				if(BeanUtil.isEmpty(reqAppEarningsSetting)) {
					appEarningsSettingMapper.deleteByPrimaryKey(dbAppEarningsSetting);
				}
			});
			
			reqList.forEach(reqAppEarningsSetting->{
				AppEarningsSetting dbAppEarningsSetting = dbMap.get(reqAppEarningsSetting.getThirdBusinessId());
				if(BeanUtil.isEmpty(dbAppEarningsSetting)) {//新增
					dbAppEarningsSetting = new AppEarningsSetting();
					BeanUtil.copyProperties(reqAppEarningsSetting, dbAppEarningsSetting);
					dbAppEarningsSetting.setAppEarningsSettingId(IdUtil.fastSimpleUUID());
					dbAppEarningsSetting.setAppId(app.getAppId());
					appEarningsSettingMapper.insertSelective(dbAppEarningsSetting);
				}else {//修改
					BeanUtil.copyProperties(reqAppEarningsSetting, dbAppEarningsSetting,CopyOptions.create().ignoreNullValue());
					appEarningsSettingMapper.updateByPrimaryKeySelective(dbAppEarningsSetting);
				}
			});
		}
		
		//清空此产品升级缓存
		clearAppUpgradeCache(app.getPackageName());
	}
	
	/**
	 * @Description: 删除产品
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeApp(List<ReqRemoveApp> req) throws Exception {
		appMapper.removeApp(req);
		for (ReqRemoveApp reqRemoveApp : req) {
			stringRedisTemplate.opsForHash().delete(ConstCacheKey.APP_SECRET.cacheKey(), reqRemoveApp.getAppId());
			App app = appMapper.selectByPrimaryKey(reqRemoveApp.getAppId());
			clearAppUpgradeCache(app.getPackageName());
		}
	}
	
	@Override
	public RespUpgrade upgrade(ReqUpgrade requestParam, String versionCode, String packageName) throws Exception {
		RespUpgrade resp = appMapper.upgrade(requestParam.getDeviceId(),null,null,versionCode, packageName);
		if(BeanUtil.isEmpty(resp))
			throw LxException.of().setMsg("暂无升级");
		
		if(resp.getUrlType().equals(ConstUrlType._0.getVal())) 
			resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
		
		return resp;
	}
	
	
	/**
	 * @Description: 产品升级
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	@Override
	public RespUpgrade upgrade_v2(ReqUpgrade requestParam, String versionCode, String packageName) throws Exception {
		RespUpgrade resp = null;
		//key存在说明之前查询过数据库一次
		if(stringRedisTemplate.hasKey(ConstCacheKey.UPGRADE.cacheKey(packageName,versionCode,requestParam.getBrandName(),requestParam.getModelName()))) {
			resp = JSONUtil.toBean(stringRedisTemplate.opsForValue().get(ConstCacheKey.UPGRADE.cacheKey(packageName,versionCode,requestParam.getBrandName(),requestParam.getModelName())), RespUpgrade.class);
		}else {
			resp = appMapper.upgrade(null,requestParam.getBrandName(),requestParam.getModelName(), versionCode, packageName);
			stringRedisTemplate.opsForValue().set(ConstCacheKey.UPGRADE.cacheKey(packageName,versionCode,requestParam.getBrandName(),requestParam.getModelName()), BeanUtil.isEmpty(resp)?"{}":JSONUtil.toJsonStr(resp),Duration.ofSeconds(ConstCacheKey.UPGRADE.getExpire()));
		}
		if(resp==null || StrUtil.isBlank(resp.getUpgradePlanId())) throw LxException.of().setResult(ResultMessage.FAILURE_TIP_NOT_UPGRADE.result());
		
		if(resp.getUrlType().equals(ConstUrlType._0.getVal())) 
			resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
		
		return resp;
	}
	
	/**
	 * @Description: 启用激活码激活
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	@Override
	public void enableSn(ReqEnableSn req) throws Exception {
		App app = appMapper.selectByPrimaryKey(req.getAppId());
		if(app!=null) {
			app.setEnableSn(req.getEnableSn());
			appMapper.updateByPrimaryKeySelective(app);
		}else {
			throw LxException.of().setMsg("操作失败");			
		}
	}
	
	@Override
	public AppKeyVo appKey(String appId) {
		String cacheKey = ConstCacheKey.APP_SECRET.cacheKey();
		AppKeyVo appKeyVo = null;
		appKeyVo = BeanUtil.toBean(stringRedisTemplate.opsForHash().get(cacheKey,appId), AppKeyVo.class);
		
		if(StrUtil.isBlank(appKeyVo.getAppKey()) || StrUtil.isBlank(appKeyVo.getAppSecret())) {
			App app = appMapper.selectByPrimaryKey(appId);
			appKeyVo = new AppKeyVo().setAppKey(app.getAppKey()).setAppSecret(app.getAppSecret());
		}
		stringRedisTemplate.opsForHash().put(cacheKey, appId, JSONUtil.toJsonStr(appKeyVo));
		return appKeyVo;
	}
	
	
}
