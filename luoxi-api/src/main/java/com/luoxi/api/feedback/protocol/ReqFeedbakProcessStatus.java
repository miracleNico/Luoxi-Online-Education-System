package com.luoxi.api.feedback.protocol;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReqFeedbakProcessStatus implements Serializable{
	
	private static final long serialVersionUID = 1055722802402768004L;
	@NotBlank(message = "反馈id不能为空")
	private String feedbackId;
	@NotBlank(message = "处理状态不能为空")
	private String processStatus;
	private String remark;
	
}
