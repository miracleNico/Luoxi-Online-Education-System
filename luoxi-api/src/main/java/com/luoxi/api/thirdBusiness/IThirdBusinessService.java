package com.luoxi.api.thirdBusiness;

import com.luoxi.api.thirdBusiness.protocol.ReqThirdBusinessList;
import com.luoxi.api.thirdBusiness.protocol.ReqThirdBusinessList.RespThirdBusinessList;
import com.luoxi.base.RespPaging;

public interface IThirdBusinessService {
	
	RespPaging<RespThirdBusinessList> thirdBusinessList(ReqThirdBusinessList req) throws Exception;
	
}
