package com.ailoxi.client.vision;

import cn.hutool.core.util.URLUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LuoxiClientVisionApplicationTests {

	@Test
	void contextLoads() {

	    String url = "https://www.cnblogs.com/kenshinobiy/p/9194147.html";

        System.out.println(URLUtil.getPath(url));
    }

}
