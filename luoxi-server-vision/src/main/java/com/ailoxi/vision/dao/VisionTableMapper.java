package com.ailoxi.vision.dao;

import java.util.Map;

import com.luoxi.api.vision.protocol.ReqVisionTableInfo.RespVisionTableInfo;
import com.luoxi.model.VisionTable;

import tk.mybatis.mapper.common.Mapper;
/**
 * @Description DAO 视力表
 * @author Edison F.
 * @DateTime 2021年5月1日
 * Copyright(c) 2021. All Rights Reserved
 */
public interface VisionTableMapper extends Mapper<VisionTable> {
	/**
	 * @Description: 视力表查询
	 * @Author Edison F.
	 * @DateTime 2021年5月1日
	 */
	RespVisionTableInfo getVisionTableInfo(Map<String, Object> map);
}
