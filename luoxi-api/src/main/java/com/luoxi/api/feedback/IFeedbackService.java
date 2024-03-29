package com.luoxi.api.feedback;

import java.util.List;

import com.luoxi.api.feedback.protocol.ReqAddFeedback;
import com.luoxi.api.feedback.protocol.ReqFeedbackInfo;
import com.luoxi.api.feedback.protocol.ReqFeedbackList;
import com.luoxi.api.feedback.protocol.ReqFeedbackList.RespFeedbackList;
import com.luoxi.api.feedback.protocol.ReqFeedbakProcessStatus;
import com.luoxi.api.feedback.protocol.ReqGetFeedbackOption.RespGetFeedbackOption;
import com.luoxi.api.feedback.protocol.ReqRemoveFeedback;
import com.luoxi.api.feedback.protocol.ReqFeedbackInfo.RespFeedbackInfo;
import com.luoxi.base.RespPaging;

public interface IFeedbackService {

	/**
	 * @Description: 新增问题反馈
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	void addFeedback(String userId,String packageName,String versionCode,ReqAddFeedback req)throws Exception;
	
	/**
	 * @Description: 获取问题反馈选项
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	List<RespGetFeedbackOption> getFeedbackOption()throws Exception;
	
	/**
	 * @Description: 获取问题反馈
	 * @Author wanbo
	 * @DateTime 2020/03/12
	 */
	RespFeedbackInfo feedbackInfo(ReqFeedbackInfo req)throws Exception;
	
	/**
	 * @Description: 反馈列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespFeedbackList> feedbackList(ReqFeedbackList req) throws Exception;
	
	/**
	 * @Description: 删除反馈
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeFeedback(List<ReqRemoveFeedback> req) throws Exception;
	
	/**
	 * @Description: 问题反馈处理
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	void feedbakProcessStatus(ReqFeedbakProcessStatus req) throws Exception;
	
}
