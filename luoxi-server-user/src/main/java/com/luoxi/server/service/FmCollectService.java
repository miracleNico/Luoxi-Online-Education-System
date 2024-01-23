package com.luoxi.server.service;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Async;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.api.fmcollect.IFmCollectService;
import com.luoxi.api.fmcollect.protocol.ReqAddFmCollect;
import com.luoxi.api.fmcollect.protocol.ReqFmCollectList;
import com.luoxi.api.fmcollect.protocol.ReqFmCollectList.RespFmCollectList;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.Constant;
import com.luoxi.exception.LxException;
import com.luoxi.model.FmCollect;
import com.luoxi.server.dao.FmCollectMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService
public class FmCollectService implements IFmCollectService{
	
	@Autowired 
	private FmCollectMapper fmCollectMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
    private RedisLockRegistry redisLockRegistry;
	@Autowired
	private Constant constant;
	
	/**
	 * @Description: 新增收藏
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	@Async
	@Override
	public void addFmCollect(String userId,ReqAddFmCollect req) throws Exception{
		Lock lock = redisLockRegistry.obtain(StrUtil.join(":", "COLLECT",userId,req.getResourceId()));
		if(lock.tryLock(constant.getRedis_lock_timeout_seconds(),TimeUnit.SECONDS)) {
			try {
				//log.info("---我拿到了锁哦---:"+lock.toString());
				FmCollect fmCollect = fmCollectMapper.getFmCollect(userId, req.getResourceId());
				if(fmCollect!=null) {
					fmCollectMapper.deleteByPrimaryKey(fmCollect);
				}else {
					fmCollect = FmCollect.of().setFmCollectId(IdUtil.fastSimpleUUID()).setResourceId(req.getResourceId()).setUserId(userId);
					fmCollectMapper.insertSelective(fmCollect);
				}
			}finally {
				//log.info("---我释放锁了哦---:"+lock.toString());
				lock.unlock();
			}
		}else {
			log.info("---我等待了{}秒还未拿到锁---:{}",constant.getRedis_lock_timeout_seconds(),lock.toString());
    		throw LxException.of().setMsg("服务器繁忙！");
		}
	}
	
	/**
	 * @Description: 资源收藏列表
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	@Override
	public RespPaging<RespFmCollectList> fmCollectList(String userId, ReqFmCollectList req) throws Exception {
		RespPaging<RespFmCollectList> respPaging = new RespPaging<RespFmCollectList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespFmCollectList> list = fmCollectMapper.fmCollectList(userId);
		BeanUtil.copyProperties(new PageInfo<RespFmCollectList>(list), respPaging);
		for (EduResourceVo eduResourceVo : respPaging.getList()) {
			eduResourceVo.setCoverUrl(cosUtil.getAccessUrl(eduResourceVo.getCoverUrl()));
			eduResourceVo.setFileUrl(cosUtil.getAccessUrl(eduResourceVo.getFileUrl()));
		}
		return respPaging;
	}
	
}
