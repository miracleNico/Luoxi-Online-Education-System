package com.luoxi.server.service;


import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.thirdBusiness.IThirdBusinessService;
import com.luoxi.api.thirdBusiness.protocol.ReqThirdBusinessList;
import com.luoxi.api.thirdBusiness.protocol.ReqThirdBusinessList.RespThirdBusinessList;
import com.luoxi.base.RespPaging;
import com.luoxi.server.dao.ThirdBusinessMapper;

import cn.hutool.core.bean.BeanUtil;

@DubboService
public class ThirdBusinessService implements IThirdBusinessService{
	
	@Autowired
	private ThirdBusinessMapper thirdBusinessMapper;
	
	@Override
	public RespPaging<RespThirdBusinessList> thirdBusinessList(ReqThirdBusinessList req) throws Exception {
		RespPaging<RespThirdBusinessList> respPaging = new RespPaging<RespThirdBusinessList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespThirdBusinessList> list = thirdBusinessMapper.thirdBusinessList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespThirdBusinessList>(list), respPaging);
		return respPaging;
	}
	
}
