package com.luoxi.server.service;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.userScoreDetail.IUserScoreDetailService;
import com.luoxi.api.userScoreDetail.protocol.ReqAppUserScoreDetail;
import com.luoxi.api.userScoreDetail.protocol.ReqAppUserScoreDetail.RespAppUserScoreDetail;
import com.luoxi.api.userScoreDetail.protocol.ReqUserScoreDetailList;
import com.luoxi.api.userScoreDetail.protocol.ReqUserScoreDetailList.RespUserScoreDetailList;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.ConstUserScoreChangeType;
import com.luoxi.server.dao.UserScoreDetailMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.EnumUtil;

@DubboService
public class UserScoreDetailService implements IUserScoreDetailService{

	@Autowired
	private UserScoreDetailMapper userScoreDetailMapper;
	
	@Override
	public RespPaging<RespAppUserScoreDetail> appUserScoreDetail(ReqAppUserScoreDetail req) throws Exception {
		RespPaging<RespAppUserScoreDetail> respPaging = new RespPaging<RespAppUserScoreDetail>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespAppUserScoreDetail> list = userScoreDetailMapper.appUserScoreDetail(req);
		BeanUtil.copyProperties(new PageInfo<RespAppUserScoreDetail>(list),respPaging);
		return respPaging;
	}
	
	@Override
	public RespPaging<RespUserScoreDetailList> userScoreDetailList(ReqUserScoreDetailList req) throws Exception {
		RespPaging<RespUserScoreDetailList> respPaging = new RespPaging<RespUserScoreDetailList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespUserScoreDetailList> list = userScoreDetailMapper.userScoreDetailList(req);
		BeanUtil.copyProperties(new PageInfo<RespUserScoreDetailList>(list),respPaging);
		for (RespUserScoreDetailList respUserScoreDetailList : respPaging.getList()) {
			respUserScoreDetailList.setChangeType(EnumUtil.likeValueOf(ConstUserScoreChangeType.class, respUserScoreDetailList.getChangeType()).getText());
		}
		return respPaging;
	}
}
