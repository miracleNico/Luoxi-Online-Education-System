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

import com.luoxi.api.version.protocol.ReqAppVersionInfo.RespAppVersionInfo;
import com.luoxi.api.version.protocol.ReqAppVersionList.RespAppVersionList;
import com.luoxi.api.version.protocol.ReqRemoveAppVersion;
import com.luoxi.model.AppVersion;

import tk.mybatis.mapper.common.Mapper;

public interface AppVersionMapper extends Mapper<AppVersion>{
	
	AppVersion getAppVersion(Map<String, Object> map);
	
	/**
	 * @Description: 版本详情
	 * @Author wanbo
	 * @DateTime 2019/11/25
	 */
	RespAppVersionInfo appVersionInfo(Map<String, Object> map);
	
	/**
	 * @Description: 版本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespAppVersionList> appVersionList(Map<String, Object> map);
	
	/**
	 * @Description: 删除版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeAppVersion(@Param("list")List<ReqRemoveAppVersion> list);
	
}
