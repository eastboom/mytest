package cn.itcast.listener;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import cn.itcast.utils.SmsUtil;

/**
 * 手机短信监听器
 * @author Administrator
 *
 */
@Component("phoneListener")
public class TelePhoneListener implements MessageListener {
	
	// 注入redisTemplate
	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void onMessage(Message message) {
		try {
			//1. 转换
			MapMessage mapMessage = (MapMessage) message;
			
			//2. 获取手机号码
			String telephone = mapMessage.getString("telephone");
			
			//3. 随机生成6位验证码  (888888)
			String code = (int)((Math.random() * 9 + 1) * 100000) + "";
			
			//4. 发短信
			SmsUtil.sendSms(telephone, code);
			
			//6. 验证码存储到redis缓存中（前端系统就可以获取redis缓存中手机号对应的验证码，就可以和用户输入的验证码比较）
			redisTemplate.opsForValue().set(telephone, code,30,TimeUnit.MINUTES);
			
			System.out.println(telephone+"," + code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}










