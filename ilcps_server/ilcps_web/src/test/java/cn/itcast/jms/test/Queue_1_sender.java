package cn.itcast.jms.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 发送消息到Queue队列中。
 * Queue名称是： hello
 */
public class Queue_1_sender {

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
		
		//5. 创建消息
		TextMessage msg = session.createTextMessage();
		msg.setText("发送Queue消息到hello队列中.....");
		
		//6. 消息生产者
		MessageProducer messageProducer = session.createProducer(queue);
		
		//7. 发送消息
		messageProducer.send(msg);
		
		// 关闭
		session.close();
		conn.close();
	}
}








