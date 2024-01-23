package com.luoxi.server.service;


import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.eduResource.IEduResourceService;
import com.luoxi.api.eduResource.protocol.ReqEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqEduResourceDetail.RespEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqEduResourceInfo;
import com.luoxi.api.eduResource.protocol.ReqRecommendResource;
import com.luoxi.api.eduResource.protocol.ReqSearchResource;
import com.luoxi.api.eduResource.vo.EduResourceVo;
import com.luoxi.base.RespPaging;
import com.luoxi.server.dao.EduResourceMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;

@DubboService
public class EduResourceService implements IEduResourceService{

	@Autowired
	private EduResourceMapper eduResourceMapper;
	@Autowired
	private CosUtil cosUtil;
	
	/**
	 * @Description: 资源信息
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	@Override
	public EduResourceVo eduResourceInfo(ReqEduResourceInfo req) throws Exception {
		return eduResourceMapper.eduResourceInfo(req);
	}
	
	/**
	 * @Description: 资源搜索
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	@Override
	public RespPaging<EduResourceVo> searchResource(ReqSearchResource req) throws Exception {
		RespPaging<EduResourceVo> respPaging = new RespPaging<EduResourceVo>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<EduResourceVo> list = eduResourceMapper.searchResource(req);
		BeanUtil.copyProperties(new PageInfo<EduResourceVo>(list), respPaging);
		for (EduResourceVo eduResourceVo : respPaging.getList()) {
			eduResourceVo.setCoverUrl(cosUtil.getAccessUrl(eduResourceVo.getCoverUrl()));
			eduResourceVo.setFileUrl(cosUtil.getAccessUrl(eduResourceVo.getFileUrl()));
		}
		return respPaging;
	}
	
	/**
	 * @Description: 资源推荐
	 * @Author wanbo
	 * @DateTime 2020/04/16
	 */
	@Override
	public RespPaging<EduResourceVo> recommendResource(ReqRecommendResource req) throws Exception{
		RespPaging<EduResourceVo> respPaging = new RespPaging<EduResourceVo>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<EduResourceVo> list = eduResourceMapper.recommendResource(req);
		BeanUtil.copyProperties(new PageInfo<EduResourceVo>(list), respPaging);
		for (EduResourceVo eduResourceVo : respPaging.getList()) {
			eduResourceVo.setCoverUrl(cosUtil.getAccessUrl(eduResourceVo.getCoverUrl()));
			eduResourceVo.setFileUrl(cosUtil.getAccessUrl(eduResourceVo.getFileUrl()));
		}
		return respPaging;
	}
	
	/**
	 * @Description: 资源详情
	 * @Author wanbo
	 * @DateTime 2020/03/31
	 */
	@Override
	public RespPaging<RespEduResourceDetail> getEduResourceDetail(ReqEduResourceDetail req) throws Exception {
		RespPaging<RespEduResourceDetail> respPaging = new RespPaging<RespEduResourceDetail>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespEduResourceDetail> list = eduResourceMapper.getEduResourceDetail(req.getResourceId());
		BeanUtil.copyProperties(new PageInfo<RespEduResourceDetail>(list), respPaging);
		for (EduResourceVo eduResourceVo : respPaging.getList()) {
			eduResourceVo.setCoverUrl(cosUtil.getAccessUrl(eduResourceVo.getCoverUrl()));
			eduResourceVo.setFileUrl(cosUtil.getAccessUrl(eduResourceVo.getFileUrl()));
		}
		return respPaging;
	}
	
	
}
