package com.luoxi.api.press;

import java.util.List;

import com.luoxi.api.press.protocol.ReqPressList.RespPressList;

public interface IPressService {
	
	List<RespPressList> pressList() throws Exception;
	
}
