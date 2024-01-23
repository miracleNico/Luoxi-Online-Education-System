/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luoxi.api.eduResource.protocol.ReqOpenEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqOpenRecommendResource;
import com.luoxi.api.eduResource.protocol.ReqOpenSearchResource;
import com.luoxi.api.eduResource.vo.OpenEduResourceBaseVo;
import com.luoxi.api.eduResource.vo.OpenEduResourceInfoVo;
import com.luoxi.model.EduResource;

import tk.mybatis.mapper.common.Mapper;

public interface OpenEduResourceMapper extends Mapper<EduResource>{
	
	/**
	 * @Description: 搜索资源
	 * @Author wanbo
	 * @DateTime 2020/04/10
	 */
	List<OpenEduResourceBaseVo> searchResource(ReqOpenSearchResource req);
	
	/**
	 * @Description: 推荐资源
	 * @Author wanbo
	 * @DateTime 2020/04/05
	 */
	List<OpenEduResourceBaseVo> recommendResource(ReqOpenRecommendResource req);
	
	/**
	 * @Description: 资源详情
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	OpenEduResourceInfoVo eduResourceInfo(@Param("resourceId")String resourceId);
	
	/**
	 * @Description: 资源明细列表
	 * @Author wanbo
	 * @DateTime 2020/03/31
	 */
	List<OpenEduResourceInfoVo> eduResourceDetail(ReqOpenEduResourceDetail req);
}
