package com.luoxi.server.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.api.sequence.ISequenceService;
import com.luoxi.server.dao.SequenceMapper;

@DubboService
public class SequenceService implements ISequenceService{

	@Autowired
	private SequenceMapper sequenceMapper;
	
	@Override
	public String generateOrderId() {
		return sequenceMapper.generateOrderId();
	}
	
}
