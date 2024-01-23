package com.luoxi.controller;

import javax.validation.Valid;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.api.book.IBookService;
import com.luoxi.api.book.protocol.ReqSearchBook;
import com.luoxi.api.book.protocol.ReqSearchBook.RespSearchBook;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags={"课本"})
@RestController
@RequestMapping("api/book")
public class BookController extends BaseController{
	
	@DubboReference
	private IBookService bookService;
	
	@ApiOperation(value="搜索课本")
	@PostMapping("searchBook")
	public Result<RespPaging<RespSearchBook>> searchBook(@Valid @RequestBody ReqSearchBook req) throws Exception {
		return ResultMessage.SUCCESS.result(bookService.searchBook(req));
	}
	
}
