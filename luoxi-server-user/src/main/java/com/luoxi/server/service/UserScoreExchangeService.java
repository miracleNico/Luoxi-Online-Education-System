package com.luoxi.server.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.app.IAppService;
import com.luoxi.api.app.vo.AppVo;
import com.luoxi.api.prize.IPrizeService;
import com.luoxi.api.prize.vo.PrizeVo;
import com.luoxi.api.sequence.ISequenceService;
import com.luoxi.api.userScore.IUserScoreService;
import com.luoxi.api.userScore.vo.UserScoreVo;
import com.luoxi.api.userScoreExchange.IUserScoreExchangeService;
import com.luoxi.api.userScoreExchange.protocol.ReqSaveUserScoreExchange;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchange;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeInfo.RespUserScoreExchangeInfo;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeList;
import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeList.RespUserScoreExchangeList;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.ConstOrderSendStatus;
import com.luoxi.constant.ConstPrizeStatus;
import com.luoxi.constant.ConstUserScoreChangeType;
import com.luoxi.constant.Constant;
import com.luoxi.exception.LxException;
import com.luoxi.model.User;
import com.luoxi.model.UserScoreExchange;
import com.luoxi.model.UserTargetPrize;
import com.luoxi.server.dao.UserMapper;
import com.luoxi.server.dao.UserScoreExchangeMapper;
import com.luoxi.server.dao.UserTargetPrizeMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用户积分兑换
 * @Author wanbo
 * @DateTime 2020/06/29
 */
@Slf4j
@DubboService
public class UserScoreExchangeService implements IUserScoreExchangeService{

	@Autowired
	private UserMapper userMapper;
	@DubboReference
	private IAppService appService;
	@Autowired
	private UserScoreExchangeMapper userScoreExchangeMapper;
	@DubboReference
	private IPrizeService prizeService;
	@Autowired
	private IUserScoreService userScoreService;
	@Autowired
	private UserTargetPrizeMapper userTargetPrizeMapper;
	@Autowired
    private RedisLockRegistry redisLockRegistry;
	@Autowired
	private CosUtil cosUtil;
	@DubboReference
	private ISequenceService sequenceService;
	@Autowired
	private Constant constant;
	
	@GlobalTransactional
	@Override
	public void userScoreExchange(String userId, ReqUserScoreExchange req) throws Exception {
		PrizeVo prizeVo = prizeService.getPrizeVoByIdLock(req.getPrizeId());
		if(BeanUtil.isEmpty(prizeVo) || !prizeVo.getStatus()) throw LxException.of().setMsg("奖品不存在，请重新选择您要兑换的奖品哦！");
		if(ConstPrizeStatus.DOWN.name().equals(prizeVo.getPrizeStatus())) throw LxException.of().setMsg("奖品已下架，请重新选择您要兑换的奖品哦！");
		if(prizeVo.getInventory()<1) throw LxException.of().setMsg("库存不足，请重新选择您要兑换的奖品哦！");
		
		UserScoreVo userScore = userScoreService.initUserScore(userId);
		if(NumberUtil.isLess(userScore.getValue(), prizeVo.getScore())) throw LxException.of().setMsg("您的积分不足哦！赶紧加油学习吧^^");
		
		User user = userMapper.selectByPrimaryKey(userId);
		AppVo appVo = appService.getAppByAppId(user.getAppId());
		
		UserScoreExchange userScoreExchange = BeanUtil.copyProperties(req, UserScoreExchange.class)
				.setUserScoreExchangeId(IdUtil.fastSimpleUUID())
				.setOrderNumber(sequenceService.generateOrderId())
				.setChannelId(appVo.getChannelId())
				.setUserId(userId)
				.setPrizeName(prizeVo.getPrizeName())
				.setPrizeImg(prizeVo.getPrizeImg())
				.setScore(prizeVo.getScore())
				.setSendStatus(ConstOrderSendStatus.NSEND.name())
				;
		
		//积分兑换
		userScoreExchangeMapper.insertSelective(userScoreExchange);
		
		//触发用户积分
		userScoreService.changeUserScore(userId, NumberUtil.mul(prizeVo.getScore(),-1), ConstUserScoreChangeType.EXCHANGE.name(), userScoreExchange.getUserScoreExchangeId(), StrUtil.concat(true, "兑换奖品-",prizeVo.getPrizeName()),new Date());
		
		//删除用户目标奖品
		userTargetPrizeMapper.delete(new UserTargetPrize().setUserId(userId));
		
		Lock lock = redisLockRegistry.obtain(prizeVo.getPrizeId());
		if(lock.tryLock(constant.getRedis_lock_timeout_seconds(),TimeUnit.SECONDS)) {
			try {
				//log.info("---我拿到了锁---:"+lock.toString());
				//扣减库存(并发处理)
				prizeService.updatePrizeInventory(prizeVo.getPrizeId(), -1);
			}finally {
				//log.info("---我释放锁了---:"+lock.toString());
				lock.unlock();
			}
		}else {
			log.info("---我等待了{}秒还未拿到锁---:{}",constant.getRedis_lock_timeout_seconds(),lock.toString());
    		throw LxException.of().setMsg("服务器繁忙！");
		}
	}
	
	@Override
	public RespUserScoreExchangeInfo userScoreExchangeInfo(String userScoreExchangeId) throws Exception {
		UserScoreExchange userScoreExchange = userScoreExchangeMapper.selectByPrimaryKey(userScoreExchangeId);
		if(BeanUtil.isNotEmpty(userScoreExchange)) {
			return BeanUtil.copyProperties(userScoreExchange, RespUserScoreExchangeInfo.class);
		}
		return null;
	}
	
	@Override
	public RespPaging<RespUserScoreExchangeList> userScoreExchangeList(ReqUserScoreExchangeList req) throws Exception {
		RespPaging<RespUserScoreExchangeList> respPaging = new RespPaging<RespUserScoreExchangeList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespUserScoreExchangeList> dbList = userScoreExchangeMapper.userScoreExchangeList(req);
		BeanUtil.copyProperties(new PageInfo<RespUserScoreExchangeList>(dbList), respPaging);
		for (RespUserScoreExchangeList resp : respPaging.getList()) {
			resp.setPrizeImg(cosUtil.getAccessUrl(resp.getPrizeImg()));
		}
		return respPaging;
	}
	
	@Override
	public void saveUserScoreExchange(ReqSaveUserScoreExchange req) throws Exception {
		UserScoreExchange userScoreExchange = new UserScoreExchange()
				.setUserScoreExchangeId(req.getUserScoreExchangeId())
				.setOrderNumber(req.getOrderNumber())
				.setCourierNumber(req.getCourierNumber())
				.setSendStatus(req.getSendStatus())
				.setSendTime(req.getSendTime())
				.setRemark(req.getRemark());
		userScoreExchangeMapper.updateByPrimaryKeySelective(userScoreExchange);
	}
	
	
	
	
}
