package com.luoxi.server.service;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.aop.LxRedisCache;
import com.luoxi.api.grade.IGradeService;
import com.luoxi.api.grade.protocol.ReqGradeList.RespGradeList;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.server.dao.GradeMapper;

@DubboService
public class GradeService implements IGradeService{
	
	@Autowired
	private GradeMapper gradeMapper;
	
	@LxRedisCache(key = ConstCacheKey.GRADE)
	@Override
	public List<RespGradeList> gradeList() throws Exception {
		return gradeMapper.gradeList();
	}

}
