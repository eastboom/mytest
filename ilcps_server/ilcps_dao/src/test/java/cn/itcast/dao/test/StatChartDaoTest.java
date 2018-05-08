package cn.itcast.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.ContractProductDao;
import cn.itcast.dao.DeptDao;
import cn.itcast.dao.StatChartDao;
import cn.itcast.domain.ContractProduct;
import cn.itcast.domain.Dept;

//2. 执行流程
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StatChartDaoTest {

	// 注入dao接口
	@Resource
	private StatChartDao statChartDao;

	@Test
	public void findOne() {
//		List<Object[]> list = statChartDao.factorySale();
//		System.out.println(list);
		
		System.out.println(statChartDao.productSale(10));
	}
}










