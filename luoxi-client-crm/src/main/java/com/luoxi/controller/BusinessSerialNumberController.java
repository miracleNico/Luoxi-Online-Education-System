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
import com.luoxi.api.businessSerialNumber.IBusinessSerialNumberService;
import com.luoxi.api.businessSerialNumber.protocol.ReqBusinessSerialNumberList;
import com.luoxi.api.businessSerialNumber.protocol.ReqBusinessSerialNumberList.RespBusinessSerialNumberList;
import com.luoxi.api.businessSerialNumber.protocol.ReqImportBusinessSerialNumber;
import com.luoxi.api.businessSerialNumber.protocol.ReqRemoveBusinessSerialNumber;
import com.luoxi.api.businessSerialNumber.protocol.ReqRestoreSn;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;
import com.luoxi.vo.ImportMsgVo;
import com.luoxi.vo.ImportMsgVo.ImportFailVo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@ApiModule(module = Module.BUSINESS_SN)
@Controller
@RequestMapping("businessSn")
public class BusinessSerialNumberController extends BaseController{
	
	@DubboReference IBusinessSerialNumberService businessSerialNumberService;
	
	@RequestMapping("listPage")
	public String listPage(String businessId,HttpServletRequest request) {
		request.setAttribute("businessId", businessId);
		return "page/businessSn/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("businessSnList")
	public Result<RespPaging<RespBusinessSerialNumberList>> businessSnList(@RequestBody ReqBusinessSerialNumberList req) throws Exception {
		return ResultMessage.SUCCESS.result(businessSerialNumberService.businessSerialNumberList(req));
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeBusinessSn")
	public Result<?> removeBusinessSn(@Valid @RequestBody List<ReqRemoveBusinessSerialNumber> req) throws Exception {
		businessSerialNumberService.removeBusinessSerialNumber(req);
		return ResultMessage.SUCCESS.result();
	}
	
	/**
	 * @Description: 恢复激活码-只恢复未使用成功的激活码
	 * @Author wanbo
	 * @DateTime 2020/03/11
	 */
	@ApiOperLog(action = ACTION.UPDATE)
	@ResponseBody
	@RequestMapping("restoreSn")
	public Result<?> restoreSn(@Valid @RequestBody List<ReqRestoreSn> req) throws Exception {
		businessSerialNumberService.restoreSn(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.IMPORT)
	@ResponseBody
	@RequestMapping(value = "importBusinessSn", headers = "content-type=multipart/form-data")
	public Result<?> importBusinessSerialNumber(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
		ExcelReader reader = ExcelUtil.getReader(file.getInputStream(),0);
		
		List<ReqImportBusinessSerialNumber> businessSerialNumbers = reader.read(1,2,ReqImportBusinessSerialNumber.class);
		
		if(CollectionUtil.isEmpty(businessSerialNumbers))
			throw LxException.of().setMsg("导入数据不能为空");
		
		ImportMsgVo importMsgVo = new ImportMsgVo();
		List<ImportFailVo> importErrorVos = new ArrayList<ImportFailVo>();
		importMsgVo.setTotalCount(businessSerialNumbers.size());
		
		Integer row = 3;
		for (ReqImportBusinessSerialNumber businessSerialNumber : businessSerialNumbers) {
			
			boolean flag = true;
			
			if(StringUtils.isBlank(businessSerialNumber.getBusinessCode())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("内容商代码不能为空"));
				flag = false;
			}
			
			if(StringUtils.isBlank(businessSerialNumber.getBusinessSnCode())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("内容商激活码不能为空"));
				flag = false;
			}
			
			if(StringUtils.isBlank(businessSerialNumber.getCallType())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("调用类型不能为空"));
				flag = false;
			}
			
			if(businessSerialNumber.getMaxUseNumber()<1) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("最大使用次数不能为空"));
				flag = false;
			}
			
			if(!flag) {
				importMsgVo.setFailCount(importMsgVo.getFailCount()+1);
				row++;
				continue;
			}
			
			try {
				businessSerialNumberService.importBusinessSerialNumber(businessSerialNumber);
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
