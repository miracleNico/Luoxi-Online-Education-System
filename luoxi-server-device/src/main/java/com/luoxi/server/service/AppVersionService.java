package com.luoxi.server.service;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.app.IAppService;
import com.luoxi.api.version.IAppVersionService;
import com.luoxi.api.version.protocol.ReqAppVersionInfo;
import com.luoxi.api.version.protocol.ReqAppVersionInfo.RespAppVersionInfo;
import com.luoxi.api.version.protocol.ReqAppVersionList;
import com.luoxi.api.version.protocol.ReqAppVersionList.RespAppVersionList;
import com.luoxi.api.version.protocol.ReqRemoveAppVersion;
import com.luoxi.api.version.protocol.ReqSaveAppVersion;
import com.luoxi.api.version.protocol.ReqSaveAppVersion.RespSaveAppVersion;
import com.luoxi.api.version.vo.AppVersionVo;
import com.luoxi.base.RespPaging;
import com.luoxi.base.ResultMessage;
import com.luoxi.constant.ConstUrlType;
import com.luoxi.constant.ConstVersionType;
import com.luoxi.exception.LxException;
import com.luoxi.model.App;
import com.luoxi.model.AppVersion;
import com.luoxi.server.dao.AppMapper;
import com.luoxi.server.dao.AppVersionMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;

@DubboService
public class AppVersionService implements IAppVersionService{

	@Autowired
	private AppVersionMapper appVersionMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
	private AppMapper appMapper;
	@Autowired
	private IAppService appService;
	
	@Override
	public AppVersionVo getAppVersion(String packageName, String versionCode)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("packageName", packageName);
		map.put("versionCode", versionCode);
		AppVersion appVersion = appVersionMapper.getAppVersion(map);
		if(BeanUtil.isEmpty(appVersion))
			throw LxException.of().setMsg("版本信息错误");
		AppVersionVo appVersionVo = new AppVersionVo();
		BeanUtil.copyProperties(appVersion, appVersionVo);
		return appVersionVo;
	}
	
	/**
	 * @Description: 版本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespAppVersionList> appVersionList(ReqAppVersionList req) throws Exception {
		RespPaging<RespAppVersionList> respPaging = new RespPaging<RespAppVersionList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespAppVersionList> list = appVersionMapper.appVersionList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespAppVersionList>(list), respPaging);
		for (RespAppVersionList resp : respPaging.getList()) {
			if(resp.getUrlType().equals(ConstUrlType._0.getVal()))
				resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
			if(resp.getFileSize()!=null)
				resp.setFileSize(resp.getFileSize().divide(BigDecimal.valueOf(1024*1024)).setScale(2, BigDecimal.ROUND_HALF_UP));
			resp.setVersionTypeName(ConstVersionType.getText(resp.getVersionType()));
		}
		return respPaging;
	}

	/**
	 * @Description: 版本信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespAppVersionInfo appVersionInfo(ReqAppVersionInfo req) throws Exception {
		RespAppVersionInfo resp = appVersionMapper.appVersionInfo(BeanUtil.beanToMap(req));
		if(resp!=null) {
			if(resp.getUrlType().equals(ConstUrlType._0.getVal()))
				resp.setUrl(cosUtil.getAccessUrl(resp.getUrl()));
			resp.setVersionTypeName(ConstVersionType.getText(resp.getVersionType()));
		}
		return resp;
	}

	/**
	 * @Description: 保存版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespSaveAppVersion saveAppVersion(ReqSaveAppVersion req) throws Exception {
		AppVersion appVersion = new AppVersion();
		BeanUtil.copyProperties(req, appVersion);
		
		appVersion.setUrlType(appVersion.getUrl().contains("myqcloud.com")?ConstUrlType._0.getVal():ConstUrlType._1.getVal());
		if(appVersion.getUrlType().equals(ConstUrlType._0.getVal())) {
			appVersion.setUrl(cosUtil.filterUrlDomain(appVersion.getUrl()));			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("versionCode", req.getVersionCode());
		map.put("appId", req.getAppId());
		AppVersion dbAppVersion = appVersionMapper.getAppVersion(map);
		
		if(StringUtils.isNotBlank(appVersion.getVersionId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbAppVersion) && !dbAppVersion.getVersionId().equals(appVersion.getVersionId()))
				throw LxException.of().setMsg("版本号已存在");
			if(BeanUtil.isEmpty(dbAppVersion) || dbAppVersion.getVersionId().equals(appVersion.getVersionId())) {
				appVersionMapper.updateByPrimaryKeySelective(appVersion);
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbAppVersion)) throw LxException.of().setMsg("版本号已存在");
			
			appVersion.setVersionId(IdUtil.fastSimpleUUID());
			appVersionMapper.insertSelective(appVersion);
		}
		
		App app = appMapper.selectByPrimaryKey(appVersion.getAppId());
		appService.clearAppUpgradeCache(app.getPackageName());
		
		RespSaveAppVersion resp = new RespSaveAppVersion();
		BeanUtil.copyProperties(appVersion, resp);
		return resp;
	}

	/**
	 * @Description: 删除版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeAppVersion(List<ReqRemoveAppVersion> req) throws Exception {
		for (ReqRemoveAppVersion reqRemoveAppVersion : req) {
			AppVersion appVersion = appVersionMapper.selectByPrimaryKey(reqRemoveAppVersion.getVersionId());
			App app = appMapper.selectByPrimaryKey(appVersion.getAppId());
			appService.clearAppUpgradeCache(app.getPackageName());
		}
		appVersionMapper.removeAppVersion(req);
	}
	
	
	
	
	
	
	
	
}
