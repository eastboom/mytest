package cn.itcast.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.ContractProduct;

/**
 * 货物模块，dao
 */
public interface ContractProductDao extends BaseDao<ContractProduct, String>{

	/**
	 * 根据船期查询
	 * @param inputDate 船期，如：2018-01
	 * @return
	 */
	@Query("from ContractProduct cp where to_char(cp.contract.shipTime,'yyyy-MM')=?")
	List<ContractProduct> findContractProductByShipTime(String inputDate);

	/**
	 * 根据多个购销合同ids，查询货物
	 * @param contractIds 购销合同ids
	 * @return
	 */
	//@Query("from ContractProduct cp where cp.contract.id in ?1")
	@Query("from ContractProduct cp where cp.contract.id in (?1)")
	List<ContractProduct> findContractProductByContractIds(String[] contractIds);

}












