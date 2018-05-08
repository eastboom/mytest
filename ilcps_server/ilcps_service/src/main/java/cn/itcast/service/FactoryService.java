package cn.itcast.service;

import java.util.List;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.Factory;

/**
 * 工厂模块业务逻辑接口
 */
public interface FactoryService extends BaseService<Factory, String> {

	/**
	 * 根据类型查询生产厂家
	 * @param ctype 厂家类型（货物、附件）
	 * @return
	 */
	List<Factory> findByCtype(String ctype);
	
}













