/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.luoxi.api.businessContent.protocol.ReqBusinessContentInfo.RespBusinessContentInfo;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentList.RespBusinessContentList;
import com.luoxi.api.businessContent.protocol.ReqRemoveBusinessContent;
import com.luoxi.model.BusinessContent;

import tk.mybatis.mapper.common.Mapper;

public interface BusinessContentMapper extends Mapper<BusinessContent>{
	
	BusinessContent getBusinessContent(Map<String, Object> map);
	
	/**
	 * @Description: 内容商内容详情
	 * @Author wanbo
	 * @DateTime 2019/11/25
	 */
	RespBusinessContentInfo businessContentInfo(Map<String, Object> map);
	
	/**
	 * @Description: 内容商内容列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespBusinessContentList> businessContentList(Map<String, Object> map);
	
	/**
	 * @Description: 删除内容商内容
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeBusinessContent(@Param("list")List<ReqRemoveBusinessContent> list);
	
	/**
	 * @Description: 修改父级节点
	 * @Author wanbo
	 * @DateTime 2020/02/27
	 */
	@Update("update t_business_content set parent_id=#{parentId} where business_content_id=#{businessContentId}")
	int updBusinessContentParent(@Param("businessContentId")String businessContentId,@Param("parentId")String parentId);

	
}
