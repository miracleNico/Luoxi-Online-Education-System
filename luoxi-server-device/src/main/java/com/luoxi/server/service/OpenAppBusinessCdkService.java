package com.luoxi.server.service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.openApp.IOpenAppService;
import com.luoxi.api.openAppBusiness.IOpenAppBusinessService;
import com.luoxi.api.openAppBusinessCdk.IOpenAppBusinessCdkService;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkImport;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkList;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkList.RespOpenAppBusinessCdkList;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkRemove;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.Constant;
import com.luoxi.exception.LxException;
import com.luoxi.model.Channel;
import com.luoxi.model.OpenApp;
import com.luoxi.model.OpenAppBusiness;
import com.luoxi.model.OpenAppBusinessCdk;
import com.luoxi.server.dao.ChannelMapper;
import com.luoxi.server.dao.OpenAppBusinessCdkMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService
public class OpenAppBusinessCdkService implements IOpenAppBusinessCdkService{
	
	@Autowired
	private OpenAppBusinessCdkMapper openAppBusinessCdkMapper;
	@Autowired
	private IOpenAppService openAppService;
	@Autowired
	private IOpenAppBusinessService openAppBusinessService;
	@Autowired
    private RedisLockRegistry redisLockRegistry;
	@Autowired
	private ChannelMapper channelMapper;
	@Autowired
	private Constant constant;
	
	@Override
	public OpenAppBusinessCdk info(String openAppBusinessCdkId) throws Exception {
		return openAppBusinessCdkMapper.selectByPrimaryKey(openAppBusinessCdkId);
	}
	
	@Override
	public String getOpenAppBusinessCdk(String openDeviceId, String appId, String businessCode) throws Exception {
		String cdk = null;
		OpenAppBusiness openAppBusiness = openAppBusinessService.getOpenAppBusiness(appId, businessCode);
		String oldCdk = openAppBusinessCdkMapper.getOpenAppBusinessCdk(openDeviceId, appId, businessCode);
		if(StrUtil.isNotBlank(oldCdk)) {
			cdk = oldCdk;
		}else {
			Lock lock = redisLockRegistry.obtain(StrUtil.join(":", appId, businessCode));
			if(lock.tryLock(constant.getRedis_lock_timeout_seconds(),TimeUnit.SECONDS)) {
				try {
					//log.info("---我拿到了锁---:"+lock.toString());
					OpenAppBusinessCdk where = new OpenAppBusinessCdk()
							.setStatus(true)
							.setUseStatus(false)
							.setOpenAppBusinessId(openAppBusiness.getOpenAppBusinessId())
							;
					List<OpenAppBusinessCdk> list = openAppBusinessCdkMapper.selectByRowBounds(where, new RowBounds(0, 1));
					if(CollUtil.isNotEmpty(list)) {
						OpenAppBusinessCdk openAppBusinessCdk = list.get(0);
						cdk = openAppBusinessCdk.getCdk();
						openAppBusinessCdkMapper.updateByPrimaryKeySelective(openAppBusinessCdk.setOpenDeviceId(openDeviceId).setUseStatus(true));
					}
				}finally {
					//log.info("---我释放锁了---:"+lock.toString());
					lock.unlock();
				}
			}else {
				log.info("---我等待了{}秒还未拿到锁---:{}",constant.getRedis_lock_timeout_seconds(),lock.toString());
	    		throw LxException.of().setMsg("服务器繁忙！");
			}
		}
		if(StrUtil.isBlank(cdk)) throw LxException.of().setMsg("此业务代码没有可用的CDK:"+businessCode);
		return cdk;
	}
	
	@Override
	public RespPaging<RespOpenAppBusinessCdkList> appBusinessCdkList(ReqOpenAppBusinessCdkList req) throws Exception {
		RespPaging<RespOpenAppBusinessCdkList> respPaging = new RespPaging<RespOpenAppBusinessCdkList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespOpenAppBusinessCdkList> list = openAppBusinessCdkMapper.appBusinessCdkList(req);
		BeanUtil.copyProperties(new PageInfo<RespOpenAppBusinessCdkList>(list), respPaging);
		return respPaging;
	}
	
	@Override
	public void saveAppBusinessCdk(OpenAppBusinessCdk openAppBusinessCdk) throws Exception {
		openAppBusinessCdkMapper.updateByPrimaryKeySelective(new OpenAppBusinessCdk()
				.setOpenAppBusinessCdkId(openAppBusinessCdk.getOpenAppBusinessCdkId())
				.setRemark(openAppBusinessCdk.getRemark())
				);
	}
	
	@Override
	public void removeAppBusinessCdk(List<ReqOpenAppBusinessCdkRemove> req) throws Exception {
		openAppBusinessCdkMapper.removeAppBusinessCdk(req);
	}
	
	@Override
	public void importAppBusinessCdk(ReqOpenAppBusinessCdkImport cdk) throws Exception {
		Channel channel = channelMapper.selectOne(new Channel().setStatus(true).setChannelName(cdk.getChannelName()));
		if(BeanUtil.isEmpty(channel)) throw LxException.of().setMsg("渠道不存在");
		OpenApp openApp = openAppService.getOpenAppByName(channel.getChannelId(), cdk.getOpenAppName());
		OpenAppBusiness openAppBusiness = openAppBusinessService.getOpenAppBusinessByName(openApp.getAppId(), cdk.getOpenAppBusinessName());
		OpenAppBusinessCdk openAppBusinessCdk = openAppBusinessCdkMapper.selectOne(new OpenAppBusinessCdk().setStatus(true).setOpenAppBusinessId(openAppBusiness.getOpenAppBusinessId()).setCdk(cdk.getCdk()));
		if(BeanUtil.isNotEmpty(openAppBusinessCdk)) throw LxException.of().setMsg("CDK已存在");
		openAppBusinessCdk = new OpenAppBusinessCdk()
				.setOpenAppBusinessCdkId(IdUtil.fastSimpleUUID())
				.setCdk(cdk.getCdk())
				.setOpenAppBusinessId(openAppBusiness.getOpenAppBusinessId())
				.setRemark(cdk.getRemark());
		openAppBusinessCdkMapper.insertSelective(openAppBusinessCdk);
	}
	
}
