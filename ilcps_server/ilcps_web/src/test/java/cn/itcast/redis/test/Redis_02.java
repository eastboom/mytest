package cn.itcast.redis.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Spring整合Redis
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-redis.xml")
public class Redis_02 {
	
	// 注入连接池
	@Resource
	private JedisPool jedisPool;

	@Test
	public void testName() throws Exception {
		Jedis jedis = jedisPool.getResource();
		System.out.println(jedis.get("cn"));
		jedis.close();
	}
}







