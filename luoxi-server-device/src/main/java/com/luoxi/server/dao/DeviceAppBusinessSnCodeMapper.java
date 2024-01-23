package com.luoxi.server.dao;

import org.apache.ibatis.annotations.Param;

import com.luoxi.model.DeviceAppBusinessSnCode;

import tk.mybatis.mapper.common.Mapper;

public interface DeviceAppBusinessSnCodeMapper extends Mapper<DeviceAppBusinessSnCode>{

	
	/**
	 * @Description: 获取激活成功的激活码
	 * @Author wanbo
	 * @DateTime 2020/03/10
	 */
	String getUseSuccessDeviceAppBusinessSnCode(@Param("businessCode")String businessCode,@Param("packageName")String packageName,@Param("deviceId")String deviceId);
	
}
