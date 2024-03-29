package com.luoxi.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.luoxi.api.device.IDeviceService;
import com.luoxi.api.device.protocol.ReqDeviceInfo;
import com.luoxi.api.device.protocol.ReqDeviceInfo.RespDeviceInfo;
import com.luoxi.api.device.protocol.ReqDeviceList;
import com.luoxi.api.device.protocol.ReqDeviceList.RespDeviceList;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

@ApiModule(module = Module.DEVICE)
@Controller
@RequestMapping("device")
public class DeviceController extends BaseController{
	
	@DubboReference IDeviceService deviceService;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/device/list";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String deviceId) throws Exception {
		RespDeviceInfo info = new RespDeviceInfo();
		if(StringUtils.isNotBlank(deviceId)) {
			info = deviceService.deviceInfo(getChannelId(), new ReqDeviceInfo().setDeviceId(deviceId));
		}
		request.setAttribute("info", info);
		return "page/device/info";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("deviceList")
	public Result<RespPaging<RespDeviceList>> deviceList(@RequestBody ReqDeviceList req) throws Exception {
		req.setChannelId(getChannelId());
		return ResultMessage.SUCCESS.result(deviceService.deviceList(req));
	}
	
	@ResponseBody
	@RequestMapping("deviceInfo")
	public Result<RespDeviceInfo> deviceInfo(@RequestBody ReqDeviceInfo req) throws Exception {
		return ResultMessage.SUCCESS.result(deviceService.deviceInfo(getChannelId(), req));
	}
	
	/**
	@RequestMapping("downloadTemplate")
	public void  download(HttpServletResponse response, HttpServletRequest request) throws Exception{
		ExcelWriter writer = ExcelUtil.getWriter("classpath:/static/excel/设备.xlsx");
		//response为HttpServletResponse对象
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		//test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
		response.setHeader("Content-Disposition","attachment;filename=device.xlsx"); 
		ServletOutputStream out = response.getOutputStream(); 

		writer.flush(out, true);
		// 关闭writer，释放内存
		writer.close();
		//此处记得关闭输出Servlet流
		IoUtil.close(out);
	}*/
	
	
	
}
