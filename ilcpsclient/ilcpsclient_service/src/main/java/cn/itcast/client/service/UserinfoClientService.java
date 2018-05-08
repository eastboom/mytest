package cn.itcast.client.service;

import cn.itcast.client.domain.UserinfoClient;


public interface UserinfoClientService {
	//获取一条记录
	public UserinfoClient get(String id);
	//新增和修改保存
	public  void saveOrUpdate(UserinfoClient userinfoClient);

	
}
