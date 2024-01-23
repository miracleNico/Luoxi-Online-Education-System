package com.luoxi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luoxi.api.channel.IChannelService;
import com.luoxi.api.channel.protocol.ReqChannelInfo;
import com.luoxi.api.device.IDeviceService;
import com.luoxi.api.family.IFamilyMemberService;
import com.luoxi.api.family.protocol.ReqFamilyMemberInfo;
import com.luoxi.api.grade.IGradeService;
import com.luoxi.api.press.IPressService;
import com.luoxi.api.subject.ISubjectService;
import com.luoxi.api.user.IUserService;
import com.luoxi.server.dao.EduResourceMapper;
import com.luoxi.utils.CosUtil;

import cn.hutool.core.io.FileUtil;

@SpringBootTest
class ApplicationTest {
	
	@DubboReference(async = true) IUserService userService;
	@DubboReference(async = false) IDeviceService deviceService;
	@DubboReference(async = true) IFamilyMemberService familyMemberService;
	@DubboReference(async = false) IChannelService channelService;
	@DubboReference(async = false) IFamilyMemberService familyMemberService2;
	@Autowired CosUtil cosUtil;
	@Autowired IGradeService gradeService;
	@Autowired IPressService pressService;
	@Autowired ISubjectService subjectService;
	@Autowired EduResourceMapper eduResourceMapper;
	
	public static void main(String[] args) throws Exception{
		//new ApplicationTest().exe2();
	}
	
	
	void exe() throws Exception{
		/**
		//ring [] cmdArray = {"cd /d E:\\特征提取工具","Inner_page_match.exe","pic/"};
		String [] cmdArray = {"cd /d E:\\特征提取工具","Inner_page_match.exe","pic/"}; 
		
		Runtime rt = Runtime.getRuntime();
		rt.exec("cmd /k");
		rt.exec("cd /d E:\\特征提取工具");
		rt.exec("Inner_page_match.exe");
		Process process = rt.exec("pic/");
		
		//Process process = rt.exec(cmdArray);
		InputStream in = process.getInputStream();
        InputStreamReader reader = new InputStreamReader(in, Charset.forName("GBK"));
        BufferedReader br = new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        String message;
        while((message = br.readLine()) != null) {
            sb.append(message);
        }
        System.out.println(sb);
        */
		
		//"cmd.exe /C start cd /d E:\\特征提取工具"
		//String [] cmdArray = {"cmd.exe /C start","cmd.exe cd /d E:\\特征提取工具"}; 
		
		Runtime rt = Runtime.getRuntime();
		String[] s = {"pic/"};
		//rt.exec("cmd.exe /C start cd /d E:\\特征提取工具");
		//rt.exec("cmd.exe /C start E:\\特征提取工具\\Inner_page_match.exe");
		
		Process p = rt.exec("cmd.exe /C start E:\\特征提取工具\\Inner_page_match.exe");
		OutputStream out = p.getOutputStream();
		out.write("pic/".getBytes());
		out.flush();
		out.close();
		
	}
	
	void exe2()throws Exception{
        Process process = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec("cmd.exe /C start E:\\特征提取工具\\Inner_page_match.exe");
            InputStreamReader in=new InputStreamReader(process.getInputStream());
            BufferedReader inBr=new BufferedReader(in);
            String lineStr;

            OutputStreamWriter os = new OutputStreamWriter(process.getOutputStream());
            BufferedWriter bw = new BufferedWriter(os);
            //bw.write("pic/");

            while((lineStr=inBr.readLine())!=null){
                System.out.println(lineStr);
            }

            //process.waitFor();
            process.getInputStream().close();
            process.getOutputStream().close();
            inBr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Test
	void update() {
		System.out.println(cosUtil.uploadPv("test.jpg", FileUtil.file("G:/test.jpg")));
	}
	
	@Test
	void sync() throws Exception {
		System.out.println("------userService:"+userService.userInfo("14762874de024c6eb990fed253a2db4e"));
		//System.out.println("------deviceService:"+deviceService.deviceInfo(new ReqDeviceInfo().setDeviceId("DV1001")));
		System.out.println("------familyMemberService:"+familyMemberService.familyMemberInfo(new ReqFamilyMemberInfo().setFamilyMemberId("00c74553739b4c7395cc19c81f7a1a91")));
		System.out.println("------channelService:"+channelService.channelInfo(new ReqChannelInfo().setChannelId("CN1016")));
		System.out.println("------familyMemberService2:"+familyMemberService2.familyMemberInfo(new ReqFamilyMemberInfo().setFamilyMemberId("00c74553739b4c7395cc19c81f7a1a91")));
	}
	
	@Test
	void gradeList() throws Exception {
		gradeService.gradeList().forEach(x->{
			System.out.println(x.getGradeName());
		});
	}
	
	@Test
	void pressList() throws Exception {
		pressService.pressList().forEach(x->{
			System.out.println(x.getPressName());
		});
	}
	
	@Test
	void subjectList() throws Exception {
		subjectService.subjectList().forEach(x->{
			System.out.println(x.getSubjectName());
		});
	}

}
