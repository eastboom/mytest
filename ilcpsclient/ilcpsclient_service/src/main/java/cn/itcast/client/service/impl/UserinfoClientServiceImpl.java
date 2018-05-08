package cn.itcast.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.itcast.client.dao.UserinfoClientDao;
import cn.itcast.client.domain.UserinfoClient;
import cn.itcast.client.service.UserinfoClientService;

@Service
public class UserinfoClientServiceImpl implements UserinfoClientService {
	
	@Autowired(required=false)
	@Qualifier("userinfoClientDao")
	private UserinfoClientDao userinfoClientDao;
	

	@Override
	public UserinfoClient get(String id) {
		return userinfoClientDao.findOne(id);
	}

	@Override
	public void saveOrUpdate(UserinfoClient userinfoClient) {
		userinfoClientDao.save(userinfoClient);
		
	}
	
}
