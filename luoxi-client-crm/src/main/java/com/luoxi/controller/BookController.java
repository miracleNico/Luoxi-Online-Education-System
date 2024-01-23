package com.luoxi.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.luoxi.aop.ApiModule;
import com.luoxi.aop.ApiModule.Module;
import com.luoxi.aop.ApiOperLog;
import com.luoxi.aop.ApiOperLog.ACTION;
import com.luoxi.api.book.IBookService;
import com.luoxi.api.book.protocol.ReqBookInfo;
import com.luoxi.api.book.protocol.ReqBookInfo.RespBookInfo;
import com.luoxi.api.book.protocol.ReqBookList;
import com.luoxi.api.book.protocol.ReqBookList.RespBookList;
import com.luoxi.api.book.protocol.ReqRemoveBook;
import com.luoxi.api.book.protocol.ReqSaveBook;
import com.luoxi.base.BaseController;
import com.luoxi.base.RespPaging;
import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@ApiModule(module = Module.BOOK)
@Controller
@RequestMapping("book")
public class BookController extends BaseController{
	
	@DubboReference
	private IBookService bookService;
	@Autowired
	private CosUtil cosUtil;
	@Value("${lx.custom.temp.booksPath}")
	private String booksPath;
	
	@RequestMapping("listPage")
	public String listPage() {
		return "page/book/list";
	}
	
	@ApiOperLog(action = ACTION.SELECT)
	@ResponseBody
	@RequestMapping("bookList")
	public Result<RespPaging<RespBookList>> bookList(@RequestBody ReqBookList req) throws Exception {
		return ResultMessage.SUCCESS.result(bookService.bookList(req));
	}
	
	@RequestMapping("editPage")
	public String editPage(HttpServletRequest request,String bookId) throws Exception {
		RespBookInfo info = new RespBookInfo();
		if(StringUtils.isNotBlank(bookId)) {
			info = bookService.bookInfo(new ReqBookInfo().setBookId(bookId));
		}
		request.setAttribute("info", info);
		return "page/book/edit";
	}
	
	@ApiOperLog(action = ACTION.INFO)
	@RequestMapping("infoPage")
	public String infoPage(HttpServletRequest request,String bookId) throws Exception {
		RespBookInfo info = new RespBookInfo();
		if(StringUtils.isNotBlank(bookId)) {
			info = bookService.bookInfo(new ReqBookInfo().setBookId(bookId));
		}
		request.setAttribute("info", info);
		return "page/book/info";
	}
	
	@ApiOperLog(action = ACTION.DELETE)
	@ResponseBody
	@RequestMapping("removeBook")
	public Result<?> removeBook(@Valid @RequestBody List<ReqRemoveBook> req) throws Exception {
		bookService.removeBook(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.SAVE)
	@ResponseBody
	@RequestMapping("saveBook")
	public Result<?> saveBook(@Valid @RequestBody ReqSaveBook req) throws Exception {
		bookService.saveBook(req);
		return ResultMessage.SUCCESS.result();
	}
	
	@ApiOperLog(action = ACTION.IMPORT)
	@ResponseBody
	@RequestMapping(value = "importBook", headers = "content-type=multipart/form-data")
	public Result<?> importBook(@RequestParam("file") MultipartFile file) throws Exception {
		String bookName = StrUtil.removeSuffixIgnoreCase(file.getOriginalFilename(), ".zip");
		String bookPath = booksPath + "/"+ bookName;
		ZipUtil.unzip(file.getInputStream(), FileUtil.file(booksPath),Charset.defaultCharset());
		File featureFile = FileUtil.file(bookPath + "/feature");
		File jsonFile = FileUtil.file(bookPath + "/JQXXYYD1C.json");
		
		//上传封面
		File coverFile = FileUtil.file(bookPath+"/"+"cover.jpg");
		String coverUrl = cosUtil.upload("books/"+bookName+"/"+FileUtil.getName(coverFile),coverFile);
		
		//上传audios
		for(File audioZipFile : FileUtil.loopFiles(bookPath+"/audios")) {
			cosUtil.upload("/books/"+bookName+"/audios/"+audioZipFile.getName(),audioZipFile);
		}
		
		File[] zipFiles = {featureFile,jsonFile,coverFile};
		File bookZipFile = ZipUtil.zip(FileUtil.file(bookPath+".zip"),true,zipFiles);
		String zipUrl = cosUtil.upload("books/"+bookName+"/"+file.getOriginalFilename(),bookZipFile);
		return ResultMessage.SUCCESS.result(MapUtil.builder().put("bookId", bookName).put("coverUrl", coverUrl).put("zipUrl", zipUrl).build());
	}

	
}
