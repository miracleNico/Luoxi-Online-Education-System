package com.luoxi.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoxi.base.Result;
import com.luoxi.base.ResultMessage;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags={"云端识别"})
@RestController
@RequestMapping("api/k12")
public class K12Controller {
	
	private String prefix = "lx.print.val:";
	@Value("${lx.custom.python.enable.log:true}")
	private Boolean pythonEnableLog;
	@Value("${lx.custom.python.version:python}")
	private String pythonVersion;
	
	//封面
	@Value("${lx.custom.python.cover.default.datasetPath:}")
	private String pythonCoverDefaultDatasetPath;
	@Value("${lx.custom.python.cover.dir:}")
	private String pythonCoverDir;
	@Value("${lx.custom.python.cover.py:}")
	private String pythonCoverPy;
	@Value("${lx.custom.python.cover.imgPath:}")
	private String pythonCoverImgPath;
	
	//内页
	@Value("${lx.custom.python.page.dir}")
	private String pythonPageDir;
	@Value("${lx.custom.python.page.py}")
	private String pythonPagePy;
	@Value("${lx.custom.python.finger.py}")
	private String pythonFingerPy;
	@Value("${lx.custom.python.page.imgPath:}")
	private String pythonPageImgPath;
	
	//搜题
	@Value("${lx.custom.python.searchTopic.dir:}")
	private String pythonSearchTopicDir;
	@Value("${lx.custom.python.searchTopic.py:}")
	private String pythonSearchTopicPy;
	@Value("${lx.custom.python.searchTopic.imgPath:}")
	private String pythonSearchTopicImgPath;
	
	
	@ApiOperation(value="封面识别")
	@PostMapping("cover")
	public Result<String> cover(@Valid @RequestBody ReqCover req) throws Exception {
		StringBuffer ret = new StringBuffer();
		String filePath = StrUtil.join("/", pythonCoverImgPath,IdUtil.fastSimpleUUID()+".txt");
		FileUtil.appendString(req.getImage(), filePath, Charset.forName("UTF-8"));
		String datasetPath = pythonCoverDefaultDatasetPath;
		if(StrUtil.isNotBlank(req.getMerchant())) {
			datasetPath = StrUtil.join("/", req.getMerchant(),"dataset");
		}
		Process pr = RuntimeUtil.exec(null, FileUtil.file(pythonCoverDir), StrUtil.join(" ", pythonVersion, pythonCoverPy, filePath, datasetPath));
		RuntimeUtil.getResultLines(pr).forEach(line->{
			if(StrUtil.indexOf(line, prefix, 0, false)==0) {
				ret.append(StrUtil.removePrefix(line, prefix));
			}
        	if(pythonEnableLog) {
        		log.info("-----cover:{}",line);
        	}else if(StrUtil.indexOf(line, "cost_time:", 0, false)==0) {
        		log.info("-----cover:{}",line);
        	}
        });
		//FileUtil.del(filePath);
		Result<String> result = null;
        if(StrUtil.isNotBlank(ret.toString())) {
        	result = ResultMessage.SUCCESS.result(ret.toString());
        }else{
        	result = ResultMessage.FAILURE_RECOGNITION_EMPTY.result();
        }
        log.info("-----cover:{}",JSONUtil.toJsonStr(result));
        return result;
	}
	
	@ApiOperation(value="内页识别")
	@PostMapping("page")
	public Result<Integer> page(@Valid @RequestBody ReqSearchPage req) throws Exception {
		String filePath = StrUtil.join("/", pythonPageImgPath,IdUtil.fastSimpleUUID()+".txt");
		FileUtil.appendString(req.getImage(), filePath, Charset.forName("UTF-8"));
		StringBuffer ret = new StringBuffer();
		Process pr = RuntimeUtil.exec(null, FileUtil.file(pythonPageDir), StrUtil.join(" ", pythonVersion, pythonPagePy, filePath, req.getBookId()));
		RuntimeUtil.getResultLines(pr).forEach(line->{
        	if(StrUtil.indexOf(line, prefix, 0, false)==0) {
        		ret.append(Convert.toBigDecimal(StrUtil.removePrefix(line, prefix)));
        	}
        	if(pythonEnableLog) {
        		log.info("-----page:{}",line);
        	}else if(StrUtil.indexOf(line, "cost_time:", 0, false)==0) {
        		log.info("-----page:{}",line);
        	}
        });
        //FileUtil.del(filePath);
		Result<Integer> result = null;
        if(StrUtil.isNotBlank(ret.toString())) {
        	result = ResultMessage.SUCCESS.result(Convert.toInt(ret));
        }else{
        	result = ResultMessage.FAILURE_RECOGNITION_EMPTY.result();
        }
        log.info("-----page:{}",JSONUtil.toJsonStr(result));
        return result;
	}
	
