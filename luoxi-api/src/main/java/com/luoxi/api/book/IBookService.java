package com.luoxi.api.book;

import java.util.List;

import com.luoxi.api.book.protocol.ReqBookInfo;
import com.luoxi.api.book.protocol.ReqBookList;
import com.luoxi.api.book.protocol.ReqBookList.RespBookList;
import com.luoxi.api.book.protocol.ReqRemoveBook;
import com.luoxi.api.book.protocol.ReqSaveBook;
import com.luoxi.api.book.protocol.ReqSearchBook;
import com.luoxi.api.book.protocol.ReqBookInfo.RespBookInfo;
import com.luoxi.api.book.protocol.ReqSearchBook.RespSearchBook;
import com.luoxi.base.RespPaging;

public interface IBookService {

	/**
	 * @Description: 课本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	RespPaging<RespBookList> bookList(ReqBookList req) throws Exception;
	
	/**
	 * @Description: 导入课本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void saveBook(ReqSaveBook req) throws Exception;
	
	/**
	 * @Description: 课本信息
	 * @Author wanbo
	 * @DateTime 2020/04/26
	 */
	RespBookInfo bookInfo(ReqBookInfo req) throws Exception;
	
	/**
	 * @Description: 删除课本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	void removeBook(List<ReqRemoveBook> req) throws Exception;
	
	/**
	 * @Description: 搜索课本
	 * @Author wanbo
	 * @DateTime 2020/01/13
	 */
	RespPaging<RespSearchBook> searchBook(ReqSearchBook req) throws Exception;
	
}
