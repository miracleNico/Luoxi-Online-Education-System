package com.luoxi.server.service;


import java.util.List;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.luoxi.api.snyc.ISyncService;
import com.luoxi.api.snyc.protocol.ReqSyncPull;
import com.luoxi.api.snyc.protocol.ReqSyncPush;
import com.luoxi.constant.ConstSyncModel;
import com.luoxi.constant.ConstSyncOperType;
import com.luoxi.exception.LxException;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

@DubboService
public class SyncService implements ISyncService{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Object> syncPull(String userId, ReqSyncPull req) throws Exception{
		
		Class<?> c = Class.forName(EnumUtil.getEnumMap(ConstSyncModel.class).get(req.getModel()).getClassName());
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("userId").is(userId));
		
		List<?> list = mongoTemplate.find(query, c);
		
		List<Object> resp = JSONUtil.toList(new JSONArray(list), Object.class);
		
		return resp;
	}
	
	@Override
	public void syncPush(String userId, ReqSyncPush req) throws Exception {
		ConstSyncModel constSyncModel = EnumUtil.getEnumMap(ConstSyncModel.class).get(req.getModel());
		
		if(BeanUtil.isEmpty(constSyncModel)) throw LxException.of().setMsg("未知模块");
		
		Class<?> c = Class.forName(constSyncModel.getClassName());
		
		req.getSyncPushData().forEach(x->{
			if(ConstSyncOperType.REMOVE.name().equals(x.getOperType())) {
				mongoTemplate.remove(BeanUtil.mapToBeanIgnoreCase(x.getJsonData(), c, true));
			}
			
			if(ConstSyncOperType.SAVE.name().equals(x.getOperType())) {
				mongoTemplate.save(BeanUtil.mapToBeanIgnoreCase(x.getJsonData(), c, true));
			}
		});
		
	}
	
	
}
