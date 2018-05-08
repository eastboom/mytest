package cn.itcast.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ContractDao;
import cn.itcast.dao.ContractProductDao;
import cn.itcast.domain.Contract;
import cn.itcast.domain.ContractProduct;
import cn.itcast.domain.ExtCproduct;
import cn.itcast.service.ContractProductService;

@Service
@Transactional
public class ContractProductServiceImpl extends BaseServiceImpl<ContractProduct, String> implements ContractProductService {
	
	private ContractProductDao contractProductDao;
	// 注入购销合同dao
	@Resource
	private ContractDao contractDao;
	
	@Resource
	public void setContractProductDao(ContractProductDao contractProductDao) {
		this.contractProductDao = contractProductDao;
		super.setBaseDao(contractProductDao);
	}
	
	@Override
	public void saveOrUpdate(ContractProduct t) {
		// 判断
		if (StringUtils.isBlank(t.getId())) {
			/** 新增保存 */
			// 购销合同总金额 = 购销合同总金额 + 货物金额
			
			// 1. 获取购销合同id
			String contractId = t.getContract().getId();
			// 2. 根据id查询
			Contract contract = contractDao.findOne(contractId);
			
			// 3. 货物金额
			Double amount = t.getPrice() * t.getCnumber();
			// 4. 设置货物金额
			t.setAmount(amount);
			
			// 5. 设置购销合同金额
			if (contract.getTotalAmount() == null) {
				contract.setTotalAmount(0d);
			}
			contract.setTotalAmount(contract.getTotalAmount() + amount);
			
			// 6. 保存购销合同
			// contractDao.save(contract);
		} else {
			/** 修改 */
			// 购销合同总金额 = 购销合同总金额  + 修改后货物金额 - 修改前货物金额
			
			//1. 修改前货物金额
			double oldAmount = t.getAmount();
			
			//2. 修改后货物金额
			double newAmount = t.getPrice() * t.getCnumber();
			
			//3. 设置货物金额
			t.setAmount(newAmount);
			
			//4. 修改购销合同金额
			Contract contract = t.getContract();
			contract.setTotalAmount(contract.getTotalAmount() + newAmount - oldAmount);
			//5. 保存购销合同
			//contractDao.save(contract);
		}
		
		// 保存或者修改
		contractProductDao.save(t);
	}
	
	@Override
	public void delete(String id) {
		/** 删除货物(购销合同总金额=购销合同总金额-货物-货物下附件) */
		
		//1. 根据货物id查询
		ContractProduct cp = contractProductDao.findOne(id);
		
		//2. 货物购销合同
		Contract contract = cp.getContract();
		// 购销合同总金额 - 货物
		contract.setTotalAmount(contract.getTotalAmount()-cp.getAmount());
		
		//3. 获取货物附件、遍历
		Set<ExtCproduct> extCproducts = cp.getExtCproducts();
		if (extCproducts != null && extCproducts.size() > 0) {
			for (ExtCproduct extCproduct : extCproducts) {
				// 购销合同总金额 - 附件
				contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
			}
		}
		
		//4. 保存购销合同, 修改合同总金额
		contractDao.save(contract);
		
		//5. 删除货物
		contractProductDao.delete(cp);
	}
	
	
	@Override
	public List<ContractProduct> findContractProductByShipTime(String inputDate) {
		return contractProductDao.findContractProductByShipTime(inputDate);
	}
}





















