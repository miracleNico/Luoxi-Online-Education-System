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

import com.luoxi.api.businessApp.protocol.ReqBusinessAppInfo.RespBusinessAppInfo;
import com.luoxi.api.businessApp.protocol.ReqBusinessAppList.RespBusinessAppList;
import com.luoxi.api.businessApp.protocol.ReqRemoveBusinessApp;
import com.luoxi.api.businessApp.vo.BusinessAppVo;
import com.luoxi.model.BusinessApp;

import tk.mybatis.mapper.common.Mapper;

public interface BusinessAppMapper extends Mapper<BusinessApp>{
	
	/**
	 * @Description: 获取内容商最新产品
	 * @Author wanbo
	 * @DateTime 2020/04/27
	 */
	BusinessAppVo latestBusinessApp(@Param("businessCode")String businessCode,@Param("packageName")String packageName);
	
	List<Map<String, Object>> getBusinessAppTree(Map<String, Object> map);
	
	BusinessApp getBusinessApp(Map<String, Object> map);
	
	RespBusinessAppInfo businessAppInfo(Map<String, Object> map);
	
	/**
	 * @Description: 列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespBusinessAppList> businessAppList(Map<String, Object> map);
	
	/**
	 * @Description: 删除
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeBusinessApp(@Param("list")List<ReqRemoveBusinessApp> list);
	
}
