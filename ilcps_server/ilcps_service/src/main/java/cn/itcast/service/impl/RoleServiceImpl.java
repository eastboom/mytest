package cn.itcast.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.RoleDao;
import cn.itcast.domain.Role;
import cn.itcast.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {
	
	private RoleDao roleDao;
	
	@Resource
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(roleDao);
	}
	
}





















