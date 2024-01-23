/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.luoxi.api.business.protocol.ReqBusinessInfo.RespBusinessInfo;
import com.luoxi.api.business.protocol.ReqBusinessList.RespBusinessList;
import com.luoxi.api.business.protocol.ReqRemoveBusiness;
import com.luoxi.model.Business;

import tk.mybatis.mapper.common.Mapper;

public interface BusinessMapper extends Mapper<Business>{
	
	List<Map<String, Object>> getBusinessTree();
	
	Business getBusiness(Map<String, Object> map);
	
	RespBusinessInfo businessInfo(Map<String, Object> map);
	
	/**
	 * @Description: 列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespBusinessList> businessList(Map<String, Object> map);
	
	/**
	 * @Description: 删除内容商
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeBusiness(@Param("list")List<ReqRemoveBusiness> list);
	
}
