package com.ailoxi.vision.dao;

import com.luoxi.api.vision.protocol.ReqRemoveVisionPlan;
import com.luoxi.api.vision.protocol.ReqSaveVisionPlan;
import com.luoxi.api.vision.protocol.ReqVisionPlanInfo;
import com.luoxi.api.vision.protocol.ReqVisionPlanList;
import com.luoxi.base.RespPaging;
import com.luoxi.model.VisionPlan;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author nicely
 * @Description
 * @date 2021年05月18日14:43
 */
public interface VisionPlanMapper extends Mapper<VisionPlan> {

    /**
     * 查询计划列表
     * @param req
     * @return
     * @throws Exception
     */
    List<ReqVisionPlanList.RespVisionPlanList> getVisionPlanList(Map<String,Object> req) throws Exception;

    /**
     * 获取计划详情
     * @param req
     * @return
     * @throws Exception
     */
    ReqVisionPlanInfo.RespVisionPlanInfo getVisionPlanInfo(Map<String,Object> req) throws Exception;

    /**
     * 保存计划
     * @param req
     * @return
     * @throws Exception
     */
    int saveVisionPlan(Map<String,Object> req) throws Exception;

    /**
     * 新增计划
     * @param req
     * @return
     * @throws Exception
     */
    int insertVisionPlan(Map<String,Object> req) throws Exception;

    /**
     * 删除计划
     * @param list
     * @return
     * @throws Exception
     */
    int removeVisionPlan(List<ReqRemoveVisionPlan> list) throws Exception;
}
