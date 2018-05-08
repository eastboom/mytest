package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ShippingOrderDao;
import cn.itcast.domain.ShippingOrder;
import cn.itcast.service.ShippingOrderService;

@Service
@Transactional
public class ShippingOrderServiceImpl extends BaseServiceImpl<ShippingOrder, String> implements ShippingOrderService {
	
	private ShippingOrderDao shippingOrderDao;
	
	@Resource
	public void setShippingOrderDao(ShippingOrderDao shippingOrderDao) {
		this.shippingOrderDao = shippingOrderDao;
		super.setBaseDao(shippingOrderDao);
	}

	@Override
	public List<ShippingOrder> findbyState(int state) {
		return shippingOrderDao.findbyState(state);
	}
}





















