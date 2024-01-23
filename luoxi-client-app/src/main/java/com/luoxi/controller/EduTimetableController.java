package com.luoxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.api.eduTimetable.IEduTimetableService;
import com.luoxi.api.eduTimetable.protocol.ReqEduTimetable;
import com.luoxi.api.eduTimetable.protocol.ReqEduTimetableResource;
import com.luoxi.api.eduTimetable.vo.EduTimetableVo;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"课程表"})
@RestController
@RequestMapping("api/eduTimetable")
public class EduTimetableController extends BaseController{
	
	@DubboReference
	private IEduTimetableService eduTimetableService;
	
	@ApiOperation(value="课程表")
	@PostMapping("timetableList")
	public Result<List<EduTimetableVo>> timetableList() throws Exception {
		return ResultMessage.SUCCESS.result(eduTimetableService.timetableList(new ReqEduTimetable().setUserId(getUserId())));
	}
	
	@ApiOperation(value="课程表资源")
	@PostMapping("timetableResource")
	public Result<List<EduResourceVo>> timetableResource(@Valid @RequestBody ReqEduTimetableResource req) throws Exception {
		req.setUserId(getUserId());
		return ResultMessage.SUCCESS.result(eduTimetableService.timetableResource(req));
	}
	
}
