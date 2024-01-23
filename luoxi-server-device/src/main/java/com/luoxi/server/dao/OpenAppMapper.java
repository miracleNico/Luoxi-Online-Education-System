package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.luoxi.api.openApp.protocol.ReqOpenAppList;
import com.luoxi.api.openApp.protocol.ReqOpenAppList.RespOpenAppList;
import com.luoxi.api.openApp.protocol.ReqOpenAppRemove;
import com.luoxi.model.OpenApp;

import tk.mybatis.mapper.common.Mapper;

public interface OpenAppMapper extends Mapper<OpenApp>{

	List<OpenApp> appSelected(Map<String, Object> map);
	
	List<RespOpenAppList> appList(ReqOpenAppList req);
	
	int removeApp(@Param("list")List<ReqOpenAppRemove> list);
}
