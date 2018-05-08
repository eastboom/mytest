package cn.itcast.job.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class App {

	// 测试任务调度实现 （只要确保spring配置文件有加载，就表示Quartz框架以及完成初始化）
	@Test
	public void app() throws Exception {
		// 确保容器处于运行状态
		while(true){}
	}
}














