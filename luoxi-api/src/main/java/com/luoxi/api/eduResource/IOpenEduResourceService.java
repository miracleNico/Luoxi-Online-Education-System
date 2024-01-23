package com.luoxi.api.eduResource;

import java.util.List;

import com.luoxi.api.eduResource.protocol.ReqOpenEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqOpenEduResourceInfo;
import com.luoxi.api.eduResource.protocol.ReqOpenRecommendResource;
import com.luoxi.api.eduResource.protocol.ReqOpenSearchResource;
import com.luoxi.api.eduResource.vo.OpenEduResourceBaseVo;
import com.luoxi.api.eduResource.vo.OpenEduResourceInfoVo;
import com.luoxi.base.RespPaging;

public interface IOpenEduResourceService {
	
	/**
	 * @Description: 搜索资源
	 * @Author wanbo
	 * @DateTime 2020/09/24
	 */
	RespPaging<OpenEduResourceBaseVo> searchResource(ReqOpenSearchResource req) throws Exception;
	
	/**
	 * @Description: 推荐资源
	 * @Author wanbo
	 * @DateTime 2020/09/24
	 */
	List<OpenEduResourceBaseVo> recommendResource(ReqOpenRecommendResource req) throws Exception;

	/**
	 * @Description: 资源详情
	 * @Author wanbo
	 * @DateTime 2020/09/24
	 */
	OpenEduResourceInfoVo eduResourceInfo(ReqOpenEduResourceInfo req) throws Exception;
	
	/**
	 * @Description: 资源明细列表
	 * @Author wanbo
	 * @DateTime 2020/03/31
	 */
	RespPaging<OpenEduResourceInfoVo> eduResourceDetail(ReqOpenEduResourceDetail req) throws Exception;
}
