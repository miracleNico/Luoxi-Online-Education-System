/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.luoxi.api.storeApp.protocol.ReqRemoveStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSearchStoreApp;
import com.luoxi.api.storeApp.protocol.ReqSearchStoreApp.RespSearchStoreApp;
import com.luoxi.api.storeApp.protocol.ReqStoreAppInfo.RespStoreAppInfo;
import com.luoxi.api.storeApp.protocol.ReqStoreAppList.RespStoreAppList;
import com.luoxi.api.storeApp.protocol.ReqStoreAppScopeInfo.RespStoreAppScopeInfo;
import com.luoxi.model.StoreApp;
import com.luoxi.model.StoreAppScope;

import tk.mybatis.mapper.common.Mapper;

public interface StoreAppMapper extends Mapper<StoreApp>{
	
	List<RespSearchStoreApp> searchStoreApp(ReqSearchStoreApp req);
	
	StoreApp getStoreApp(Map<String, Object> map);
	
	/**
	 * @Description: 应用商店详情
	 * @Author wanbo
	 * @DateTime 2019/11/25
	 */
	RespStoreAppInfo storeAppInfo(Map<String, Object> map);
	
	/**
	 * @Description: 应用商店列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespStoreAppList> storeAppList(Map<String, Object> map);
	
	/**
	 * @Description: 应用商店范围
	 * @Author wanbo
	 * @DateTime 2019/12/02
	 */
	List<RespStoreAppScopeInfo> storeAppScopeInfoList(String storeAppId);
	
	/**
	 * @Description: 删除应用商店
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeStoreApp(@Param("list")List<ReqRemoveStoreApp> list);
	
	/**
	 * @Description: 删除应用商店范围
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	@Delete("delete from t_store_app_scope where store_app_id=#{storeAppId}")
	int removeStoreAppScope(String storeAppId);
	
	/**
	 * @Description: 批量添加应用商店范围
	 * @Author wanbo
	 * @DateTime 2019/12/04
	 */
	int addStoreAppScope(@Param("list")List<StoreAppScope> list);
	
}
