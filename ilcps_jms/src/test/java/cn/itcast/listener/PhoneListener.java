package cn.itcast.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 消息消费监听器，监听Topic主题中的phone信息
 * @author Administrator
 *
 */
public class PhoneListener implements MessageListener{

	@Override
	public void onMessage(Message msg) {
		try {
			// 1. 转换
			MapMessage mapMessage =  (MapMessage) msg;
			
			// 2. 根据key，获取消息中的数据
			String phone = mapMessage.getString("phone");
			
			// 3. 业务处理（发短信）
			System.out.println("消息处理成功--->" + phone);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
