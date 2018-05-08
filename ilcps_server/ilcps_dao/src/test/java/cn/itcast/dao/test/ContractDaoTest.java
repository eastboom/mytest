package cn.itcast.dao.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.ContractDao;
import cn.itcast.dao.DeptDao;
import cn.itcast.domain.Contract;
import cn.itcast.domain.Dept;
import cn.itcast.utils.MailUtil;

//1. 基本用法
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ContractDaoTest {

	// 注入dao接口
	@Resource
	private ContractDao contractDao;

	// 主键查询
	@Test
	public void findOne() {
		String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		System.out.println(date1+"==============");
		//查询有没有到期的购销合同
		List<Contract> period = contractDao.findContractByDeliveryPeriod(date1);
		if(period != null && period.size() >0) {
			for (Contract contract : period) {
				try {
					System.out.println(contract);
					MailUtil.sendMsg("13725936808@163.com", "购销合同到期", "你好,你有一份购销合同到期"+contract.getId());
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}else {
			try {
				System.out.println("====adsagasadg");
				MailUtil.sendMsg("13725936808@163.com", "购销合同到期", "你好,你没有份购销合同到期");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

}
