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
import com.luoxi.api.subject.ISubjectService;
import com.luoxi.api.subject.protocol.ReqSubjectList.RespSubjectList;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.SUBJECT)
@Controller
@RequestMapping("subject")
public class SubjectController extends BaseController{
	
	@DubboReference ISubjectService subjectService;
	
	@ApiPermission(AUTH.OPEN)
	@ResponseBody
	@RequestMapping("subjectSelected")
	public Result<List<RespSubjectList>> subjectSelected() throws Exception {
		return ResultMessage.SUCCESS.result(subjectService.subjectList());
	}
	
	
}
