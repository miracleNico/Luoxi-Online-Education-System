package com.luoxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.eduDirectory.protocol.ReqEduCondition;
import com.luoxi.api.eduDirectory.protocol.ReqEduCondition.RespEduCondition;
import com.luoxi.api.eduResource.IEduConditionService;
import com.luoxi.api.eduResource.IOpenEduResourceService;
import com.luoxi.api.eduResource.protocol.ReqOpenEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqOpenEduResourceInfo;
import com.luoxi.api.eduResource.protocol.ReqOpenRecommendResource;
import com.luoxi.api.eduResource.protocol.ReqOpenSearchResource;
import com.luoxi.api.eduResource.vo.OpenEduResourceBaseVo;
import com.luoxi.api.eduResource.vo.OpenEduResourceInfoVo;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"学习资源"})
@RestController
@RequestMapping("api/eduResource")
public class EduResourceController extends BaseController{
	
	@DubboReference
	private IEduConditionService eduConditionService;
	@DubboReference
	private IOpenEduResourceService openEduResourceService;
	
	@ApiOperation(value="筛选条件")
	@PostMapping("eduCondition")
	public Result<List<RespEduCondition>> eduCondition(@Valid @RequestBody ReqEduCondition req) throws Exception {
		return ResultMessage.SUCCESS.result(eduConditionService.eduConditionDynamicAll(req));
	}
	
	@ApiOperation(value="推荐资源")
	@PostMapping("recommendResource")
	public Result<List<OpenEduResourceBaseVo>> recommendResource(@Valid @RequestBody ReqOpenRecommendResource req) throws Exception {
		return ResultMessage.SUCCESS.result(openEduResourceService.recommendResource(req));
	}
	
	@ApiOperation(value="资源搜索")
	@PostMapping("searchResource")
	public Result<RespPaging<OpenEduResourceBaseVo>> searchResource(@Valid @RequestBody ReqOpenSearchResource req) throws Exception {
		return ResultMessage.SUCCESS.result(openEduResourceService.searchResource(req));
	}
	
	@ApiOperation(value="资源详情")
	@PostMapping("eduResourceInfo")
	public Result<OpenEduResourceInfoVo> eduResourceInfo(@Valid @RequestBody ReqOpenEduResourceInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(openEduResourceService.eduResourceInfo(req));
	}
	
	@ApiOperation(value="资源明细列表")
	@PostMapping("eduResourceDetail")
	public Result<RespPaging<OpenEduResourceInfoVo>> eduResourceDetail(@Valid @RequestBody ReqOpenEduResourceDetail req) throws Exception {
		return ResultMessage.SUCCESS.result(openEduResourceService.eduResourceDetail(req));
	}
	
	
}
