package cn.itcast.dao;

import java.util.List;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.Factory;

/**
 * 工厂模块，dao
 */
public interface FactoryDao extends BaseDao<Factory, String>{

	/**
	 * 根据类型查询生产厂家
	 * @param ctype 厂家类型（货物、附件）
	 * @return
	 */
	List<Factory> findByCtype(String ctype);

}












