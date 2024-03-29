package com.luoxi.api.vision;

import java.util.List;

import com.luoxi.api.vision.protocol.ReqRemoveVisionTest;
import com.luoxi.api.vision.protocol.ReqSaveVisionReport;
import com.luoxi.api.vision.protocol.ReqSaveVisionTest;
import com.luoxi.api.vision.protocol.ReqSaveVisionTest.RespSaveVisionTest;
import com.luoxi.api.vision.protocol.ReqVisionTestInfo;
import com.luoxi.api.vision.protocol.ReqVisionTestInfo.RespVisionTestInfo;
import com.luoxi.api.vision.protocol.ReqVisionTestList;
import com.luoxi.api.vision.protocol.ReqVisionTestList.RespVisionTestList;
import com.luoxi.base.RespPaging;

/**
 * @author EdisonFeng
 * @Description 类IVisionTest, 视力测试活动接口类，用于rpc服务
 * @DateTime 2021年4月21日
 * Copyright(c) 2021. All Rights Reserved
 */
public interface IVisionTest {
	/**
	 * @Description: 查询视力测试活动列表
	 * @Author Edison F.
	 * @DateTime 2021/04/20
	 */
	RespPaging<RespVisionTestList> getVisionTestList(ReqVisionTestList req) throws Exception;
	
	/**
	 * @Description: 查询视力测试活动详情
	 * @Author Edison F.
	 * @DateTime 2021/04/20
	 */
	RespVisionTestInfo getVisionTestInfo(ReqVisionTestInfo req) throws Exception;
	
	/**
	 * @return 
	 * @Description: 增加、修改视力测试活动信息
	 * @Author Edison F.
	 * @DateTime 2021/04/20
	 */
	RespSaveVisionTest saveVisionTest(ReqSaveVisionTest req) throws Exception;
	
	/**
	 * @Description: 增加测试活动信息
	 * @Author Edison F.
	 * @DateTime 2021/05/03
	 */
	RespSaveVisionTest insertVisionTest(ReqSaveVisionTest req) throws Exception;
	
	/**
	 * @Description: 删除视力测试活动信息
	 * @Author Edison F.
	 * @DateTime 2021/04/20
	 */
	String removeVisionTest(List<ReqRemoveVisionTest> tests) throws Exception;
}
