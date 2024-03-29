package com.luoxi.server.service;


import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.channelEarningsDetail.IChannelEarningsDetailService;
import com.luoxi.api.channelEarningsDetail.protocol.ReqChannelEarningsDetailList;
import com.luoxi.api.channelEarningsDetail.protocol.ReqChannelEarningsDetailList.RespChannelEarningsDetailList;
import com.luoxi.base.RespPaging;
import com.luoxi.server.dao.ChannelEarningsDetailMapper;

import cn.hutool.core.bean.BeanUtil;

@DubboService
public class ChannelEarningsDetailService implements IChannelEarningsDetailService{
	
	@Autowired
	private ChannelEarningsDetailMapper channelEarningsDetailMapper;
	
	@Override
	public RespPaging<RespChannelEarningsDetailList> channelEarningsDetailList(ReqChannelEarningsDetailList req) throws Exception {
		RespPaging<RespChannelEarningsDetailList> respPaging = new RespPaging<RespChannelEarningsDetailList>();
		Map<String, Object> reqMap = BeanUtil.beanToMap(req);
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespChannelEarningsDetailList> list = channelEarningsDetailMapper.channelEarningsDetailList(reqMap);
		BeanUtil.copyProperties(new PageInfo<RespChannelEarningsDetailList>(list), respPaging);
		Map<String, Object> mapSum = channelEarningsDetailMapper.channelEarningsDetailListSum(reqMap);
		respPaging.setExtra(mapSum);
		return respPaging;
	}
	
	
}
