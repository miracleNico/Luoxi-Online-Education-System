package com.luoxi.api.userAddress;

import com.luoxi.api.userAddress.protocol.ReqSaveUserAddress;
import com.luoxi.api.userAddress.protocol.ReqSaveUserAddress.RespSaveUserAddress;
import com.luoxi.api.userAddress.protocol.ReqUserAddressInfo.RespUserAddressInfo;

public interface IUserAddressService {
	
	/**
	 * @Description: 用户收货地址信息
	 * @Author wanbo
	 * @DateTime 2020/06/29
	 */
	RespUserAddressInfo userAddressInfo(String userId) throws Exception;
	
	/**
	 * @Description: 保存用户收货地址
	 * @Author wanbo
	 * @DateTime 2020/06/29
	 */
	RespSaveUserAddress saveUserAddress(String userId,ReqSaveUserAddress req) throws Exception;
	
	
}
