package com.luoxi.server.dao;

import java.util.List;

import com.luoxi.api.openDevice.protocol.ReqOpenDeviceList;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import com.luoxi.model.OpenDevice;

import tk.mybatis.mapper.common.Mapper;

public interface OpenDeviceMapper extends Mapper<OpenDevice>{

	List<RespOpenDeviceList> deviceList(ReqOpenDeviceList req);
	
}
