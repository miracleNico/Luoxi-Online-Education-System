package com.luoxi.server.service;


import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.eduResource.IOpenEduResourceService;
import com.luoxi.api.eduResource.protocol.ReqOpenEduResourceDetail;
import com.luoxi.api.eduResource.protocol.ReqOpenEduResourceInfo;
import com.luoxi.api.eduResource.protocol.ReqOpenRecommendResource;
import com.luoxi.api.eduResource.protocol.ReqOpenSearchResource;
import com.luoxi.api.eduResource.vo.OpenEduResourceBaseVo;
import com.luoxi.api.eduResource.vo.OpenEduResourceInfoVo;
import com.luoxi.base.RespPaging;
import com.luoxi.server.dao.OpenEduResourceMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;

@DubboService
public class OpenEduResourceService implements IOpenEduResourceService{

	@Autowired
	private OpenEduResourceMapper openEduResourceMapper;
	@Autowired
	private CosUtil cosUtil;
	
	@Override
	public List<OpenEduResourceBaseVo> recommendResource(ReqOpenRecommendResource req) throws Exception {
		List<OpenEduResourceBaseVo> list = openEduResourceMapper.recommendResource(req);
		for (OpenEduResourceBaseVo resp : list) {
			resp.setCoverUrl(cosUtil.getAccessUrl(resp.getCoverUrl()));
			resp.setFileUrl(cosUtil.getAccessUrl(resp.getFileUrl()));
		}
		return list;
	}
	
	@Override
	public RespPaging<OpenEduResourceBaseVo> searchResource(ReqOpenSearchResource req) throws Exception {
		RespPaging<OpenEduResourceBaseVo> respPaging = new RespPaging<OpenEduResourceBaseVo>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<OpenEduResourceBaseVo> list = openEduResourceMapper.searchResource(req);
		BeanUtil.copyProperties(new PageInfo<OpenEduResourceBaseVo>(list), respPaging);
		for (OpenEduResourceBaseVo resp : respPaging.getList()) {
			resp.setCoverUrl(cosUtil.getAccessUrl(resp.getCoverUrl()));
			resp.setFileUrl(cosUtil.getAccessUrl(resp.getFileUrl()));
		}
		return respPaging;
	}
	
	@Override
	public OpenEduResourceInfoVo eduResourceInfo(ReqOpenEduResourceInfo req) throws Exception {
		OpenEduResourceInfoVo resp = openEduResourceMapper.eduResourceInfo(req.getResourceId());
		if(BeanUtil.isNotEmpty(resp)) {
			resp.setCoverUrl(cosUtil.getAccessUrl(resp.getCoverUrl()));
			resp.setFileUrl(cosUtil.getAccessUrl(resp.getFileUrl()));
		}
		return resp;
	}
	
	@Override
	public RespPaging<OpenEduResourceInfoVo> eduResourceDetail(ReqOpenEduResourceDetail req) throws Exception {
		RespPaging<OpenEduResourceInfoVo> respPaging = new RespPaging<OpenEduResourceInfoVo>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<OpenEduResourceInfoVo> list = openEduResourceMapper.eduResourceDetail(req);
		BeanUtil.copyProperties(new PageInfo<OpenEduResourceInfoVo>(list), respPaging);
		for (OpenEduResourceInfoVo resp : respPaging.getList()) {
			resp.setCoverUrl(cosUtil.getAccessUrl(resp.getCoverUrl()));
			resp.setFileUrl(cosUtil.getAccessUrl(resp.getFileUrl()));
		}
		return respPaging;
	}
	
	
}
