package com.ailoxi.vision.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.ailoxi.vision.dao.VisionTableMapper;
import com.luoxi.api.vision.IVisionTable;
import com.luoxi.api.vision.protocol.ReqVisionTableInfo;
import com.luoxi.api.vision.protocol.ReqVisionTableInfo.RespVisionTableInfo;
import cn.hutool.core.bean.BeanUtil;

/**
 * @Description 服务：视力表
 * @author EdisonFeng
 * @DateTime 2021年5月1日
 * Copyright(c) 2021. All Rights Reserved
 */
@DubboService
public class VisionTableService implements IVisionTable {
	@Autowired
	private VisionTableMapper visionTableMapper;
	
	@Override
	public RespVisionTableInfo getVisionTableInfo(ReqVisionTableInfo req) throws Exception {
		//获取视力表信息
		return visionTableMapper.getVisionTableInfo(BeanUtil.beanToMap(req));
	}
}
