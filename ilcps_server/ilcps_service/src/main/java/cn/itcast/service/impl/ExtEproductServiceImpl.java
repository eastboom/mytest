package cn.itcast.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ExtEproductDao;
import cn.itcast.domain.ExtEproduct;
import cn.itcast.service.ExtEproductService;

@Service  // 加入ioc容器
@Transactional
public class ExtEproductServiceImpl extends BaseServiceImpl<ExtEproduct, String> implements ExtEproductService{
	
	private ExtEproductDao extEproductDao;
	
	@Resource
	protected void setBaseDao(ExtEproductDao extEproductDao) {
		super.setBaseDao(extEproductDao);
		this.extEproductDao = extEproductDao;
	}
}








