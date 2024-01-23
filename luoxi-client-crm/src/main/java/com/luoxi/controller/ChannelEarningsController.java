package com.luoxi.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.luoxi.api.channelEarnings.IChannelEarningsService;
import com.luoxi.api.channelEarnings.protocol.ReqChannelEarningsList;
import com.luoxi.api.channelEarnings.protocol.ReqChannelEarningsList.RespChannelEarningsList;
import com.luoxi.api.channelEarnings.protocol.ReqImportChannelEarnings;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;
import com.luoxi.vo.ImportMsgVo;
import com.luoxi.vo.ImportMsgVo.ImportFailVo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@ApiModule(module = Module.APP_EARNINGS)
@Controller
@RequestMapping("channelEarnings")
public class ChannelEarningsController extends BaseController{
	
	@DubboReference IChannelEarningsService channelEarningsService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/channelEarnings/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("channelEarningsList")
	public Result<RespPaging<RespChannelEarningsList>> channelEarningsList(@RequestBody ReqChannelEarningsList req) throws Exception {
		return ResultMessage.SUCCESS.result(channelEarningsService.channelEarningsList(req));
	}
	
	@ApiOperLog(action = ACTION.IMPORT)
	@ResponseBody
	@RequestMapping(value = "importChannelEarnings", headers = "content-type=multipart/form-data")
	public Result<?> importChannelEarnings(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
		ExcelReader reader = ExcelUtil.getReader(file.getInputStream(),0);
		List<ReqImportChannelEarnings> list = reader.read(1,2,ReqImportChannelEarnings.class);
		reader.close();
		
		if(CollectionUtil.isEmpty(list)) throw LxException.of().setMsg("导入数据不能为空");
		
		ImportMsgVo importMsgVo = new ImportMsgVo();
		List<ImportFailVo> importErrorVos = new ArrayList<ImportFailVo>();
		importMsgVo.setTotalCount(list.size());
		
		Integer row = 3;
		for (ReqImportChannelEarnings channelEarnings : list) {
			
			boolean flag = true;
			
			if(StrUtil.isBlank(channelEarnings.getMonth())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("月份不能为空"));
				flag = false;
			}
			
			if(StrUtil.isBlank(channelEarnings.getThirdBusinessCode())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("第三方内容商代码不能为空"));
				flag = false;
			}
			
			if(StrUtil.isBlank(channelEarnings.getUserId())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("用户ID不能为空"));
				flag = false;
			}
			
			if(StrUtil.isBlank(channelEarnings.getOrderNumber())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("订单号不能为空"));
				flag = false;
			}
			
			if(StrUtil.isBlank(channelEarnings.getOrderPayTime())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("订单支付时间不能为空"));
				flag = false;
			}
			
			if(ObjectUtil.isNull(channelEarnings.getOrderPayAmount())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("订单支付金额不能为空"));
				flag = false;
			}
			
			if(ObjectUtil.isNull(channelEarnings.getOriginalEarningsAmount())) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg("分成金额不能为空"));
				flag = false;
			}
			
			if(!flag) {
				importMsgVo.setFailCount(importMsgVo.getFailCount()+1);
				row++;
				continue;
			}
			
			try {
				channelEarningsService.importChannelEarnings(channelEarnings);
				importMsgVo.setSuccessCount(importMsgVo.getSuccessCount()+1);
			} catch (LxException e) {
				importErrorVos.add(new ImportFailVo().setRow(row).setMsg(e.getResult().getMsg()));
				importMsgVo.setFailCount(importMsgVo.getFailCount()+1);
			}
			row++;
		}
		importMsgVo.setFails(importErrorVos);
		return ResultMessage.SUCCESS.result(importMsgVo);
	}
	
	
}
