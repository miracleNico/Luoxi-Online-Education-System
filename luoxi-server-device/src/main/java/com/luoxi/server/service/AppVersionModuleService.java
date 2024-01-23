package com.luoxi.server.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.api.versionModule.IAppVersionModuleService;
import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleInfo;
import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleInfo.RespAppVersionModuleInfo;
import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleList;
import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleList.RespAppVersionModuleList;
import com.luoxi.api.versionModule.protocol.ReqRemoveAppVersionModule;
import com.luoxi.api.versionModule.protocol.ReqSaveAppVersionModule;
import com.luoxi.api.versionModule.protocol.ReqUpdAppVersionModuleParent;
import com.luoxi.api.versionModule.protocol.ReqViewAppVersionModule.RespViewAppVersionModule;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;
import com.luoxi.model.AppVersionModule;
import com.luoxi.server.dao.AppVersionModuleMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@DubboService
public class AppVersionModuleService implements IAppVersionModuleService{

	@Autowired
	private AppVersionModuleMapper appVersionModuleMapper;
	@Autowired
	private CosUtil cosUtil;
	
	/**
	 * @Description: 展示产品模块
	 * @Author wanbo
	 * @DateTime 2020/03/06
	 */
	@Override
	public RespViewAppVersionModule viewAppVersionModule(String packageName, String versionCode) throws Exception {
		RespViewAppVersionModule resp = appVersionModuleMapper.viewAppVersionModule(packageName, versionCode);
		if(resp!=null) {
			if(StringUtils.isNotBlank(resp.getModuleIcon())) {
				resp.setModuleIcon(cosUtil.getAccessUrl(resp.getModuleIcon()));
			}
			if(StringUtils.isNotBlank(resp.getBusinessAppsUrl())) {
				List<String> businessAppsUrls = StrUtil.split(resp.getBusinessAppsUrl(), ',');
				for(String appUrl : businessAppsUrls) {
					appUrl = cosUtil.getAccessUrl(appUrl);
				}
				resp.setBusinessAppsUrl(StrUtil.join(",", businessAppsUrls));
			}
		}
		return resp;
	}
	
	/**
	 * @Description: 版本模块列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public List<RespAppVersionModuleList> appVersionModuleList(ReqAppVersionModuleList req) throws Exception {
		List<RespAppVersionModuleList> list = appVersionModuleMapper.appVersionModuleList(BeanUtil.beanToMap(req));
		return list;
	}

	/**
	 * @Description: 版本模块信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespAppVersionModuleInfo appVersionModuleInfo(ReqAppVersionModuleInfo req) throws Exception {
		RespAppVersionModuleInfo resp = appVersionModuleMapper.appVersionModuleInfo(BeanUtil.beanToMap(req));
		if(StringUtils.isNoneBlank(resp.getModuleIcon())) {
			resp.setModuleIcon(cosUtil.getAccessUrl(resp.getModuleIcon()));			
		}
		return resp;
	}

	/**
	 * @Description: 保存版本模块
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void saveAppVersionModule(ReqSaveAppVersionModule req) throws Exception {
		AppVersionModule versionModule = new AppVersionModule();
		BeanUtil.copyProperties(req, versionModule);
		if(StringUtils.isNoneBlank(versionModule.getModuleIcon())) {
			versionModule.setModuleIcon(cosUtil.filterUrlDomain(versionModule.getModuleIcon()));			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("versionId", req.getVersionId());
		map.put("moduleName", req.getModuleName());
		AppVersionModule dbAppVersionModule = appVersionModuleMapper.getAppVersionModule(map);
		
		if(StringUtils.isNotBlank(versionModule.getVersionModuleId())) {
			//修改操作
			if(dbAppVersionModule!=null && !dbAppVersionModule.getVersionModuleId().equals(versionModule.getVersionModuleId()))
				throw LxException.of().setMsg("名称已存在");
			if(dbAppVersionModule==null || dbAppVersionModule.getVersionModuleId().equals(versionModule.getVersionModuleId())) {
				appVersionModuleMapper.updateByPrimaryKeySelective(versionModule);
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbAppVersionModule))
				throw LxException.of().setMsg("名称已存在");
			versionModule.setVersionModuleId(IdUtil.fastSimpleUUID());
			appVersionModuleMapper.insertSelective(versionModule);
		}
		
	}

	/**
	 * @Description: 删除版本模块
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeAppVersionModule(List<ReqRemoveAppVersionModule> req) throws Exception {
		appVersionModuleMapper.removeAppVersionModule(req);
	}
	
	
	/**
	 * @Description: 修改父级节点
	 * @Author wanbo
	 * @DateTime 2020/02/27
	 */
	@Override
	public void updAppVersionModuleParent(List<ReqUpdAppVersionModuleParent> req) throws Exception {
		for (ReqUpdAppVersionModuleParent o : req) {
			if(StringUtils.isEmpty(o.getParentId())) {
				o.setParentId("");
			}
			appVersionModuleMapper.updAppVersionModuleParent(o.getVersionModuleId(), o.getParentId());			
		}
	}
	
	
	
	
	
}
