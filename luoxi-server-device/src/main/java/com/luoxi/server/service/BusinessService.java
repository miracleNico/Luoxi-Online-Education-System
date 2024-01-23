package com.luoxi.server.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.business.IBusinessService;
import com.luoxi.api.business.protocol.ReqBusinessInfo;
import com.luoxi.api.business.protocol.ReqBusinessInfo.RespBusinessInfo;
import com.luoxi.api.business.protocol.ReqBusinessList;
import com.luoxi.api.business.protocol.ReqBusinessList.RespBusinessList;
import com.luoxi.api.business.protocol.ReqRemoveBusiness;
import com.luoxi.api.business.protocol.ReqSaveBusiness;
import com.luoxi.api.business.protocol.ReqSaveBusiness.RespSaveBusiness;
import com.luoxi.base.RespPaging;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;
import com.luoxi.model.Business;
import com.luoxi.server.dao.BusinessMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;

@DubboService
public class BusinessService implements IBusinessService{

	@Autowired
	private BusinessMapper businessMapper;
	
	/**
	 * @Description: 内容商列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespBusinessList> businessList(ReqBusinessList req) throws Exception {
		RespPaging<RespBusinessList> respPaging = new RespPaging<RespBusinessList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespBusinessList> list = businessMapper.businessList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespBusinessList>(list), respPaging);
		return respPaging;
	}

	/**
	 * @Description: 内容商信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespBusinessInfo businessInfo(ReqBusinessInfo req) throws Exception {
		RespBusinessInfo resp = businessMapper.businessInfo(BeanUtil.beanToMap(req));
		return resp;
	}

	/**
	 * @Description: 保存内容商
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespSaveBusiness saveBusiness(ReqSaveBusiness req) throws Exception {
		Business business = new Business();
		BeanUtil.copyProperties(req, business);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessCode", req.getBusinessCode());
		Business dbBusiness = businessMapper.getBusiness(map);
		
		if(StringUtils.isNotBlank(business.getBusinessId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbBusiness) && !dbBusiness.getBusinessId().equals(business.getBusinessId())) 
				throw LxException.of().setMsg("内容商代码已存在");
			if(BeanUtil.isEmpty(dbBusiness) || dbBusiness.getBusinessId().equals(business.getBusinessId())) {
				businessMapper.updateByPrimaryKeySelective(business);				
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbBusiness))
				throw LxException.of().setMsg("内容商代码已存在");
			business.setBusinessId(IdUtil.fastSimpleUUID());
			businessMapper.insertSelective(business);
		}
		
		RespSaveBusiness resp = new RespSaveBusiness();
		BeanUtil.copyProperties(business, resp);
		return resp;
	}

	/**
	 * @Description: 删除内容商
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeBusiness(List<ReqRemoveBusiness> req) throws Exception {
		businessMapper.removeBusiness(req);
	}
	
	
	
	
	
	
	
	
}
