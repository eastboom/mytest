package cn.itcast.jms.test2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消费消息
 */
public class Topic_2_consumer2 {

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
		
		//5. 创建消息消费者
		MessageConsumer messageConsumer = session.createConsumer(topic);
		
		//6. 消费消息
		messageConsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				MapMessage msg = (MapMessage) message;
				try {
					String email = msg.getString("email");
					String phone = msg.getString("phone");
					System.out.println(email + "," + phone);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		// 保持监听器运行
		while(true){}
	}
}








