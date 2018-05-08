package cn.itcast.jms.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 发送消息到Topic主题消息中
 */
public class Topic_1_sender {

	// 发送消息
	public static void main(String[] args) throws Exception {
		//1. 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		//2. 创建连接
		Connection conn = connectionFactory.createConnection();
		// 开启连接
		conn.start();
		//3. 创建session
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//4. 创建消息发送的目的地对象
		Topic topic = session.createTopic("hello2");
		
		//5. 创建消息
		TextMessage msg = session.createTextMessage();
		msg.setText("发送Topic消息到hello2队列中.....");
		
		//6. 消息生产者
		MessageProducer messageProducer = session.createProducer(topic);
		
		//7. 发送消息
		messageProducer.send(msg);
		
		// 关闭
		session.close();
		conn.close();
	}
}








