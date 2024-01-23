/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.luoxi.api.channelEarningsDetail.protocol.ReqChannelEarningsDetailList.RespChannelEarningsDetailList;
import com.luoxi.model.ChannelEarningsDetail;

import tk.mybatis.mapper.common.Mapper;

public interface ChannelEarningsDetailMapper extends Mapper<ChannelEarningsDetail>{
	
	/**
	 * @Description: 渠道收益详情列表汇总
	 * @Author wanbo
	 * @DateTime 2020/06/05
	 */
	Map<String, Object> channelEarningsDetailListSum(Map<String, Object> map);
	
	/**
	 * @Description: 渠道收益详情列表
	 * @Author wanbo
	 * @DateTime 2020/06/04
	 */
	List<RespChannelEarningsDetailList> channelEarningsDetailList(Map<String, Object> map);
	
	@Select("select * from t_channel_earnings_detail where status=true and user_id=#{userId} and order_number=#{orderNumber}")
	ChannelEarningsDetail getChannelEarningsDetailOnly(@Param("userId")String userId,@Param("orderNumber")String orderNum);
	
}
