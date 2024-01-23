package com.luoxi.server.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.luoxi.api.businessContent.IBusinessContentService;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentInfo;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentInfo.RespBusinessContentInfo;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentList;
import com.luoxi.api.businessContent.protocol.ReqBusinessContentList.RespBusinessContentList;
import com.luoxi.api.businessContent.protocol.ReqRemoveBusinessContent;
import com.luoxi.api.businessContent.protocol.ReqSaveBusinessContent;
import com.luoxi.api.businessContent.protocol.ReqSaveBusinessContent.RespSaveBusinessContent;
import com.luoxi.api.businessContent.protocol.ReqUpdBusinessContentParent;
import com.luoxi.base.ResultMessage;
import com.luoxi.exception.LxException;
import com.luoxi.model.BusinessContent;
import com.luoxi.server.dao.BusinessContentMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;

@DubboService
public class BusinessContentService implements IBusinessContentService{
	
	@Autowired
	private BusinessContentMapper businessContentMapper;
	
	/**
	 * @Description: 内容商内容列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public List<RespBusinessContentList> businessContentList(ReqBusinessContentList req) throws Exception {
		List<RespBusinessContentList> list = businessContentMapper.businessContentList(BeanUtil.beanToMap(req));
		return list;
	}

	/**
	 * @Description: 内容商内容信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespBusinessContentInfo businessContentInfo(ReqBusinessContentInfo req) throws Exception {
		RespBusinessContentInfo resp = businessContentMapper.businessContentInfo(BeanUtil.beanToMap(req));
		return resp;
	}

	/**
	 * @Description: 保存内容商内容
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespSaveBusinessContent saveBusinessContent(ReqSaveBusinessContent req) throws Exception {
		BusinessContent businessContent = new BusinessContent();
		BeanUtil.copyProperties(req, businessContent);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contentName", req.getContentName());
		map.put("businessId", req.getBusinessId());
		BusinessContent dbBusinessContent = businessContentMapper.getBusinessContent(map);
		
		if(StringUtils.isNotBlank(businessContent.getBusinessContentId())) {
			//修改操作---2020年2月25日18:45:16 需求修改为名称可重复
			//if(dbBusinessContent!=null && !dbBusinessContent.getBusinessContentId().equals(businessContent.getBusinessContentId()))
				//throw LxException.of().setMsg("名称已存在");
			if(dbBusinessContent==null || dbBusinessContent.getBusinessContentId().equals(businessContent.getBusinessContentId())) {
				businessContentMapper.updateByPrimaryKeySelective(businessContent);
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {
			//新增操作---2020年2月25日18:45:16 需求修改为名称可重复
			//if(!BeanUtil.isEmpty(dbBusinessContent))
				//throw LxException.of().setMsg("名称已存在");
			businessContent.setBusinessContentId(IdUtil.fastSimpleUUID());
			businessContentMapper.insertSelective(businessContent);
		}
		
		RespSaveBusinessContent resp = new RespSaveBusinessContent();
		BeanUtil.copyProperties(businessContent, resp);
		return resp;
	}

	/**
	 * @Description: 删除内容商内容
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeBusinessContent(List<ReqRemoveBusinessContent> req) throws Exception {
		businessContentMapper.removeBusinessContent(req);
	}
	
	
	/**
	 * @Description: 修改父级节点
	 * @Author wanbo
	 * @DateTime 2020/02/27
	 */
	@Override
	public void updBusinessContentParent(List<ReqUpdBusinessContentParent> req) throws Exception {
		for (ReqUpdBusinessContentParent o : req) {
			if(StringUtils.isEmpty(o.getParentId())) {
				o.setParentId("");
			}
			businessContentMapper.updBusinessContentParent(o.getBusinessContentId(), o.getParentId());			
		}
	}
	
	
	
	
	
}
