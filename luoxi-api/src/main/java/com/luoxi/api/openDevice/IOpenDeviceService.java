package com.luoxi.api.openDevice;

import java.util.List;

import com.luoxi.api.openDevice.protocol.ReqOpenDeviceAuth;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceEnable;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceAuth.RespOpenDeviceAuth;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceList;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceList.RespOpenDeviceList;
import com.luoxi.api.openDevice.vo.OpenDeviceToken;
import com.luoxi.api.openDevice.protocol.ReqOpenDeviceRemove;
import com.luoxi.base.RespPaging;
import com.luoxi.model.OpenDevice;

public interface IOpenDeviceService {
	
	/**
	 * @Description: 设备列表
	 * @Author wanbo
	 * @DateTime 2020/09/27
	 */
	RespPaging<RespOpenDeviceList> deviceList(ReqOpenDeviceList req) throws Exception;
	
	/**
	 * @Description: 删除认证设备
	 * @Author wanbo
	 * @DateTime 2020/09/27
	 */
	void removeDevice(List<ReqOpenDeviceRemove> devices) throws Exception;
	
	/**
	 * @Description: 设备信息
	 * @Author wanbo
	 * @DateTime 2020/09/28
	 */
	OpenDevice info(String openDeviceId) throws Exception;
	
	/**
	 * @Description:启用/禁用设备 
	 * @Author wanbo
	 * @DateTime 2020/09/28
	 */
	void enable(ReqOpenDeviceEnable req) throws Exception;
	
	/**
	 * @Description: 设备认证
	 * @Author wanbo
	 * @DateTime 2020/09/21
	 */
	RespOpenDeviceAuth auth(ReqOpenDeviceAuth req) throws Exception;
	
	String checkToken(String token,OpenDeviceToken openDeviceToken) throws Exception;
	
}
