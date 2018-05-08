package cn.itcast.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.itcast.domain.Contract;
import cn.itcast.service.ContractService;
import cn.itcast.utils.MailUtil;

/**
 * 定时执行的任务类
 */
public class JobDetail {
	
	/**
	 * 交期到，邮件提醒：
	 * 1） 数据分析
	 *   交期，是在contract_c表.
	 *   邮件从哪里找？
	 *   方案1：给用户发送邮件。（用户就是指销售人员）
	 *       找到contract_c表，找到记录的创建人，再找user_info_c,最后找到要发送的邮件。
	 *   方案2： 给厂家发送邮件
	 *       找到contract_c表，找到货物，找到货物对应的每一个厂家，找到厂家邮箱。
	 *       
	 * 2) 获取当前时间，格式化为：2018-04-19
	 * 3) 按照交期查询，查询条件就是当前时间。
	 *    select distinct(email) from contract_c where to_char(DELIVERY_PERIOD,'yyyy-MM-dd') = 2018-04-19....
	 * 
	 * 注意：
	 * 	    1. 任务调度工程ilcps_quartz的JobDetail类要注入service。
	 * 		2. ilcps_quartz要依赖service
	 * 		3. 所以，运行时期，会去本地库找service，找dao，找domain
	 *         先install domain、dao、service
	 *      4. 最后再运行......
	 */
	//获取注入ContractService
	@Resource
	private ContractService contractService;
	
	
	/**
	 * 定时执行的方法
	 */
	public void executeJob(){
		
		//获取当前时间
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		//查询有没有到期的购销合同
		List<Contract> period = contractService.findContractByDeliveryPeriod(date);
		
		if(period != null && period.size() >0) {
			for (Contract contract : period) {
				try {
					MailUtil.sendMsg("lucy@itheima.com", "购销合同到期", "你好,你有一份购销合同到期"+contract.getId());
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










