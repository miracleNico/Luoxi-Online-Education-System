package com.luoxi.server.service;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.openAppBusiness.IOpenAppBusinessService;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessKey.RespOpenAppBusinessKey;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessList;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessList.RespOpenAppBusinessList;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessRemove;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessSave;
import com.luoxi.base.RespPaging;
import com.luoxi.exception.LxException;
import com.luoxi.model.OpenAppBusiness;
import com.luoxi.server.dao.OpenAppBusinessMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@DubboService
public class OpenAppBusinessService implements IOpenAppBusinessService{
	
	@Autowired
	private OpenAppBusinessMapper openAppBusinessMapper;
	
	@Override
	public OpenAppBusiness getOpenAppBusinessByName(String appId,String businessName) throws Exception{
		OpenAppBusiness openAppBusiness = openAppBusinessMapper.selectOne(new OpenAppBusiness().setAppId(appId).setBusinessName(businessName));
		if(BeanUtil.isEmpty(openAppBusiness)) throw LxException.of().setMsg("业务名称不存在");
		return openAppBusiness;
	}
	
	@Override
	public OpenAppBusiness getOpenAppBusiness(String appId, String businessCode) throws Exception {
		OpenAppBusiness openAppBusiness = openAppBusinessMapper.selectOne(new OpenAppBusiness().setAppId(appId).setBusinessCode(businessCode));
		if(BeanUtil.isEmpty(openAppBusiness)) throw LxException.of().setMsg("暂未配置此业务信息:"+businessCode);
		return openAppBusiness;
	}
	
	@Override
	public RespOpenAppBusinessKey getOpenAppBusinessKey(String appId, String businessCode) throws Exception {
		return BeanUtil.copyProperties(getOpenAppBusiness(appId, businessCode), RespOpenAppBusinessKey.class);
	}
	
	@Override
	public void checkOpenAppBusiness(String appId, String businessCode) throws Exception {
		getOpenAppBusiness(appId, businessCode);
	}
	
	@Override
	public RespPaging<RespOpenAppBusinessList> appBusinessList(ReqOpenAppBusinessList req) throws Exception {
		RespPaging<RespOpenAppBusinessList> respPaging = new RespPaging<RespOpenAppBusinessList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespOpenAppBusinessList> list = openAppBusinessMapper.appBusinessList(req);
		BeanUtil.copyProperties(new PageInfo<RespOpenAppBusinessList>(list), respPaging);
		return respPaging;
	}
	
	@Override
	public OpenAppBusiness info(String appBusinessId) throws Exception {
		return openAppBusinessMapper.selectByPrimaryKey(appBusinessId);
	}
	
	@Override
	public void saveAppBusiness(ReqOpenAppBusinessSave req) throws Exception {
		OpenAppBusiness openAppBusinessCode = openAppBusinessMapper.selectOne(new OpenAppBusiness().setChannelId(req.getChannelId()).setAppId(req.getAppId()).setBusinessCode(req.getBusinessCode()));
		OpenAppBusiness openAppBusinessName = openAppBusinessMapper.selectOne(new OpenAppBusiness().setChannelId(req.getChannelId()).setAppId(req.getAppId()).setBusinessName(req.getBusinessName()));
		if(StrUtil.isBlank(req.getOpenAppBusinessId())) {
			if(BeanUtil.isNotEmpty(openAppBusinessCode)) throw LxException.of().setMsg("业务代码已存在");
			if(BeanUtil.isNotEmpty(openAppBusinessName)) throw LxException.of().setMsg("业务名称已存在");
			OpenAppBusiness openAppBusiness = BeanUtil.copyProperties(req, OpenAppBusiness.class).setOpenAppBusinessId(IdUtil.fastSimpleUUID());
			openAppBusinessMapper.insertSelective(openAppBusiness);
		}else {
			OpenAppBusiness db =  openAppBusinessMapper.selectByPrimaryKey(req.getOpenAppBusinessId());
			if(BeanUtil.isNotEmpty(openAppBusinessCode) && !StrUtil.equals(db.getOpenAppBusinessId(), openAppBusinessCode.getOpenAppBusinessId())) throw LxException.of().setMsg("业务代码已存在");
			if(BeanUtil.isNotEmpty(openAppBusinessName) && !StrUtil.equals(db.getOpenAppBusinessId(), openAppBusinessName.getOpenAppBusinessId())) throw LxException.of().setMsg("业务名称已存在");
			BeanUtil.copyProperties(req, db, CopyOptions.create().ignoreNullValue());
			openAppBusinessMapper.updateByPrimaryKeySelective(db);
		}
	}
	
	@Override
	public void removeAppBusiness(List<ReqOpenAppBusinessRemove> req) throws Exception {
		openAppBusinessMapper.removeAppBusiness(req);
	}
	
	@Override
	public List<OpenAppBusiness> appBusinessSelected(String appId) throws Exception {
		return openAppBusinessMapper.appBusinessSelected(appId);
	}
	
}
