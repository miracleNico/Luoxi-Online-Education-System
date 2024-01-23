package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.snyc.ISyncService;
import com.luoxi.api.snyc.protocol.ReqSyncPull;
import com.luoxi.api.snyc.protocol.ReqSyncPush;
import com.luoxi.base.BaseController;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"同步"})
@RestController
@RequestMapping("api/sync")
public class SyncController extends BaseController{
	
	@DubboReference
	private ISyncService syncService;
	
	@ApiOperation(value="全量拉取")
	@PostMapping("syncPull")
	public Result<?> syncPull(@Valid @RequestBody ReqSyncPull requestParam) throws Exception {
		return ResultMessage.SUCCESS.result(syncService.syncPull(getUserId(),requestParam));
	}
	
	@ApiOperation(value="增量推送")
	@PostMapping("syncPush")
	public Result<?> syncPush(@Valid @RequestBody ReqSyncPush requestParam) throws Exception {
		syncService.syncPush(getUserId(),requestParam);
		return ResultMessage.SUCCESS.result();
	}
	
	
}
