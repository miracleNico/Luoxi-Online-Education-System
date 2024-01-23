package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkList;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkList.RespOpenAppBusinessCdkList;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkRemove;
import com.luoxi.model.OpenAppBusinessCdk;

import tk.mybatis.mapper.common.Mapper;

public interface OpenAppBusinessCdkMapper extends Mapper<OpenAppBusinessCdk>{
	
	String getOpenAppBusinessCdk(@Param("openDeviceId")String openDeviceId,@Param("appId")String appId,@Param("businessCode")String businessCode);
	
	List<RespOpenAppBusinessCdkList> appBusinessCdkList(ReqOpenAppBusinessCdkList req);
	
	int removeAppBusinessCdk(@Param("list")List<ReqOpenAppBusinessCdkRemove> list);
}
