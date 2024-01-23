package com.luoxi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.api.openAppBusinessCdk.IOpenAppBusinessCdkService;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkImport;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkList;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkList.RespOpenAppBusinessCdkList;
import com.luoxi.api.openAppBusinessCdk.protocol.ReqOpenAppBusinessCdkRemove;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;
import com.luoxi.model.OpenAppBusinessCdk;
import com.luoxi.vo.ImportMsgVo;
import com.luoxi.vo.ImportMsgVo.ImportFailVo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@ApiModule(module = Module.OPEN_APP_BUSINESS)
@Controller
@RequestMapping("openAppBusinessCdk")
public class OpenAppBusinessCdkController extends BaseController{
	
	@DubboReference
	private IOpenAppBusinessCdkService openAppBusinessCdkService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/openAppBusinessCdk/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("appBusinessCdkList")
	public Result<RespPaging<RespOpenAppBusinessCdkList>> appBusinessCdkList(@RequestBody ReqOpenAppBusinessCdkList req) throws Exception {
		return ResultMessage.SUCCESS.result(openAppBusinessCdkService.appBusinessCdkList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String openAppBusinessCdkId) throws Exception {
		OpenAppBusinessCdk info = new OpenAppBusinessCdk();
		if(StringUtils.isNotBlank(openAppBusinessCdkId)) {
			info = openAppBusinessCdkService.info(openAppBusinessCdkId);
		}
		request.setAttribute("info", info);
		return "page/openAppBusinessCdk/edit";
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveAppBusinessCdk")
	public Result<?> saveAppBusinessCdk(@RequestBody OpenAppBusinessCdk req) throws Exception {
		openAppBusinessCdkService.saveAppBusinessCdk(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeAppBusinessCdk")
	public Result<?> removeAppBusinessCdk(@Valid @RequestBody List<ReqOpenAppBusinessCdkRemove> req) throws Exception {
		openAppBusinessCdkService.removeAppBusinessCdk(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.IMPORT)
	@ResponseBody
	@RequestMapping(value = "importAppBusinessCdk", headers = "content-type=multipart/form-data")
	public Result<?> importAppBusinessCdk(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
		ExcelReader reader = ExcelUtil.getReader(file.getInputStream(),0);
		
		List<ReqOpenAppBusinessCdkImport> cdks = reader.read(1,2,ReqOpenAppBusinessCdkImport.class);
		
		if(CollectionUtil.isEmpty(cdks)) throw LxException.of().setMsg("导入数据不能为空");
		
		ImportMsgVo importMsgVo = new ImportMsgVo();
		List<ImportFailVo> importErrorVos = new ArrayList<ImportFailVo>();
		importMsgVo.setTotalCount(cdks.size());
		
		Integer row = 3;
		for (ReqOpenAppBusinessCdkImport cdk : cdks) {
			
			boolean flag = true;
			
			if(StringUtils.isBlank(cdk.getCdk())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("CDK不能为空"));
				flag = false;
			}
			
			if(StringUtils.isBlank(cdk.getChannelName())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("渠道名称不能为空"));
				flag = false;
			}
			
			if(StringUtils.isBlank(cdk.getOpenAppName())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("应用名称不能为空"));
				flag = false;
			}
			
			if(StringUtils.isBlank(cdk.getOpenAppBusinessName())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("业务名称不能为空"));
				flag = false;
			}
			
			if(!flag) {
				importMsgVo.setFailCount(importMsgVo.getFailCount()+1);
				row++;
				continue;
			}
			
			try {
				openAppBusinessCdkService.importAppBusinessCdk(cdk);
				importMsgVo.setSuccessCount(importMsgVo.getSuccessCount()+1);
			} catch (LxException e) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg(e.getResult().getMsg()));
				importMsgVo.setFailCount(importMsgVo.getFailCount()+1);
			}
			row++;
		};
		
		importMsgVo.setFails(importErrorVos);
		return ResultMessage.SUCCESS.result(importMsgVo);
	}
	
	
}
