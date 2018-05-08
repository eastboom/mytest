package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ContractDao;
import cn.itcast.domain.Contract;
import cn.itcast.service.ContractService;

@Service
@Transactional
public class ContractServiceImpl extends BaseServiceImpl<Contract, String> implements ContractService {
	
	@Resource
	private ContractDao contractDao;
	
	@Resource
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
		super.setBaseDao(contractDao);
	}

	@Override
	public List<Contract> findContractByDeliveryPeriod(String date) {
		return contractDao.findContractByDeliveryPeriod(date);
	}

}





















