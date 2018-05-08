package cn.itcast.jms.test3_spring;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring整合ActiveMQ发送消息
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-mq-send.xml")
public class Sender {

	// 注入发送Queue、Topic消息的模板对象
	@Resource
	private JmsTemplate jmsQueueTemplate;
	@Resource
	private JmsTemplate jmsTopicTemplate;
	
	
	@Test
	public void sender() throws Exception {
		// 发送Queue队列消息
		jmsQueueTemplate.send("email", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("email", "610731230@qq.com");
				return mapMessage;
			}
		});
		
		
		// 发送Topic主题消息
		jmsTopicTemplate.send("phone", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("phone", "18665591009");
				return mapMessage;
			}
		});
	}
}













