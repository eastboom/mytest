package cn.itcast.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.ShippingOrderDao;
import cn.itcast.domain.ShippingOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ShippinOrderDaoTest {

	@Resource
	private ShippingOrderDao shippingOrderDao;
	
	@Test
	public void test(){
		List<ShippingOrder> list = shippingOrderDao.findbyState(1);
		System.out.println(list);
	}
}
