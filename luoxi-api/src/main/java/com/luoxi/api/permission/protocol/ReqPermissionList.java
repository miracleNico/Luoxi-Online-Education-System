package com.luoxi.api.permission.protocol;

import java.io.Serializable;

import com.luoxi.api.permission.protocol.ReqPermissionInfo.RespPermissionInfo;
import com.luoxi.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqPermissionList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String permissionName;
	private String permissionType;
	private String remark;
	private String startTime;
	private String endTime;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespPermissionList extends RespPermissionInfo implements Serializable{
		private static final long serialVersionUID = 6799409157978436034L;
		private Boolean open;
    }
    

}
