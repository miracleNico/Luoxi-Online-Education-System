package com.luoxi.server.service;

import java.util.Date;
import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.api.eduTimetable.IEduTimetableService;
import com.luoxi.api.eduTimetable.protocol.ReqEduTimetable;
import com.luoxi.api.eduTimetable.protocol.ReqEduTimetableResource;
import com.luoxi.api.eduTimetable.vo.EduTimetableVo;
import com.luoxi.server.dao.EduTimetableMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.date.DateUtil;

@DubboService
public class EduTimetableService implements IEduTimetableService{
	
	@Autowired
	private EduTimetableMapper eduTimetableMapper;
	@Autowired
	private CosUtil cosUtil;
	
	@Override
	public boolean existsThisWeekResource(String userId, String resourceId) throws Exception {
		return eduTimetableMapper.existsThisWeekResource(userId, resourceId);
	}
	
	@Override
	public List<EduTimetableVo> timetableList(ReqEduTimetable req) throws Exception{
		List<EduTimetableVo> list = eduTimetableMapper.timetableList(req);
		for (EduTimetableVo eduTimetableVo : list) {
			if(eduTimetableVo.getStartDate().equals(DateUtil.beginOfWeek(new Date()).toDateStr())) {
				eduTimetableVo.setResourceList(timetableResource(new ReqEduTimetableResource().setUserId(req.getUserId()).setTimetableId(eduTimetableVo.getTimetableId())));
			}
		}
		return list;
	}
	
	/**
	 * @Description: 课程表资源
	 * @Author wanbo
	 * @DateTime 2020/04/13
	 */
	@Override
	public List<EduResourceVo> timetableResource(ReqEduTimetableResource req) throws Exception {
		List<EduResourceVo> list = eduTimetableMapper.timetableResource(req);
		for (EduResourceVo eduResourceVo : list) {
			eduResourceVo.setCoverUrl(cosUtil.getAccessUrl(eduResourceVo.getCoverUrl()));
			eduResourceVo.setFileUrl(cosUtil.getAccessUrl(eduResourceVo.getFileUrl()));
		}
		return list;
	}

}
