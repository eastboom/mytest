package cn.itcast.jms.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消费Queue队列中的消息：
 * Queue名称是： hello
 */
public class Queue_2_consumer {

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
		Session session = conn.createSession(true, Session.AUTO_ACKNOWLEDGE);
		
		//4. 创建消息发送的目的地对象
		//interface javax.jms.Queue extends javax.jms.Destination 
		Queue queue = session.createQueue("hello");
		
		//6. 创建消费者
		MessageConsumer messageConsumer = session.createConsumer(queue);
		
		//7. 消费消息  (同步)
		// receive() 调用这个方法，如果容器中没有消息，线程处于阻塞状态。直到有消息才结束当前线程。
		// receive(5000)  从容器中如果没有拿到消息的等待时间 5秒
		Message message = messageConsumer.receive(5000);
		if (message != null) {
			TextMessage msg = (TextMessage) message;
			System.out.println("----->" + msg.getText());
		}
		
		
		// 提交事务、关闭
		session.commit();
		session.close();
		conn.close();
	}
}








