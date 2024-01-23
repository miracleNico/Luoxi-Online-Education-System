package com.luoxi.server.service;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.aop.LxRedisCache;
import com.luoxi.api.subject.ISubjectService;
import com.luoxi.api.subject.protocol.ReqSubjectList.RespSubjectList;
import com.luoxi.constant.ConstCacheKey;
import com.luoxi.server.dao.SubjectMapper;

@DubboService
public class SubjectService implements ISubjectService{
	
	@Autowired
	private SubjectMapper subjectMapper;
	
	@LxRedisCache(key = ConstCacheKey.SUBJECT)
	@Override
	public List<RespSubjectList> subjectList() throws Exception {
		return subjectMapper.subjectList();
	}

}
