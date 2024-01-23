package com.luoxi.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.grade.IGradeService;
import com.luoxi.api.grade.protocol.ReqGradeList.RespGradeList;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"年级"})
@RestController
@RequestMapping("api/grade")
public class GradeController extends BaseController{
	
	@DubboReference
	private IGradeService gradeService;
	
	@ApiOperation(value="年级列表")
	@PostMapping("gradeList")
	public Result<List<RespGradeList>> gradeList() throws Exception {
		return ResultMessage.SUCCESS.result(gradeService.gradeList());
	}
	
}
