package cn.itcast.service;

import java.util.List;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.ShippingOrder;

/**
 * 委托单模块业务逻辑接口
 */
public interface ShippingOrderService extends BaseService<ShippingOrder, String> {
	
	List<ShippingOrder> findbyState(int state);
}