	@ApiOperation(value="手指识别")
	@PostMapping("finger")
	public Result<List<BigDecimal>> finger(@Valid @RequestBody ReqSearchPage req) throws Exception {
		String filePath = StrUtil.join("/", pythonPageImgPath,IdUtil.fastSimpleUUID()+".txt");
		FileUtil.appendString(req.getImage(), filePath, Charset.forName("UTF-8"));
		List<BigDecimal> ret = new ArrayList<BigDecimal>();
		Process pr = RuntimeUtil.exec(null, FileUtil.file(pythonPageDir), StrUtil.join(" ", pythonVersion, pythonFingerPy, filePath, req.getBookId()));
		RuntimeUtil.getResultLines(pr).forEach(line->{
        	if(StrUtil.indexOf(line, prefix, 0, false)==0) {
        		ret.add(Convert.toBigDecimal(StrUtil.removePrefix(line, prefix)));
        	}
        	if(pythonEnableLog) {
        		log.info("-----finger:{}",line);
        	}else if(StrUtil.indexOf(line, "cost_time:", 0, false)==0) {
        		log.info("-----finger:{}",line);
        	}
        });
        //FileUtil.del(filePath);
		Result<List<BigDecimal>> result = null;
        if(ret.size()>0) {
        	result = ResultMessage.SUCCESS.result(ret);
        }else{
        	result = ResultMessage.FAILURE_RECOGNITION_EMPTY.result();
        }
        log.info("-----finger:{}",JSONUtil.toJsonStr(result));
        return result;
	}
	
	@ApiOperation(value="搜题识别")
	@PostMapping("searchTopic")
	public Result<List<String>> searchTopic(@Valid @RequestBody ReqSearchTopic req) throws Exception {
		String basePath = StrUtil.join("/", pythonSearchTopicImgPath,req.getAndroId());
		String fileName = req.getSearchId()+".txt";
		String filePath = StrUtil.join("/", basePath,fileName);
		
		//刪除此安卓id的其他文件
		FileUtil.loopFiles(basePath, new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if(!pathname.getName().equals(fileName)) FileUtil.del(pathname);
				return false;
			}
		});
		
		List<String> ret = new ArrayList<String>();
		//添加一行img
		FileUtil.appendLines(Arrays.asList(req.getImage()), filePath, Charset.forName("UTF-8"));
        Process pr = RuntimeUtil.exec(null, FileUtil.file(pythonSearchTopicDir), StrUtil.join(" ", pythonVersion, pythonSearchTopicPy, filePath));
		RuntimeUtil.getResultLines(pr).forEach(line->{
        	if(StrUtil.indexOf(line, prefix, 0, false)==0) {
        		ret.add(StrUtil.removePrefix(line, prefix));
        	}
        	if(pythonEnableLog) {
        		log.info("-----searchTopic:{}",line);
        	}else if(StrUtil.indexOf(line, "cost_time:", 0, false)==0) {
        		log.info("-----searchTopic:{}",line);
        	}
        });
        Result<List<String>> result = null;
        if(ArrayUtil.isNotEmpty(ret)) {
        	//搜题图片有返回则删除此次请求文件
        	FileUtil.del(filePath);
        	result = ResultMessage.SUCCESS.result(ret);
        }else{
        	result = ResultMessage.FAILURE_RECOGNITION_EMPTY.result();
        }
        log.info("-----searchTopic:{}",JSONUtil.toJsonStr(result));
        return result;
	}
	
	@ApiModel("请求-内页识别")
	@Data
	static class ReqSearchPage implements Serializable{
		private static final long serialVersionUID = -1561909538998528831L;
		@ApiModelProperty(value = "课本ID")
		@NotBlank(message = "课本ID不能为空")
		private String bookId;
		@ApiModelProperty(value = "图片")
		@NotBlank(message = "图片不能为空")
		private String image;
	}
	
	@ApiModel("请求-内页识别")
	@Data
	static class ReqUploadPage implements Serializable{
		private static final long serialVersionUID = -1561909538998528831L;
		@ApiModelProperty(value = "课本ID")
		@NotBlank(message = "课本ID不能为空")
		private String bookId;
	}
	
	@ApiModel("请求-搜题识别")
	@Data
	static class ReqSearchTopic implements Serializable{
		private static final long serialVersionUID = -1561909538998528831L;
		@ApiModelProperty(value = "搜题ID")
		@NotBlank(message = "搜题ID不能为空")
		private String searchId;
		@ApiModelProperty(value = "安卓ID")
		@NotBlank(message = "安卓ID不能为空")
		private String androId;
		@ApiModelProperty(value = "图片")
		@NotBlank(message = "图片不能为空")
		private String image;
	}
	
	@ApiModel("请求-封面识别")
	@Data
	static class ReqCover implements Serializable{
		private static final long serialVersionUID = -1561909538998528831L;
		@ApiModelProperty(value = "图片")
		@NotBlank(message = "图片不能为空")
		private String image;
		@ApiModelProperty(value = "内容商[gankao/tongnian]")
		private String merchant;
	}
	
}
