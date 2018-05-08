package cn.itcast.listener;

import java.util.UUID;

import javax.annotation.Resource;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import cn.itcast.utils.MailUtil;

/**
 * 邮件消息监听器
 * @author Administrator
 *
 */
@Component("mailListener")
public class MailListener implements MessageListener {
	
	// 注入redisTemplate
	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void onMessage(Message message) {
		try {
			//1. 转换
			MapMessage mapMessage = (MapMessage) message;
			
			//2. 获取邮箱
			String email = mapMessage.getString("email");
			
			//3. 邮件激活，激活码
			//http://192.168.95.63:9000/userAction_activeUser?email=xx&code=激活码
			String uuid = UUID.randomUUID().toString();
			// 激活码存储到redis缓存中
			redisTemplate.opsForValue().set(email, uuid);
			
			//4. 调用工具类发送邮件
			String subject = "【新用户注册激活邮件】";
			String content = "欢迎来到国际物流云商前端系统，离注册成功还差一步，点击下面地址激活，<a href='http://192.168.95.29:9000/userAction_activeUser?email="+email+"&code="+uuid+"'>邮件激活</a>，如果不能点击上面地址，请求url拷贝浏览器请求。";
			MailUtil.sendMsg(email, subject, content);
			
			System.out.println(email + "发送邮件成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}










