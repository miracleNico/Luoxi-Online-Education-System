package com.luoxi.api.sn;

import java.util.List;

import com.luoxi.api.sn.protocol.ReqRemoveSerialNumber;
import com.luoxi.api.sn.protocol.ReqSaveSerialNumber;
import com.luoxi.api.sn.protocol.ReqSerialNumberInfo;
import com.luoxi.api.sn.protocol.ReqSerialNumberInfo.RespSerialNumberInfo;
import com.luoxi.api.sn.protocol.ReqSerialNumberList;
import com.luoxi.api.sn.protocol.ReqSerialNumberList.RespSerialNumberList;
import com.luoxi.base.RespPaging;

public interface ISerialNumberService {

	/**
	 * @Description: SN码列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespSerialNumberList> serialNumberList(ReqSerialNumberList req) throws Exception;
	
	/**
	 * @Description: SN码详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSerialNumberInfo serialNumberInfo(ReqSerialNumberInfo req) throws Exception;
	
	/**
	 * @Description: 保存SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveSerialNumber(ReqSaveSerialNumber req) throws Exception;
	
	/**
	 * @Description: 删除SN码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeSerialNumber(List<ReqRemoveSerialNumber> req) throws Exception;
	
	
}
