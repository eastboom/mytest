package cn.itcast.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.InvoiceDao;
import cn.itcast.domain.Invoice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class InvoiceDaoTest {

	@Resource
	private InvoiceDao invoiceDao;
	
	@Test
	public void test(){
		
		List<Invoice> list = invoiceDao.findAll();
		System.out.println(list);
	}
}
