package com.luoxi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.api.permission.IPermissionService;
import com.luoxi.api.permission.protocol.ReqPermissionInfo;
import com.luoxi.api.permission.protocol.ReqPermissionInfo.RespPermissionInfo;
import com.luoxi.api.permission.protocol.ReqPermissionList;
import com.luoxi.api.permission.protocol.ReqPermissionList.RespPermissionList;
import com.luoxi.api.permission.protocol.ReqRemovePermission;
import com.luoxi.api.permission.protocol.ReqSavePermission;
import com.luoxi.api.permission.protocol.ReqUpdPermissionParent;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import cn.hutool.core.util.StrUtil;

@ApiModule(module = Module.PERMISSION)
@Controller
@RequestMapping("permission")
public class PermissionController extends BaseController{
	
	@DubboReference IPermissionService permissionService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/permission/list";
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String permissionId,String parentId) throws Exception {
		RespPermissionInfo info = new RespPermissionInfo();
		if(StringUtils.isNotBlank(permissionId)) {
			info = permissionService.permissionInfo(new ReqPermissionInfo().setPermissionId(permissionId));
			if(info==null) 
				info = new RespPermissionInfo();
		}
		if(StrUtil.isEmpty(info.getParentId())) {
			info.setParentId(parentId);
		}
		request.setAttribute("info", info);
		return "page/permission/edit";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("permissionList")
	public Result<List<RespPermissionList>> permissionList(@RequestBody ReqPermissionList req) throws Exception {
		return ResultMessage.SUCCESS.result(permissionService.permissionList(req));
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("savePermission")
	public Result<?> savePermission(@Valid @RequestBody ReqSavePermission req) throws Exception {
		permissionService.savePermission(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removePermission")
	public Result<?> removePermission(@Valid @RequestBody List<ReqRemovePermission> req) throws Exception {
		permissionService.removePermission(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("updPermissionParent")
	public Result<?> updPermissionParent(@Valid @RequestBody List<ReqUpdPermissionParent> req) throws Exception {
		permissionService.updPermissionParent(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ResponseBody
	@RequestMapping("permissionTree")
	public Result<List<Map<String, Object>>> permissionTree(@RequestBody Map<String,String> map) throws Exception {
		return ResultMessage.SUCCESS.result(permissionService.permissionTree(map.get("roleId")));
	}
	
	
}
