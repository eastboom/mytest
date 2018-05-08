package cn.itcast.exception;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.domain.Contract;
import cn.itcast.service.impl.ContractServiceImpl;
import cn.itcast.utils.MailUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class App2 {
	
	@Test
	public void app() {
		//获取当前时间
				String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				System.out.println(date1+"==============");
				//查询有没有到期的购销合同
				ContractServiceImpl contractServiceImpl = new ContractServiceImpl();
				List<Contract> period = contractServiceImpl.findContractByDeliveryPeriod(date1);
				if(period != null && period.size() >0) {
					for (Contract contract : period) {
						try {
							MailUtil.sendMsg("13725936808@163.com", "购销合同到期", "你好,你有一份购销合同到期"+contract.getId());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}else {
					try {
						MailUtil.sendMsg("13725936808@163.com", "购销合同到期", "你好,你没有份购销合同到期");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	}
}
