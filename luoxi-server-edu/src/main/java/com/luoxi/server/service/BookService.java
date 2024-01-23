package com.luoxi.server.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoxi.api.book.IBookService;
import com.luoxi.api.book.protocol.ReqBookInfo;
import com.luoxi.api.book.protocol.ReqBookInfo.RespBookInfo;
import com.luoxi.api.book.protocol.ReqBookList;
import com.luoxi.api.book.protocol.ReqBookList.RespBookList;
import com.luoxi.api.book.protocol.ReqRemoveBook;
import com.luoxi.api.book.protocol.ReqSaveBook;
import com.luoxi.api.book.protocol.ReqSearchBook;
import com.luoxi.api.book.protocol.ReqSearchBook.RespSearchBook;
import com.luoxi.base.RespPaging;
import com.luoxi.constant.ConstBookVolume;
import com.luoxi.model.Book;
import com.luoxi.server.dao.BookMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;

@DubboService
public class BookService implements IBookService{
	
	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private CosUtil cosUtil;
	
	/**
	 * @Description: 课本列表
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public RespPaging<RespBookList> bookList(ReqBookList req) throws Exception {
		RespPaging<RespBookList> respPaging = new RespPaging<RespBookList>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespBookList> list = bookMapper.bookList(BeanUtil.beanToMap(req));
		BeanUtil.copyProperties(new PageInfo<RespBookList>(list), respPaging);
		for (RespBookList resp : respPaging.getList()) {
			resp.setCoverUrl(cosUtil.getAccessUrl(resp.getCoverUrl()));
			resp.setZipUrl(cosUtil.getAccessUrl(resp.getZipUrl()));
			resp.setVolume(StrUtil.isEmpty(resp.getVolume())?null:EnumUtil.likeValueOf(ConstBookVolume.class, resp.getVolume()).getText());
		}
		return respPaging;
	}
	
	@Override
	public RespBookInfo bookInfo(ReqBookInfo req) throws Exception {
		RespBookInfo resp = bookMapper.bookInfo(req.getBookId());
		if(resp!=null) {
			resp.setCoverUrl(cosUtil.getAccessUrl(resp.getCoverUrl()));
			resp.setZipUrl(cosUtil.getAccessUrl(resp.getZipUrl()));
		}
		return resp;
	}
	
	@Transactional
	@Override
	public void saveBook(ReqSaveBook reqSaveBook) throws Exception {
		reqSaveBook.setCoverUrl(cosUtil.filterUrlDomain(reqSaveBook.getCoverUrl()));
		reqSaveBook.setZipUrl(cosUtil.filterUrlDomain(reqSaveBook.getZipUrl()));
		reqSaveBook.setVolume(StrUtil.emptyToDefault(reqSaveBook.getVolume(), null));
		Book book = new Book();
		BeanUtil.copyProperties(reqSaveBook, book);
		
		Book dbBook = bookMapper.getBook(MapUtil.builder().put("bookId", reqSaveBook.getBookId()).build());
		
		if(dbBook!=null) {
			bookMapper.updateByPrimaryKeySelective(book);
		}else {
			bookMapper.insertSelective(book);
		}
	}
	
	/**
	 * @Description: 删除课本
	 * @Author wanbo
	 * @DateTime 2019/11/20
	 */
	@Override
	public void removeBook(List<ReqRemoveBook> req) throws Exception {
		removeBookFile(req.get(0).getBookId());
		bookMapper.deleteByPrimaryKey(req.get(0).getBookId());
	}
	
	
	@Override
	public RespPaging<RespSearchBook> searchBook(ReqSearchBook req) throws Exception{
		RespPaging<RespSearchBook> respPaging = new RespPaging<RespSearchBook>();
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<RespSearchBook> list = bookMapper.searchBook(req);
		BeanUtil.copyProperties(new PageInfo<RespSearchBook>(list), respPaging);
		for (RespSearchBook resp : respPaging.getList()) {
			resp.setCoverUrl(cosUtil.getAccessUrl(resp.getCoverUrl()));
			resp.setZipUrl(cosUtil.getAccessUrl(resp.getZipUrl()));
		}
		return respPaging;
	}
	
	private void removeBookFile(String bookId) throws Exception {
		Book book = bookMapper.selectByPrimaryKey(bookId);
		if(StringUtils.isNotBlank(book.getCoverUrl()))
			cosUtil.deleteFile(book.getCoverUrl());
		if(StringUtils.isNotBlank(book.getZipUrl()))
			cosUtil.deleteFile(book.getZipUrl());
	}
	
	
	
}
