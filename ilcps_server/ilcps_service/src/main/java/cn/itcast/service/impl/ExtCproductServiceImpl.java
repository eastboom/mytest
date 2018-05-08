package cn.itcast.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ExtCproductDao;
import cn.itcast.domain.ExtCproduct;
import cn.itcast.service.ExtCproductService;

@Service
@Transactional
public class ExtCproductServiceImpl extends BaseServiceImpl<ExtCproduct, String> implements ExtCproductService {
	
	private ExtCproductDao extCproductDao;
	
	@Resource
	public void setExtCproductDao(ExtCproductDao extCproductDao) {
		this.extCproductDao = extCproductDao;
		super.setBaseDao(extCproductDao);
	}
}





















