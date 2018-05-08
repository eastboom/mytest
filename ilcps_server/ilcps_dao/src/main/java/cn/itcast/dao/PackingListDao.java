package cn.itcast.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.PackingList;
import cn.itcast.domain.ShippingOrder;

/**
 * 装箱单模块，dao
 */
public interface PackingListDao extends BaseDao<PackingList, String>{

	@Query("from PackingList pl where pl.state = ?")
	List<PackingList> findbyState(int state);
}












