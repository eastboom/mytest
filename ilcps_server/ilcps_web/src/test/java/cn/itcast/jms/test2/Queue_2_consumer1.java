package cn.itcast.jms.test2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消费者1
 */
public class Queue_2_consumer1 {

	// 发送消息
	public static void main(String[] args) throws Exception {
		//1. 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
		
		//2. 创建连接
		Connection conn = connectionFactory.createConnection();
		// 开启连接
		conn.start();
		
		//3. 创建session
		// 参数1： 是否需要事务环境，如果为true表示需要事务环境，最后发送消息后需要提交事务。
		// 参数2： 自动应答机制(表示从容器消费消息后自动通知消息容器)
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//4. 创建消息发送的目的地对象
		//interface javax.jms.Queue extends javax.jms.Destination 
		Queue queue = session.createQueue("hello");
		
		//6. 创建消费者
		MessageConsumer messageConsumer = session.createConsumer(queue);
		
		//7. 消费消息  (监听器消费消息，异步)
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
		
		
		// 保持监听器的运行
		while(true){}
		
	}
}








