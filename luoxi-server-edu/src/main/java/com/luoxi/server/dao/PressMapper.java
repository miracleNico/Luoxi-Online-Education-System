/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.luoxi.api.press.protocol.ReqPressList.RespPressList;
import com.luoxi.model.Press;

import tk.mybatis.mapper.common.Mapper;

public interface PressMapper extends Mapper<Press>{
	
	@Select("select * from t_press order by press_id asc")
	List<RespPressList> pressList();
	
}
