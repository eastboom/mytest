package cn.itcast.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.ContractProductDao;
import cn.itcast.dao.DeptDao;
import cn.itcast.domain.ContractProduct;
import cn.itcast.domain.Dept;

//2. 执行流程
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ContractProductDaoTest {

	// 注入dao接口
	@Resource
	private ContractProductDao contractProductDao;

	// Caused by: java.lang.IllegalArgumentException: Encountered array-valued parameter binding, but was expecting [java.lang.String (n/a)]
	// 解决：  where cp.contract.id in (?1)  要通过1指定对应方法第一个参数。
	@Test
	public void findOne() {
		String[] contractIds = {"4028817a33812ffd0133813f25940001","4028817a33fc4e280133fd9f8b4e002f"};
		List<ContractProduct> list = contractProductDao.findContractProductByContractIds(contractIds);
		System.out.println(list.size());
	}
}










