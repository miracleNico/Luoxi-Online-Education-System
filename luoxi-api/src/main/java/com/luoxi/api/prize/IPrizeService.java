package com.luoxi.api.prize;

import com.luoxi.api.prize.protocol.ReqPrizeStore;
import com.luoxi.api.prize.protocol.ReqPrizeStore.RespPrizeStore;
import com.luoxi.api.prize.vo.PrizeVo;
import com.luoxi.base.RespPaging;

public interface IPrizeService {
	
	/**
	 * @Description: 奖品商城
	 * @Author wanbo
	 * @DateTime 2020/06/28
	 */
	RespPaging<RespPrizeStore> prizeStore(ReqPrizeStore req) throws Exception;
	
	/**
	 * @Description: 获取奖品对象-加入排他锁
	 * @Author wanbo
	 * @DateTime 2020/06/30
	 */
	PrizeVo getPrizeVoByIdLock(String prizeId);
	
	/**
	 * @Description: 更新库存
	 * @Author wanbo
	 * @DateTime 2020/06/30
	 */
	int updatePrizeInventory(String prizeId, Integer number);
	
}
