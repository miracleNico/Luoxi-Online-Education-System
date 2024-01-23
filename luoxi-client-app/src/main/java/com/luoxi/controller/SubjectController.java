package com.luoxi.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.subject.ISubjectService;
import com.luoxi.api.subject.protocol.ReqSubjectList.RespSubjectList;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"科目"})
@RestController
@RequestMapping("api/subject")
public class SubjectController extends BaseController{
	
	@DubboReference
	private ISubjectService subjectService;
	
	@ApiOperation(value="科目列表")
	@PostMapping("subjectList")
	public Result<List<RespSubjectList>> subjectList() throws Exception {
		return ResultMessage.SUCCESS.result(subjectService.subjectList());
	}
	
}
