package cn.itcast.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.itcast.client.dao.CompanyDao;
import cn.itcast.client.domain.CompanyClient;
import cn.itcast.client.service.CompanyClientService;

@Service
public class CompanyClientServiceImpl implements CompanyClientService {
	
	@Autowired(required=false)
	@Qualifier("companyClientDao")
	private CompanyDao companyClientDao;
	

	@Override
	public CompanyClient get(String id) {
		return companyClientDao.findOne(id);
	}

	@Override
	public void saveOrUpdate(CompanyClient companyClient) {
		companyClientDao.save(companyClient);
		
	}
	
}
