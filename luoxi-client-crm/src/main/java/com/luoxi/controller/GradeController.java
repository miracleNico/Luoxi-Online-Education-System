package com.luoxi.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiPermission;
import com.luoxi.aop.ApiPermission.AUTH;
import com.luoxi.api.grade.IGradeService;
import com.luoxi.api.grade.protocol.ReqGradeList.RespGradeList;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.GRADE)
@Controller
@RequestMapping("grade")
public class GradeController extends BaseController{
	
	@DubboReference IGradeService gradeService;
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("gradeSelected")
	public Result<List<RespGradeList>> gradeSelected() throws Exception {
		return ResultMessage.SUCCESS.result(gradeService.gradeList());
	}
	
	
}
