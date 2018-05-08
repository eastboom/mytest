package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.FactoryDao;
import cn.itcast.domain.Factory;
import cn.itcast.service.FactoryService;

@Service
@Transactional
public class FactroyServiceImpl extends BaseServiceImpl<Factory, String> implements FactoryService {
	
	private FactoryDao factoryDao;
	
	@Resource
	public void setFactoryDao(FactoryDao factoryDao) {
		this.factoryDao = factoryDao;
		super.setBaseDao(factoryDao);
	}
	
	@Override
	public List<Factory> findByCtype(String ctype) {
		return factoryDao.findByCtype(ctype);
	}
}





















