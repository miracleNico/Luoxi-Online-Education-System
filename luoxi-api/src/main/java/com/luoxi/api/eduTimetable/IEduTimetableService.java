package com.luoxi.api.eduTimetable;

import java.util.List;

import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.api.eduTimetable.protocol.ReqEduTimetable;
import com.luoxi.api.eduTimetable.protocol.ReqEduTimetableResource;
import com.luoxi.api.eduTimetable.vo.EduTimetableVo;

public interface IEduTimetableService {
	
	/**
	 * @Description: 指定资源是否存在与本周资源
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	boolean existsThisWeekResource(String userId,String resourceId)throws Exception;
	
	/**
	 * @Description: 课程表
	 * @Author wanbo
	 * @DateTime 2020/04/13
	 */
	List<EduTimetableVo> timetableList(ReqEduTimetable req) throws Exception;

	/**
	 * @Description: 课程表资源
	 * @Author wanbo
	 * @DateTime 2020/04/13
	 */
	List<EduResourceVo> timetableResource(ReqEduTimetableResource req) throws Exception;
	
}
