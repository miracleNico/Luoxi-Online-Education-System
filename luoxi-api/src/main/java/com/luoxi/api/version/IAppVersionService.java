package com.luoxi.api.version;

import java.util.List;

import com.luoxi.api.version.protocol.ReqAppVersionInfo;
import com.luoxi.api.version.protocol.ReqAppVersionInfo.RespAppVersionInfo;
import com.luoxi.api.version.protocol.ReqAppVersionList;
import com.luoxi.api.version.protocol.ReqAppVersionList.RespAppVersionList;
import com.luoxi.api.version.protocol.ReqRemoveAppVersion;
import com.luoxi.api.version.protocol.ReqSaveAppVersion;
import com.luoxi.api.version.protocol.ReqSaveAppVersion.RespSaveAppVersion;
import com.luoxi.api.version.vo.AppVersionVo;
import com.luoxi.base.RespPaging;

public interface IAppVersionService {
	
	AppVersionVo getAppVersion(String packageName,String versionCode)throws Exception;

	/**
	 * @Description: 版本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespAppVersionList> appVersionList(ReqAppVersionList req) throws Exception;
	
	/**
	 * @Description: 版本详情
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespAppVersionInfo appVersionInfo(ReqAppVersionInfo req) throws Exception;
	
	/**
	 * @Description: 保存版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespSaveAppVersion saveAppVersion(ReqSaveAppVersion req) throws Exception;
	
	/**
	 * @Description: 删除版本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeAppVersion(List<ReqRemoveAppVersion> req) throws Exception;
	
	
}
