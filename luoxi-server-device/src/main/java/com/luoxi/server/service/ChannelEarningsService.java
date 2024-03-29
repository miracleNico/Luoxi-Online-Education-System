package com.luoxi.server.service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.channelEarnings.IChannelEarningsService;
import com.luoxi.api.channelEarnings.protocol.ReqChannelEarningsList;
import com.luoxi.api.channelEarnings.protocol.ReqChannelEarningsList.RespChannelEarningsList;
import com.luoxi.api.channelEarnings.protocol.ReqImportChannelEarnings;
import com.luoxi.api.user.IUserService;
import com.luoxi.api.user.protocol.ReqUserInfo.RespUserInfo;
import com.luoxi.base.RespPaging;
import com.luoxi.exception.LxException;
import com.luoxi.model.App;
import com.luoxi.model.AppEarningsSetting;
import com.luoxi.model.ChannelEarnings;
import com.luoxi.model.ChannelEarningsDetail;
import com.luoxi.model.ThirdBusiness;
import com.luoxi.server.dao.AppEarningsSettingMapper;
import com.luoxi.server.dao.AppMapper;
import com.luoxi.server.dao.ChannelEarningsDetailMapper;
import com.luoxi.server.dao.ChannelEarningsMapper;
import com.luoxi.server.dao.ThirdBusinessMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;

@DubboService
public class ChannelEarningsService implements IChannelEarningsService{
	
	@Autowired
	private ChannelEarningsMapper channelEarningsMapper;
	@Autowired
	private ChannelEarningsDetailMapper channelEarningsDetailMapper;
	@Autowired
	private ThirdBusinessMapper thirdBusinessMapper;
	@DubboReference
	private IUserService userService;
	@Autowired
	private AppMapper appMapper;
	@Autowired
	private AppEarningsSettingMapper appEarningsSettingMapper;
	
	@Override
	public RespPaging<RespChannelEarningsList> channelEarningsList(ReqChannelEarningsList req) throws Exception {
		RespPaging<RespChannelEarningsList> respPaging = new RespPaging<RespChannelEarningsList>();
		Map<String, Object> reqMap = BeanUtil.beanToMap(req);
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespChannelEarningsList> list = channelEarningsMapper.channelEarningsList(reqMap);
		BeanUtil.copyProperties(new PageInfo<RespChannelEarningsList>(list), respPaging);
		Map<String, Object> mapSum = channelEarningsMapper.channelEarningsListSum(reqMap);
		respPaging.setExtra(mapSum);
		return respPaging;
	}
	
	@Override
	@Transactional
	public void importChannelEarnings(ReqImportChannelEarnings req) throws Exception {
		ThirdBusiness thirdBusiness = thirdBusinessMapper.getThirdBusinessByCode(req.getThirdBusinessCode());
		if(BeanUtil.isEmpty(thirdBusiness)) throw LxException.of().setMsg("第三方内容商不存在");
		
		ChannelEarningsDetail channelEarningsDetail = channelEarningsDetailMapper.getChannelEarningsDetailOnly(req.getUserId(), req.getOrderNumber());
		if(BeanUtil.isNotEmpty(channelEarningsDetail)) throw LxException.of().setMsg("收益明细记录已存在");
		
		RespUserInfo userInfo = userService.userInfo(req.getUserId());
		if(BeanUtil.isEmpty(userInfo)) throw LxException.of().setMsg("用户不存在");
		
		App app = appMapper.selectByPrimaryKey(userInfo.getAppId());
		if(BeanUtil.isEmpty(app)) throw LxException.of().setMsg("产品不存在");
		
		AppEarningsSetting appEarningsSetting = appEarningsSettingMapper.getAppEarningsSetting(app.getAppId(), thirdBusiness.getThirdBusinessId());
		if(BeanUtil.isEmpty(appEarningsSetting)) throw LxException.of().setMsg("产品【"+app.getAppName()+"】收益信息未设置");
		
		ChannelEarnings channelEarnings = channelEarningsMapper.getChannelEarnings(req.getMonth(), app.getChannelId());
		
		/**更新或新增主表*/
		if(BeanUtil.isEmpty(channelEarnings)) {
			channelEarnings = new ChannelEarnings()
					.setChannelEarningsId(IdUtil.fastSimpleUUID())
					.setChannelId(app.getChannelId())
					.setMonth(req.getMonth())
					.setOrderPayTotalAmount(NumberUtil.round(req.getOrderPayAmount(),2, RoundingMode.FLOOR))
					.setOriginalEarningsTotalAmount(NumberUtil.round(req.getOriginalEarningsAmount(),2, RoundingMode.FLOOR))
					.setActualEarningsTotalAmount(NumberUtil.round(NumberUtil.mul(req.getOriginalEarningsAmount(),appEarningsSetting.getSettlementRatio()/100),2, RoundingMode.FLOOR))
					;
			channelEarningsMapper.insertSelective(channelEarnings);
		}else {
			channelEarnings
			.setOrderPayTotalAmount(NumberUtil.round(NumberUtil.add(channelEarnings.getOrderPayTotalAmount(),req.getOrderPayAmount()),2, RoundingMode.FLOOR))
			.setOriginalEarningsTotalAmount(NumberUtil.round(NumberUtil.add(channelEarnings.getOriginalEarningsTotalAmount(),req.getOriginalEarningsAmount()),2, RoundingMode.FLOOR))
			.setActualEarningsTotalAmount(NumberUtil.round(NumberUtil.add(channelEarnings.getActualEarningsTotalAmount(),NumberUtil.mul(req.getOriginalEarningsAmount(),appEarningsSetting.getSettlementRatio()/100)),2, RoundingMode.FLOOR))
			;
			channelEarningsMapper.updateByPrimaryKeySelective(channelEarnings);
		}
		
		/**新增明细表*/
		channelEarningsDetail = new ChannelEarningsDetail()
				.setChannelEarningsDetailId(IdUtil.fastSimpleUUID())
				.setChannelEarningsId(channelEarnings.getChannelEarningsId())
				.setThirdBusinessId(thirdBusiness.getThirdBusinessId())
				.setUserId(req.getUserId())
				.setOrderNumber(req.getOrderNumber())
				.setOrderPayTime(DateUtil.parse(req.getOrderPayTime()))
				.setOrderPayAmount(NumberUtil.round(req.getOrderPayAmount(),2, RoundingMode.FLOOR))
				.setOriginalEarningsAmount(NumberUtil.round(req.getOriginalEarningsAmount(),2, RoundingMode.FLOOR))
				.setSettlementRatio(appEarningsSetting.getSettlementRatio())
				.setActualEarningsAmount(NumberUtil.round(NumberUtil.mul(req.getOriginalEarningsAmount(),appEarningsSetting.getSettlementRatio()/100),2, RoundingMode.FLOOR))
				;
		channelEarningsDetailMapper.insertSelective(channelEarningsDetail);
		
	}
	
	public static void main(String[] args) {
		System.out.println(NumberUtil.round(BigDecimal.valueOf(1.11), 2, RoundingMode.FLOOR));
	}
	
}
