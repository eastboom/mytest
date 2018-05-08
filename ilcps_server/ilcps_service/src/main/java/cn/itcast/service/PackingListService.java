package cn.itcast.service;

import java.util.List;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.PackingList;
import cn.itcast.domain.ShippingOrder;

/**
 * 购销合同模块业务逻辑接口
 */
public interface PackingListService extends BaseService<PackingList, String> {
	List<PackingList> findbyState(int state);
}













