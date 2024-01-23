/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luoxi.api.eduResource.protocol.ReqEduResourceDetail.RespEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqEduResourceInfo;
import com.luoxi.api.eduResource.protocol.ReqRecommendResource;
import com.luoxi.api.eduResource.protocol.ReqSearchResource;
import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.model.EduResource;

import tk.mybatis.mapper.common.Mapper;

public interface EduResourceMapper extends Mapper<EduResource>{
	
	/**
	 * @Description: 资源信息
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	EduResourceVo eduResourceInfo(ReqEduResourceInfo req);
	
	/**
	 * @Description: 搜索资源
	 * @Author wanbo
	 * @DateTime 2020/04/10
	 */
	List<EduResourceVo> searchResource(ReqSearchResource req);
	
	/**
	 * @Description: 推荐资源
	 * @Author wanbo
	 * @DateTime 2020/04/05
	 */
	List<EduResourceVo> recommendResource(ReqRecommendResource req);
	
	/**
	 * @Description: 资源详情
	 * @Author wanbo
	 * @DateTime 2020/03/31
	 */
	List<RespEduResourceDetail> getEduResourceDetail(@Param("parentId")String parentId);
}
