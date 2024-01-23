package com.luoxi.api.eduResource;

import com.luoxi.api.eduResource.protocol.ReqEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqEduResourceDetail.RespEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqEduResourceInfo;
import com.luoxi.api.eduResource.protocol.ReqRecommendResource;
import com.luoxi.api.eduResource.protocol.ReqSearchResource;
import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.base.RespPaging;

public interface IEduResourceService {
	
	/**
	 * @Description: 资源信息
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	EduResourceVo eduResourceInfo(ReqEduResourceInfo req)throws Exception;
	
	/**
	 * @Description: 搜索资源
	 * @Author wanbo
	 * @DateTime 2020/04/05
	 */
	RespPaging<EduResourceVo> searchResource(ReqSearchResource req)throws Exception;
	
	/**
	 * @Description: 推荐资源
	 * @Author wanbo
	 * @DateTime 2020/04/05
	 */
	RespPaging<EduResourceVo> recommendResource(ReqRecommendResource req)throws Exception;
	
	/**
	 * @Description: 资源详情
	 * @Author wanbo
	 * @DateTime 2020/03/31
	 */
	RespPaging<RespEduResourceDetail> getEduResourceDetail(ReqEduResourceDetail req) throws Exception;
	
}
