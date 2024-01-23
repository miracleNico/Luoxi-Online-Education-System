/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.api.eduTimetable.protocol.ReqEduTimetable;
import com.luoxi.api.eduTimetable.protocol.ReqEduTimetableResource;
import com.luoxi.api.eduTimetable.vo.EduTimetableVo;
import com.luoxi.model.EduTimetable;

import tk.mybatis.mapper.common.Mapper;

public interface EduTimetableMapper extends Mapper<EduTimetable>{
	
	/**
	 * @Description: 指定资源是否存在与本周资源
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	boolean existsThisWeekResource(@Param("userId")String userId,@Param("resourceId")String resourceId);
	
	/**
	 * @Description: 课程表
	 * @Author wanbo
	 * @DateTime 2020/04/13
	 */
	List<EduTimetableVo> timetableList(ReqEduTimetable req);

	/**
	 * @Description: 课程表资源
	 * @Author wanbo
	 * @DateTime 2020/04/13
	 */
	List<EduResourceVo> timetableResource(ReqEduTimetableResource req);
	
}
