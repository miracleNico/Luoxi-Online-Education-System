package com.luoxi.api.vision;

import java.util.List;

import com.luoxi.api.vision.protocol.ReqVisionCondition;
import com.luoxi.api.vision.protocol.ReqVisionCondition.RespVisionCondition;

/**
 * @author EdisonFeng
 * @Description: 视力报告筛选条件
 * @Date 2021年4月21日
 */
public interface IVisionCondition {
	List<RespVisionCondition> getVisionCondition(ReqVisionCondition req) throws Exception;
}
