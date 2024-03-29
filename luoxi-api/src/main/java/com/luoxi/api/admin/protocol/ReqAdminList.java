package com.luoxi.api.admin.protocol;

import java.io.Serializable;

import com.luoxi.api.admin.protocol.ReqAdminInfo.RespAdminInfo;
import com.luoxi.base.Paging;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@Data
public class ReqAdminList extends Paging implements Serializable{
	
	private static final long serialVersionUID = 3397901518385598200L;
	private String username;
	private String remark;
	private String startTime;
	private String endTime;
    
	@EqualsAndHashCode(callSuper=true)
    @Accessors(chain = true)
    @Data
    public static class RespAdminList extends RespAdminInfo implements Serializable{
		private static final long serialVersionUID = 6799409157978436034L;
		private String roleNames;
        private String createTime;
        private String updateTime;
    }
    

}
