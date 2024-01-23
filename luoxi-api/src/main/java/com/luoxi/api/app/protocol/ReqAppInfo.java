package com.luoxi.api.app.protocol;

import java.io.Serializable;
import java.util.List;

import com.luoxi.api.app.protocol.ReqAppList.RespAppList;
import com.luoxi.api.app.vo.AppEarningsSettingVo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class ReqAppInfo implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String appId;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespAppInfo extends RespAppList implements Serializable{
    	private static final long serialVersionUID = -4291721599975198242L;
    	private List<AppEarningsSettingVo> appEarningsSettings;
    }
    

}
