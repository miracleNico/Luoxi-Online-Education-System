/**  
 * @Title: OrderMfeedbacker.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luoxi.model.StatisticalAppActive;
import com.luoxi.server.vo.StatisticalVo;

import tk.mybatis.mapper.common.Mapper;

public interface StatisticalAppActiveMapper extends Mapper<StatisticalAppActive>{
	
	List<StatisticalVo> appActiveUserNumber(@Param("channelId")String channelId);
	
	Integer yesterdayActiveUserTotal(@Param("channelId")String channelId);
	
}
