package com.luoxi.server.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.model.IModelService;
import com.luoxi.api.model.protocol.ReqModelInfo;
import com.luoxi.api.model.protocol.ReqModelInfo.RespModelInfo;
import com.luoxi.api.model.protocol.ReqModelList;
import com.luoxi.api.model.protocol.ReqModelList.RespModelList;
import com.luoxi.api.model.protocol.ReqRemoveModel;
import com.luoxi.api.model.protocol.ReqSaveModel;
import com.luoxi.api.model.protocol.ReqSaveModel.RespSaveModel;
import com.luoxi.base.RespPaging;
import com.luoxi.base.ResultMessage;
import com.luoxi.constant.ConstSource;
import com.luoxi.exception.LxException;
import com.luoxi.model.Model;
import com.luoxi.server.dao.ModelMapper;
import com.luoxi.server.dao.SequenceMapper;

import cn.hutool.core.bean.BeanUtil;

@DubboService
public class ModelService implements IModelService{

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private SequenceMapper sequenceMapper;
	
	/**
	 * @Description: 型号列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespModelList> modelList(ReqModelList req) throws Exception {
		RespPaging<RespModelList> respPaging = new RespPaging<RespModelList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespModelList> list = modelMapper.modelList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespModelList>(list), respPaging);
		for (RespModelList resp : respPaging.getList()) {
			resp.setSource(ConstSource.getText(resp.getSource()));
		}
		return respPaging;
	}

	/**
	 * @Description: 型号信息
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespModelInfo modelInfo(ReqModelInfo req) throws Exception {
		RespModelInfo resp = modelMapper.modelInfo(BeanUtil.beanToMap(req));
		if(resp!=null) {
			resp.setSource(ConstSource.getText(resp.getSource()));
		}
		return resp;
	}

	/**
	 * @Description: 保存型号
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespSaveModel saveModel(ReqSaveModel req) throws Exception {
		Model model = new Model();
		BeanUtil.copyProperties(req, model);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modelName", req.getModelName());
		map.put("brandId", req.getBrandId());
		Model dbModel = modelMapper.getModel(map);
		
		if(StringUtils.isNotBlank(model.getModelId())) {
			//修改操作
			if(!BeanUtil.isEmpty(dbModel) && !dbModel.getBrandId().equals(model.getBrandId()))
				throw LxException.of().setMsg("名称已存在");
			if(BeanUtil.isEmpty(dbModel) || dbModel.getBrandId().equals(model.getBrandId())) {
				modelMapper.updateByPrimaryKeySelective(model);				
			}else {
				throw LxException.of().setResult(ResultMessage.FAILURE.result());				
			}
		}else {
			//新增操作
			if(!BeanUtil.isEmpty(dbModel))
				throw LxException.of().setMsg("名称已存在");
			model.setModelId(sequenceMapper.generateModelId());
			modelMapper.insertSelective(model);
		}
		
		RespSaveModel resp = new RespSaveModel();
		BeanUtil.copyProperties(model, resp);
		return resp;
	}

	/**
	 * @Description: 删除型号
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeModel(List<ReqRemoveModel> req) throws Exception {
		modelMapper.removeModel(req);
	}
	
	
	
	
	
	
	
	
}
