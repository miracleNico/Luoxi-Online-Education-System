package com.luoxi.server.vo;

import com.luoxi.model.Device;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
@Data
public class DeviceVo extends Device{
	
	private static final long serialVersionUID = -1193716875678478210L;
	
	private String deviceAppId;

}
