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
import com.luoxi.api.fmbrowse.IFmBrowseService;
import com.luoxi.api.fmbrowse.protocol.ReqAddFmBrowse;
import com.luoxi.api.fmbrowse.protocol.ReqFmBrowseList;
import com.luoxi.api.fmbrowse.protocol.ReqFmBrowseList.RespFmBrowseList;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.Constant;
import com.luoxi.exception.LxException;
import com.luoxi.model.FmBrowse;
import com.luoxi.server.dao.FmBrowseMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DubboService
public class FmBrowseService implements IFmBrowseService{
	
	@Autowired 
	private FmBrowseMapper fmBrowseMapper;
	@Autowired
	private CosUtil cosUtil;
	@Autowired
    private RedisLockRegistry redisLockRegistry;
	@Autowired
	private Constant constant;
	
	/**
	 * @Description: 新增浏览记录
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	@Async
	@Override
	public void addFmBrowse(String userId,ReqAddFmBrowse req) throws Exception {
		Lock lock = redisLockRegistry.obtain(StrUtil.join(":", "BROWSE", userId, req.getResourceId()));
		if(lock.tryLock(constant.getRedis_lock_timeout_seconds(),TimeUnit.SECONDS)) {
			try {
				//log.info("---我拿到了锁哦---:"+lock.toString());
				FmBrowse fmBrowse = fmBrowseMapper.getFmBrowse(userId, req.getResourceId());
				if(fmBrowse!=null) {
					fmBrowseMapper.updateFmBrowseTime(fmBrowse.getFmBrowseId());
				}else {
					fmBrowseMapper.insertSelective(FmBrowse.of().setFmBrowseId(IdUtil.fastSimpleUUID()).setResourceId(req.getResourceId()).setUserId(userId));
					//溢出删除
					fmBrowseMapper.fmBrowseOverflow(userId);
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
	 * @Description: 资源浏览记录列表
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	@Override
	public RespPaging<RespFmBrowseList> fmBrowseList(String userId, ReqFmBrowseList req) throws Exception {
		RespPaging<RespFmBrowseList> respPaging = new RespPaging<RespFmBrowseList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespFmBrowseList> list = fmBrowseMapper.fmBrowseList(userId);
		BeanUtil.copyProperties(new PageInfo<RespFmBrowseList>(list), respPaging);
		for (EduResourceVo eduResourceVo : respPaging.getList()) {
			eduResourceVo.setCoverUrl(cosUtil.getAccessUrl(eduResourceVo.getCoverUrl()));
			eduResourceVo.setFileUrl(cosUtil.getAccessUrl(eduResourceVo.getFileUrl()));
		}
		return respPaging;
	}
	
}
