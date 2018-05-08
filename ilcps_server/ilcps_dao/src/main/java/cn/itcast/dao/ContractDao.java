package cn.itcast.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;


import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.Contract;

/**
 * 购销合同模块，dao
 */
public interface ContractDao extends BaseDao<Contract, String>{
	
	@Query(value="select * from contract_c c where to_char(c.delivery_Period,'yyyy-MM-dd')=?1",nativeQuery=true)
	List<Contract> findContractByDeliveryPeriod(String date);

}












