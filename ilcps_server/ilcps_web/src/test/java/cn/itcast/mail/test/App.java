package cn.itcast.mail.test;

import org.junit.Test;

import cn.itcast.utils.MailUtil;

public class App {

	// 测试发邮件工具类
	@Test
	public void sendMail() throws Exception {
		MailUtil.sendMsg("yuanjie8090@163.com", "新员工入职邮件", "欢迎你加入ITCASt大家庭！");
	}
	
}
