package cn.itcast.redis.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis_01 {

	// 存、取数据
	@Test
	public void testName() throws Exception {
		
		// 创建连接池配置 
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 设置最大连接
		poolConfig.setMaxTotal(10);
		// 设置最大空闲连接
		poolConfig.setMaxIdle(5);

		// 创建redis连接池对象
		JedisPool jedisPool = new JedisPool(poolConfig, "localhost",6379);
		Jedis jedis = jedisPool.getResource();
		// 获取数据
		System.out.println(jedis.get("cn"));
		
		// 关闭
		jedis.close();
		jedisPool.close();
	}
}
