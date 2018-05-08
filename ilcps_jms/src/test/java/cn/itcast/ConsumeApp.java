package cn.itcast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-mq-send.xml")
public class ConsumeApp {

	// 保持监听器运行
	@Test
	public void run() throws Exception {
		while(true){}
	}
}




