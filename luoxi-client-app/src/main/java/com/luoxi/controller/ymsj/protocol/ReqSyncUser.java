package com.luoxi.controller.ymsj.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqSyncUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "电话号码不能为空")
	private String phone;
	
}
