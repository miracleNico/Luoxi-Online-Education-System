package com.luoxi.server.service;

import java.math.BigDecimal;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.api.userTargetPrize.IUserTargetPrizeService;
import com.luoxi.api.userTargetPrize.protocol.ReqSaveUserTargetPrize;
import com.luoxi.api.userTargetPrize.protocol.ReqUserTargetPrizeInfo.RespUserTargetPrizeInfo;
import com.luoxi.model.UserTargetPrize;
import com.luoxi.server.dao.UserTargetPrizeMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;

@DubboService
public class UserTargetPrizeService implements IUserTargetPrizeService{

	@Autowired
	private UserTargetPrizeMapper userTargetPrizeMapper;
	@Autowired
	private CosUtil cosUtil;
	
	@Override
	public void saveUserTargetProze(String userId, ReqSaveUserTargetPrize req) throws Exception {
		UserTargetPrize userTargetPrize = userTargetPrizeMapper.userTargetPrize(userId);
		if(BeanUtil.isEmpty(userTargetPrize)) {
			userTargetPrize = new UserTargetPrize().setUserTargetPrizeId(IdUtil.fastSimpleUUID()).setUserId(userId).setPrizeId(req.getPrizeId());
			userTargetPrizeMapper.insertSelective(userTargetPrize);
		}else {
			userTargetPrize.setPrizeId(req.getPrizeId());
			userTargetPrizeMapper.updateByPrimaryKeySelective(userTargetPrize);
		}
	}
	
	@Override
	public RespUserTargetPrizeInfo userTargetPrizeInfo(String userId) throws Exception {
		RespUserTargetPrizeInfo resp = userTargetPrizeMapper.userTargetPrizesInfo(userId);
		if(BeanUtil.isNotEmpty(resp)) {
			resp.setPrizeImg(cosUtil.getAccessUrl(resp.getPrizeImg()));
		}
		return resp;
	}
	
	@Override
	public BigDecimal prizeCompletion(String userId) throws Exception {
		return userTargetPrizeMapper.prizeCompletion(userId);
	}
	
}
