package com.luoxi.api.versionModule.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReqUpdAppVersionModuleParent implements Serializable{
	
	private static final long serialVersionUID = -8542855042034048825L;
	private String versionModuleId;
	private String parentId;
	
}
