package com.luoxi.api.userScoreDetail;

import com.luoxi.api.userScoreDetail.protocol.ReqAppUserScoreDetail;
import com.luoxi.api.userScoreDetail.protocol.ReqAppUserScoreDetail.RespAppUserScoreDetail;
import com.luoxi.api.userScoreDetail.protocol.ReqUserScoreDetailList;
import com.luoxi.api.userScoreDetail.protocol.ReqUserScoreDetailList.RespUserScoreDetailList;
import com.luoxi.base.RespPaging;

public interface IUserScoreDetailService {

	/**
	 * @Description: 积分明细
	 * @Author wanbo
	 * @DateTime 2020/07/02
	 */
	RespPaging<RespAppUserScoreDetail> appUserScoreDetail(ReqAppUserScoreDetail req) throws Exception;
	
	/**
	 * @Description: 用户积分明细列表
	 * @Author wanbo
	 * @DateTime 2020/07/08
	 */
	RespPaging<RespUserScoreDetailList> userScoreDetailList(ReqUserScoreDetailList req) throws Exception;
	
}
