/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import com.luoxi.api.userScoreDetail.protocol.ReqAppUserScoreDetail;
import com.luoxi.api.userScoreDetail.protocol.ReqAppUserScoreDetail.RespAppUserScoreDetail;
import com.luoxi.api.userScoreDetail.protocol.ReqUserScoreDetailList;
import com.luoxi.api.userScoreDetail.protocol.ReqUserScoreDetailList.RespUserScoreDetailList;
import com.luoxi.model.UserScoreDetail;

import tk.mybatis.mapper.common.Mapper;

public interface UserScoreDetailMapper extends Mapper<UserScoreDetail>{
	
	/**
	 * @Description: 用户积分明细
	 * @Author wanbo
	 * @DateTime 2020/07/02
	 */
	List<RespAppUserScoreDetail> appUserScoreDetail(ReqAppUserScoreDetail req);
	
	/**
	 * @Description: 用户积分明细列表
	 * @Author wanbo
	 * @DateTime 2020/07/08
	 */
	List<RespUserScoreDetailList> userScoreDetailList(ReqUserScoreDetailList req);
	
}
