package cn.itcast.service;

import java.util.List;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.Contract;

/**
 * 购销合同模块业务逻辑接口
 */
public interface ContractService extends BaseService<Contract, String> {

	List<Contract> findContractByDeliveryPeriod(String date);
	
}













