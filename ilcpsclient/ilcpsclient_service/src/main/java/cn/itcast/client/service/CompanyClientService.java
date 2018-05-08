package cn.itcast.client.service;

import cn.itcast.client.domain.CompanyClient;

public interface CompanyClientService {
	//获取一条记录
	public CompanyClient get(String id);
	//新增和修改保存
	public  void saveOrUpdate(CompanyClient companyClient);

	
}
