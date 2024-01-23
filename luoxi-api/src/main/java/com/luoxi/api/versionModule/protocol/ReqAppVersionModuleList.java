package com.luoxi.api.versionModule.protocol;

import java.io.Serializable;

import com.luoxi.api.versionModule.protocol.ReqAppVersionModuleInfo.RespAppVersionModuleInfo;
import com.luoxi.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqAppVersionModuleList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String versionId;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespAppVersionModuleList extends RespAppVersionModuleInfo implements Serializable{
    	
    	private static final long serialVersionUID = -4291721599975198242L;
    	private Boolean open;
    	
    }
    

}
