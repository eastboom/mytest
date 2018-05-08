package cn.itcast.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.ShippingOrder;
/**
 * 委托单模块，dao
 * @author 17783
 *
 */
public interface ShippingOrderDao extends BaseDao<ShippingOrder, String>{

	@Query("from ShippingOrder so where so.state = ?")
	List<ShippingOrder> findbyState(int state);
}
