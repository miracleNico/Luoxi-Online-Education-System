package com.luoxi.api.model;

import java.util.List;

import com.luoxi.api.model.protocol.ReqModelInfo;
import com.luoxi.api.model.protocol.ReqModelInfo.RespModelInfo;
import com.luoxi.api.model.protocol.ReqModelList;
import com.luoxi.api.model.protocol.ReqRemoveModel;
import com.luoxi.api.model.protocol.ReqModelList.RespModelList;
import com.luoxi.api.model.protocol.ReqSaveModel;
import com.luoxi.api.model.protocol.ReqSaveModel.RespSaveModel;
import com.luoxi.base.RespPaging;

public interface IModelService {

	/**
	 * @Description: 型号列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespModelList> modelList(ReqModelList req) throws Exception;
	
	/**
	 * @Description: 型号详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespModelInfo modelInfo(ReqModelInfo req) throws Exception;
	
	/**
	 * @Description: 保存型号
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSaveModel saveModel(ReqSaveModel req) throws Exception;
	
	/**
	 * @Description: 删除型号
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeModel(List<ReqRemoveModel> req) throws Exception;

	
}
