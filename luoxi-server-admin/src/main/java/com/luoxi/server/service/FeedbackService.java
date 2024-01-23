package com.luoxi.server.service;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.aop.LxRedisCache;
import com.luoxi.api.feedback.IFeedbackService;
import com.luoxi.api.feedback.protocol.ReqAddFeedback;
import com.luoxi.api.feedback.protocol.ReqFeedbackInfo;
import com.luoxi.api.feedback.protocol.ReqFeedbackInfo.RespFeedbackInfo;
import com.luoxi.api.feedback.protocol.ReqFeedbackList;
import com.luoxi.api.feedback.protocol.ReqFeedbackList.RespFeedbackList;
import com.luoxi.api.feedback.protocol.ReqFeedbakProcessStatus;
import com.luoxi.api.feedback.protocol.ReqGetFeedbackOption.RespGetFeedbackOption;
import com.luoxi.api.feedback.protocol.ReqRemoveFeedback;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.constant.ConstFeedbackProcessStatus;
import com.luoxi.model.Feedback;
import com.luoxi.model.FeedbackOption;
import com.luoxi.server.dao.FeedbackMapper;
import com.luoxi.server.dao.FeedbackOptionMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

@DubboService
public class FeedbackService implements IFeedbackService{
	
	@Autowired
	private FeedbackMapper feedbackMapper;
	@Autowired
	private FeedbackOptionMapper feedbackOptionMapper;

	/**
	 * @Description: 新增问题反馈
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	@Override
	public void addFeedback(String userId,String packageName, String versionCode, ReqAddFeedback req) throws Exception {
		Feedback feedback = new Feedback();
		BeanUtil.copyProperties(req, feedback,CopyOptions.create().ignoreNullValue());
		feedback.setFeedbackId(IdUtil.fastSimpleUUID());
		feedback.setPackageName(packageName);
		feedback.setVersionCode(versionCode);
		feedback.setCreateUser(userId);
		feedbackMapper.insertSelective(feedback);
	}
	
	/**
	 * @Description: 获取问题反馈选项
	 * @Author wanbo
	 * @DateTime 2020/03/09
	 */
	@LxRedisCache(key = ConstCacheKey.FEEDBACK_OPTION)
	@Override
	public List<RespGetFeedbackOption> getFeedbackOption() throws Exception {
		List<FeedbackOption> feedbackOptions = feedbackOptionMapper.selectAll();
		TreeNodeConfig treeNodeConfig = TreeNodeConfig.DEFAULT_CONFIG.setIdKey("feedbackOptionId").setNameKey("feedbackOptionContent").setParentIdKey("parentId").setChildrenKey("children");
		List<Tree<String>> trees = cn.hutool.core.lang.tree.TreeUtil.build(feedbackOptions, "", treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getFeedbackOptionId());
            tree.setParentId(treeNode.getParentId());
            tree.setName(treeNode.getFeedbackOptionContent());
        });
		return JSONUtil.toList(new JSONArray(trees), RespGetFeedbackOption.class);
	}
	
	@Override
	public RespFeedbackInfo feedbackInfo(ReqFeedbackInfo req) throws Exception {
		return feedbackMapper.feedbackInfo(req);
	}
	
	@Override
	public RespPaging<RespFeedbackList> feedbackList(ReqFeedbackList req) throws Exception {
		RespPaging<RespFeedbackList> respPaging = new RespPaging<RespFeedbackList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespFeedbackList> list = feedbackMapper.feedbackList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespFeedbackList>(list), respPaging);
		for (RespFeedbackList resp : respPaging.getList()) {
			resp.setProcessStatus(EnumUtil.likeValueOf(ConstFeedbackProcessStatus.class, resp.getProcessStatus()).getText());
		}
		return respPaging;
	}
	
	@Override
	public void feedbakProcessStatus(ReqFeedbakProcessStatus req) throws Exception {
		Feedback feedback = new Feedback();
		BeanUtil.copyProperties(req, feedback);
		feedbackMapper.updateByPrimaryKeySelective(feedback);
	}
	
	@Override
	public void removeFeedback(List<ReqRemoveFeedback> req) throws Exception {
		feedbackMapper.removeFeedback(req);
	}

}
