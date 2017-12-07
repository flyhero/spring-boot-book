package cn.iflyapi;

import cn.iflyapi.properties.TipsProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertiesApplicationTests {

	//取出配置文件中的属性值
	@Value("${cn.iflyapi.name}")
	private String name;

	@Value("${cn.iflyapi.url}")
	private String url;

	@Autowired
	private TipsProperties tipsProperties;

	@Test
	public void contextLoads() {
	}

	/**
	 * 简单的配置方式
	 * @title: testSimpleProperties
	 * @author qfwang
	 * @params []
	 * @return void
	 * @date 2017/12/7 下午2:00
	 */
	@Test
	public void testSimpleProperties(){
		Assert.assertNotNull("name不能为空",name);
		System.out.println("name:"+name);
		Assert.assertNotNull("url不能为空",url);
		System.out.println("url:"+url);
	}

	@Test
	public void testComplexProperties(){
		Assert.assertNotNull(tipsProperties);
		System.out.println("code:"+tipsProperties.getCode());
	}
}
