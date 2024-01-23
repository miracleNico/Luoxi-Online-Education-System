package com.luoxi.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.userScore.IUserScoreService;
import com.luoxi.api.userScore.protocol.ReqLearningChangeUserScore;
import com.luoxi.api.userScore.protocol.ReqUserScoreList;
import com.luoxi.api.userScore.protocol.ReqUserScoreList.RespUserScoreList;
import com.luoxi.api.userScore.vo.UserScoreVo;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.constant.ConstUserScoreChangeType;
import com.luoxi.constant.Constant;
import com.luoxi.exception.LxException;
import com.luoxi.model.UserScore;
import com.luoxi.model.UserScoreDetail;
import com.luoxi.server.dao.UserScoreDetailMapper;
import com.luoxi.server.dao.UserScoreMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService
public class UserScoreService implements IUserScoreService{

	@Autowired
	private UserScoreMapper userScoreMapper;
	@Autowired
	private UserScoreDetailMapper userScoreDetailMapper;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	private Constant constant;
	
	@Override
	public UserScoreVo initUserScore(String userId) throws Exception {
		UserScore userScore = userScoreMapper.getUserScore(userId);
		if(BeanUtil.isEmpty(userScore)) {
			userScore = new UserScore().setUserScoreId(IdUtil.fastSimpleUUID()).setUserId(userId).setValue(BigDecimal.valueOf(0));
			userScoreMapper.insertSelective(userScore);
		}
		return BeanUtil.copyProperties(userScore, UserScoreVo.class);
	}
	
	@Transactional
	@Override
	public void changeUserScore(String userId, BigDecimal changeValue, String changeType, String joinId, String remark, Date createTime) throws Exception {
		UserScoreVo userScoreVo = this.initUserScore(userId);
		UserScoreDetail userScoreDetail = new UserScoreDetail()
				.setUserScoreDetailId(IdUtil.fastSimpleUUID())
				.setUserScoreId(userScoreVo.getUserScoreId())
				.setChangeType(changeType)
				.setChangeValue(changeValue)
				.setAfterValue(NumberUtil.add(userScoreVo.getValue(),changeValue))
				.setJoinId(joinId)
				.setCreateTime(createTime)
				.setRemark(remark);
		userScoreDetailMapper.insertSelective(userScoreDetail);
		UserScore userScore = BeanUtil.copyProperties(userScoreVo, UserScore.class).setValue(userScoreDetail.getAfterValue());
		userScoreMapper.updateByPrimaryKeySelective(userScore);
	}
	
	@Transactional
	@Override
	public void learningChangeUserScore(String userId, ReqLearningChangeUserScore req) throws Exception {
		String nowTime = DateUtil.now();
		ConstCacheKey constCacheKey = ConstCacheKey.USER_SCORE_EDU;
		String cacheKey = constCacheKey.cacheKey(DateUtil.formatDate(DateUtil.date()),userId);
		
		//取出之前调用的学习时长信息
		ReqLearningChangeUserScore beforCall  = null;
		if(stringRedisTemplate.hasKey(cacheKey)) {
			beforCall = JSONUtil.toBean(stringRedisTemplate.opsForValue().get(cacheKey), ReqLearningChangeUserScore.class);			
		}else {
			beforCall = new ReqLearningChangeUserScore().setCallTime(nowTime).setLearningMinutes(0L).setDaySumScore(BigDecimal.valueOf(0));
			LearningMinutesConvertScore(userId, nowTime, beforCall, req);
			return;
		}
		
		//日累计积分上限
		if(NumberUtil.compare(beforCall.getDaySumScore().intValue(),constant.getDay_max_convert_score().intValue())>=0) throw LxException.of().setMsg("日累计积分已达上限");
		
		//前后调用时间间隔(分钟)
		Long betweenMinutes = DateUtil.between(DateUtil.parse(beforCall.getCallTime()), DateUtil.parse(nowTime), DateUnit.MINUTE,false);
		if(req.getLearningMinutes()>0 && req.getLearningMinutes()<=betweenMinutes) {
			LearningMinutesConvertScore(userId, nowTime, beforCall, req);
			return;
		}
		log.info("-----------start");
		log.info("---learningMinutes:"+req.getLearningMinutes());
		log.info("---betweenMinutes:---"+betweenMinutes);
		log.info("-----------end");
		throw LxException.of().setMsg("违规触发学习积分");
	}
	
	private void LearningMinutesConvertScore(String userId, String nowTime, ReqLearningChangeUserScore beforCall, ReqLearningChangeUserScore req) throws Exception{
		ConstCacheKey constCacheKey = ConstCacheKey.USER_SCORE_EDU;
		String cacheKey = constCacheKey.cacheKey(DateUtil.formatDate(DateUtil.date()),userId);
		Long sumLearningMinutes = beforCall.getLearningMinutes() + req.getLearningMinutes();
		Long overflowLearningMinutes = sumLearningMinutes % constant.getLearningMinutes_convert_score();
		BigDecimal score = Convert.toBigDecimal(sumLearningMinutes / constant.getLearningMinutes_convert_score());
		
		//计算积分
		if(score.intValue()>0) {
			changeUserScore(userId, score, ConstUserScoreChangeType.EDU.name(), null, "学习积分", DateUtil.parse(nowTime));
		}
		
		//溢出保留
		ReqLearningChangeUserScore currentCall = new ReqLearningChangeUserScore()
				.setCallTime(nowTime)
				.setLearningMinutes(overflowLearningMinutes)
				.setDaySumScore(NumberUtil.add(score,beforCall.getDaySumScore()));
		stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(currentCall), constCacheKey.getExpire(), TimeUnit.SECONDS);
	}
	
	@Override
	public RespPaging<RespUserScoreList> userScoreList(ReqUserScoreList req) throws Exception {
		RespPaging<RespUserScoreList> respPaging = new RespPaging<RespUserScoreList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespUserScoreList> dbUsers = userScoreMapper.userScoreList(req);
		BeanUtil.copyProperties(new PageInfo<RespUserScoreList>(dbUsers), respPaging);
		Map<String, Object> mapSum = userScoreMapper.userScoreListSum(req);
		respPaging.setExtra(mapSum);
		return respPaging;
	}
	
}
