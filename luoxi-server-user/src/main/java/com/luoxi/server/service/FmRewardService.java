package com.luoxi.server.service;


import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.api.eduTimetable.IEduTimetableService;
import com.luoxi.api.fmreward.IFmRewardService;
import com.luoxi.api.fmreward.protocol.ReqAddReward;
import com.luoxi.exception.LxException;
import com.luoxi.model.FmReward;
import com.luoxi.server.dao.FmRewardMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;

@DubboService
public class FmRewardService implements IFmRewardService{
	
	@Autowired
	private FmRewardMapper rewardMapper;
	@DubboReference
	private IEduTimetableService eduTimetableService;
	
	/**
	 * @Description: 新增奖励-奖励后期需要根据指定资源奖励
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	@Override
	public void addReward(ReqAddReward req) throws Exception {
		if(rewardMapper.existsReward(req)) throw LxException.of().setMsg("请勿重复奖励");
		if(!eduTimetableService.existsThisWeekResource(req.getUserId(), req.getResourceId())) throw LxException.of().setMsg("此资源不在本周资源奖励范围内");
		FmReward reward = new FmReward();
		BeanUtil.copyProperties(req, reward);
		reward.setFmRewardId(IdUtil.fastSimpleUUID());
		rewardMapper.insertSelective(reward);
	}
	
}
