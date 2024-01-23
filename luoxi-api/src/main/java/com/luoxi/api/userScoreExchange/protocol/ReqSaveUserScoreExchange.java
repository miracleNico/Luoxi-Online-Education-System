package com.luoxi.api.userScoreExchange.protocol;

import java.io.Serializable;

import com.luoxi.api.userScoreExchange.protocol.ReqUserScoreExchangeInfo.RespUserScoreExchangeInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class ReqSaveUserScoreExchange extends RespUserScoreExchangeInfo implements Serializable{
	private static final long serialVersionUID = 1L;

}
