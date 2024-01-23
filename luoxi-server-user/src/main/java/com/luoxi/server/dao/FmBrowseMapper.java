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
import org.apache.ibatis.annotations.Update;

import com.luoxi.api.fmbrowse.protocol.ReqFmBrowseList.RespFmBrowseList;
import com.luoxi.model.FmBrowse;

import tk.mybatis.mapper.common.Mapper;

public interface FmBrowseMapper extends Mapper<FmBrowse>{
	
	/**
	 * @Description: 获取用户某资源浏览记录
	 * @Author wanbo
	 * @DateTime 2020/07/10
	 */
	@Select("select * from t_fm_browse where user_id=#{userId} and resource_id=#{resourceId}")
	FmBrowse getFmBrowse(@Param("userId")String userId, @Param("resourceId")String resourceId);
	
	@Update("update t_fm_browse set update_time=now() where fm_browse_id=#{fmBrowseId}")
	int updateFmBrowseTime(@Param("fmBrowseId")String fmBrowseId);
	
	/**
	 * @Description: 资源浏览记录列表
	 * @Author wanbo
	 * @DateTime 2020/03/27
	 */
	List<RespFmBrowseList> fmBrowseList(@Param("userId")String userId);
	
	/**
	 * @Description: 溢出删除
	 * @Author wanbo
	 * @DateTime 2020/04/20
	 */
	int fmBrowseOverflow(@Param("userId")String userId);
	
}
