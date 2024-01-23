/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.luoxi.api.fmcollect.protocol.ReqFmCollectList.RespFmCollectList;
import com.luoxi.model.FmCollect;

import tk.mybatis.mapper.common.Mapper;

public interface FmCollectMapper extends Mapper<FmCollect>{
	
	/**
	 * @Description: 获取用户某资源收藏记录
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	@Select("SELECT * FROM t_fm_collect cl where cl.user_id=#{userId} and cl.resource_id=#{resourceId} limit 1")
	FmCollect getFmCollect(@Param("userId")String userId, @Param("resourceId")String resourceId);
	
	/**
	 * @Description: 资源收藏列表
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	List<RespFmCollectList> fmCollectList(@Param("userId")String userId);
	
}
