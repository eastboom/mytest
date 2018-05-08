package cn.itcast.mail.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Spring整合JavaMail测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-mail.xml")
public class App2 {

	// 测试Spring整合JavaMail发送邮件
	
	@Resource
	private SimpleMailMessage simpleMailMessage;
	@Resource
	private MailSender mailSender;
	
	@Test
	public void sendMail() throws Exception {
		// 设置邮件属性： 发给谁、主题、内容
		simpleMailMessage.setTo("yuanjie8090@163.com");
		simpleMailMessage.setSubject("新员工入职邮件");
		simpleMailMessage.setText("欢迎你加入ITCASt大家庭！");
		// 发送邮件
		mailSender.send(simpleMailMessage);
	}
}
