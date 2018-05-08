package cn.itcast.service;

import java.util.List;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.ContractProduct;

/**
 * 货物模块业务逻辑接口
 */
public interface ContractProductService extends BaseService<ContractProduct, String> {

	/**
	 * 根据船期查询
	 * @param inputDate
	 * @return
	 */
	List<ContractProduct> findContractProductByShipTime(String inputDate);
	
}













