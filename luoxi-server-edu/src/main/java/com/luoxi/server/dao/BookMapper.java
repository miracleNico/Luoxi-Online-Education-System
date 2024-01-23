/**  
 * @Title: OrderMapper.java
 * @Description: TODO
 * @author wanbo
 * @date 2018年3月28日
 */
package com.luoxi.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.luoxi.api.book.protocol.ReqBookList.RespBookList;
import com.luoxi.api.book.protocol.ReqSearchBook.RespSearchBook;
import com.luoxi.api.book.protocol.ReqRemoveBook;
import com.luoxi.api.book.protocol.ReqSearchBook;
import com.luoxi.api.book.protocol.ReqBookInfo.RespBookInfo;
import com.luoxi.model.Book;

import tk.mybatis.mapper.common.Mapper;

public interface BookMapper extends Mapper<Book>{
	
	Book getBook(Map<Object, Object> map);
	
	RespBookInfo bookInfo(String bookId);
	
	/**
	 * @Description: 课本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	List<RespBookList> bookList(Map<String, Object> map);
	
	/**
	 * @Description: 删除课本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	int removeBook(@Param("list")List<ReqRemoveBook> list);
	
	/**
	 * @Description: 搜索课本
	 * @Author wanbo
	 * @DateTime 2020/01/13
	 */
	List<RespSearchBook> searchBook(ReqSearchBook req);
	
}
