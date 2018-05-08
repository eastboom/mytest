package cn.itcast.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 消息消费监听器，监听Queue队列中的email信息
 * @author Administrator
 *
 */
public class EmailListener implements MessageListener{

	@Override
	public void onMessage(Message msg) {
		try {
			// 1. 转换
			MapMessage mapMessage =  (MapMessage) msg;
			
			// 2. 根据key，获取消息中的数据
			String email = mapMessage.getString("email");
			
			// 3. 业务处理（发邮件）
			System.out.println("消息处理成功--->" + email);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
