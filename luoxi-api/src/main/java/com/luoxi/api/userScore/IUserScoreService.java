package com.luoxi.api.userScore;

import java.math.BigDecimal;
import java.util.Date;

import com.luoxi.api.userScore.protocol.ReqLearningChangeUserScore;
import com.luoxi.api.userScore.protocol.ReqUserScoreList;
import com.luoxi.api.userScore.protocol.ReqUserScoreList.RespUserScoreList;
import com.luoxi.api.userScore.vo.UserScoreVo;
import com.luoxi.base.RespPaging;

public interface IUserScoreService {
	
	/**
	 * @Description: 初始化用户积分
	 * @Author wanbo
	 * @DateTime 2020/06/30
	 */
	UserScoreVo initUserScore(String userId) throws Exception;
	
	/**
	 * @Description: 触发用户积分
	 * @Author wanbo
	 * @DateTime 2020/06/30
	 */
	void changeUserScore(String userId, BigDecimal changeValue, String changeType, String joinId, String remark, Date createTime) throws Exception;
	
	/**
	 * @Description: 学习-触发用户积分
	 * @Author wanbo
	 * @DateTime 2020/06/30
	 */
	void learningChangeUserScore(String userId, ReqLearningChangeUserScore req) throws Exception;
	
	/**
	 * @Description: 用户积分列表
	 * @Author wanbo
	 * @DateTime 2020/07/08
	 */
	RespPaging<RespUserScoreList> userScoreList(ReqUserScoreList req) throws Exception;
	
}
