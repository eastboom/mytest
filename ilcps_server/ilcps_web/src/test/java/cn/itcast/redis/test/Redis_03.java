package cn.itcast.redis.test;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Spring Data 整合Redis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-springdata-redis.xml")
public class Redis_03 {
	
	// 注入redisTemplate
	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void testName() throws Exception {
		// 放数据到redis缓存
		// 存储到缓存中，有效时间30min
		redisTemplate.opsForValue().set("usa", "America", 30, TimeUnit.MINUTES);
		
		// 获取数据
		String value = redisTemplate.opsForValue().get("usa");
		System.out.println(value);
	}
}







