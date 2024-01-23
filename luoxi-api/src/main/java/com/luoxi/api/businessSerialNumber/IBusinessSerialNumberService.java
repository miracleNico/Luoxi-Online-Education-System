package com.luoxi.api.businessSerialNumber;

import java.util.List;

import com.luoxi.api.businessSerialNumber.protocol.ReqBusinessSerialNumberList;
import com.luoxi.api.businessSerialNumber.protocol.ReqBusinessSerialNumberList.RespBusinessSerialNumberList;
import com.luoxi.api.businessSerialNumber.protocol.ReqGetBusinessSn;
import com.luoxi.api.businessSerialNumber.protocol.ReqGetBusinessSn.RespGetBusinessSn;
import com.luoxi.api.businessSerialNumber.protocol.ReqImportBusinessSerialNumber;
import com.luoxi.api.businessSerialNumber.protocol.ReqRemoveBusinessSerialNumber;
import com.luoxi.api.businessSerialNumber.protocol.ReqRestoreSn;
import com.luoxi.api.businessSerialNumber.protocol.ReqUpdBusinessSnUseStatus;
import com.luoxi.base.RespPaging;

public interface IBusinessSerialNumberService {
	
	/**
	 * @Description: 内容商激活码列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespBusinessSerialNumberList> businessSerialNumberList(ReqBusinessSerialNumberList req) throws Exception;
	
	/**
	 * @Description: 内容商激活码导入
	 * @Author wanbo
	 * @DateTime 2019/12/03
	 */
	void importBusinessSerialNumber(ReqImportBusinessSerialNumber req) throws Exception;
	
	/**
	 * @Description: 删除内容商激活码
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeBusinessSerialNumber(List<ReqRemoveBusinessSerialNumber> businessSerialNumbers) throws Exception;
	
	/**
	 * @Description: 修复内容商激活码
	 * @Author wanbo
	 * @DateTime 2020/03/11
	 */
	void restoreSn(List<ReqRestoreSn> list)throws Exception;
	
	/**
	 * @Description: 获取内容商激活码
	 * @Author wanbo
	 * @DateTime 2020/03/02
	 */
	List<RespGetBusinessSn> getBusinessSn(String packageName,String deviceId,List<ReqGetBusinessSn> req) throws Exception;
	
	/**
	 * @Description: 更新内容商激活码使用状态
	 * @Author wanbo
	 * @DateTime 2020/01/10
	 */
	void updBusinessSnUseStatus(String packageName,String versionCode,String deviceId,ReqUpdBusinessSnUseStatus req) throws Exception;
	
}
