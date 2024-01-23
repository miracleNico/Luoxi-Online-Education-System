package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessList;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessList.RespOpenAppBusinessList;
import com.luoxi.api.openAppBusiness.protocol.ReqOpenAppBusinessRemove;
import com.luoxi.model.OpenAppBusiness;

import tk.mybatis.mapper.common.Mapper;

public interface OpenAppBusinessMapper extends Mapper<OpenAppBusiness>{
	
	List<RespOpenAppBusinessList> appBusinessList(ReqOpenAppBusinessList req);
	
	int removeAppBusiness(@Param("list")List<ReqOpenAppBusinessRemove> list);
	
	@Select("select open_app_business_id,business_name from t_open_app_business where app_id=#{appId}")
	List<OpenAppBusiness> appBusinessSelected(@Param("appId")String appId);
	
}
