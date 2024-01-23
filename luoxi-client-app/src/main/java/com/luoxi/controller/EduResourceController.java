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
import com.luoxi.api.eduResource.IEduResourceService;
import com.luoxi.api.eduResource.protocol.ReqEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqEduResourceDetail.RespEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqEduResourceInfo;
import com.luoxi.api.eduResource.protocol.ReqRecommendResource;
import com.luoxi.api.eduResource.protocol.ReqSearchResource;
import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.api.fmbrowse.IFmBrowseService;
import com.luoxi.api.fmbrowse.protocol.ReqAddFmBrowse;
import com.luoxi.api.fmbrowse.protocol.ReqFmBrowseList;
import com.luoxi.api.fmbrowse.protocol.ReqFmBrowseList.RespFmBrowseList;
import com.luoxi.api.fmcollect.IFmCollectService;
import com.luoxi.api.fmcollect.protocol.ReqAddFmCollect;
import com.luoxi.api.fmcollect.protocol.ReqFmCollectList;
import com.luoxi.api.fmcollect.protocol.ReqFmCollectList.RespFmCollectList;
import com.luoxi.base.BaseController;
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
	private IEduResourceService eduResourceService;
	@DubboReference
	private IFmBrowseService fmBrowseService;
	@DubboReference
	private IFmCollectService fmCollectService;
	
	@ApiOperation(value="资源信息")
	@PostMapping("eduResourceInfo")
	public Result<EduResourceVo> eduResourceInfo(@Valid @RequestBody ReqEduResourceInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(eduResourceService.eduResourceInfo(req.setUserId(getUserId())));
	}
	
	@ApiOperation(value="资源搜索")
	@PostMapping("searchResource")
	public Result<RespPaging<EduResourceVo>> searchResource(@Valid @RequestBody ReqSearchResource req) throws Exception {
		req.setUserId(getUserId());
		return ResultMessage.SUCCESS.result(eduResourceService.searchResource(req));
	}
	
	@ApiOperation(value="资源推荐")
	@PostMapping("recommendResource")
	public Result<RespPaging<EduResourceVo>> recommendResource(@Valid @RequestBody ReqRecommendResource req) throws Exception {
		req.setUserId(getUserId());
		return ResultMessage.SUCCESS.result(eduResourceService.recommendResource(req));
	}
	
	@ApiOperation(value="筛选条件")
	@PostMapping("eduCondition")
	public Result<List<RespEduCondition>> eduCondition() throws Exception {
		return ResultMessage.SUCCESS.result(eduConditionService.eduCondition());
	}
	
	@ApiOperation(value="筛选条件-其他")
	@PostMapping("eduConditionOther")
	public Result<List<RespEduCondition>> eduConditionOther() throws Exception {
		return ResultMessage.SUCCESS.result(eduConditionService.eduConditionOther());
	}
	
	@ApiOperation(value="筛选条件-主题")
	@PostMapping("eduConditionTheme")
	public Result<List<RespEduCondition>> eduConditionTheme(@Valid @RequestBody ReqEduCondition req) throws Exception {
		return ResultMessage.SUCCESS.result(eduConditionService.eduConditionTheme(req));
	}
	
	@ApiOperation(value="资源详情")
	@PostMapping("getEduResourceDetail")
	public Result<RespPaging<RespEduResourceDetail>> getEduResourceDetail(@Valid @RequestBody ReqEduResourceDetail req) throws Exception {
		return ResultMessage.SUCCESS.result(eduResourceService.getEduResourceDetail(req));
	}
	
	@ApiOperation(value="浏览新增")
	@PostMapping("addFmBrowse")
	public Result<?> addFmBrowse(@Valid @RequestBody ReqAddFmBrowse req) throws Exception {
		fmBrowseService.addFmBrowse(getUserId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value="浏览列表")
	@PostMapping("fmBrowseList")
	public Result<RespPaging<RespFmBrowseList>> fmBrowseList(@Valid @RequestBody ReqFmBrowseList req) throws Exception {
		return ResultMessage.SUCCESS.result(fmBrowseService.fmBrowseList(getUserId(), req));
	}
	
	@ApiOperation(value="收藏新增")
	@PostMapping("addFmCollect")
	public Result<?> addFmCollect(@Valid @RequestBody ReqAddFmCollect req) throws Exception {
		fmCollectService.addFmCollect(getUserId(), req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperation(value="收藏列表")
	@PostMapping("fmCollectList")
	public Result<RespPaging<RespFmCollectList>> fmCollectList(@Valid @RequestBody ReqFmCollectList req) throws Exception {
		return ResultMessage.SUCCESS.result(fmCollectService.fmCollectList(getUserId(), req));
	}
	
}
