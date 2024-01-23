package com.luoxi.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.family.IFamilyMemberService;
import com.luoxi.api.family.protocol.ReqFamilyMemberInfo;
import com.luoxi.api.family.protocol.ReqFamilyMemberInfo.RespFamilyMemberInfo;
import com.luoxi.api.family.protocol.ReqFamilyMemberList.RespFamilyMemberList;
import com.luoxi.api.family.protocol.ReqGrowthRecord;
import com.luoxi.api.family.protocol.ReqGrowthRecord.RespGrowthRecord;
import com.luoxi.api.family.protocol.ReqRemoveFamilyMember;
import com.luoxi.api.family.protocol.ReqSaveFamilyMember;
import com.luoxi.api.family.protocol.ReqSaveFamilyMember.RespSaveFamilyMember;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"家庭成员"})
@RestController
@RequestMapping("api/familyMember")
public class FamilyMemberController extends BaseController{
	
	@DubboReference
	private IFamilyMemberService familyMemberSerivce;
	
	@ApiOperation(value="成长记录")
	@PostMapping("growthRecord")
	public Result<RespGrowthRecord> growthRecord(@Valid @RequestBody ReqGrowthRecord req) throws Exception {
		return ResultMessage.SUCCESS.result(familyMemberSerivce.growthRecord(req.setUserId(getUserId())));
	}
	
	
	@ApiOperation(value="家庭成员列表")
	@PostMapping("familyMemberList")
	public Result<List<RespFamilyMemberList>> familyMemberList() throws Exception {
		return ResultMessage.SUCCESS.result(familyMemberSerivce.familyMemberList(getUserId()));
	}
	
	
	@ApiOperation(value="家庭成员信息")
	@PostMapping("familyMemberInfo")
	public Result<RespFamilyMemberInfo> familyMemberInfo(@Valid @RequestBody ReqFamilyMemberInfo requestParam) throws Exception {
		return ResultMessage.SUCCESS.result(familyMemberSerivce.familyMemberInfo(requestParam));
	}
	
	
	@ApiOperation(value="保存家庭成员")
	@PostMapping("saveFamilyMember")
	public Result<RespSaveFamilyMember> saveFamilyMember(@Valid @RequestBody ReqSaveFamilyMember requestParam) throws Exception {
		String userId = getUserId();
		requestParam.setCreateUser(userId).setUpdateUser(userId).setJoinUserId(userId);
		return ResultMessage.SUCCESS.result(familyMemberSerivce.saveFamilyMember(requestParam));
	}
	
	@ApiOperation(value="删除家庭成员")
	@PostMapping("removeFamilyMember")
	public Result<?> removeFamilyMember(@Valid @RequestBody ReqRemoveFamilyMember requestParam) throws Exception {
		requestParam.setUserId(getUserId());
		familyMemberSerivce.removeFamilyMember(requestParam);
		return ResultMessage.SUCCESS.result();
	}
	
}
