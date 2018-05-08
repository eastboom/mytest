package cn.itcast.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ModuleDao;
import cn.itcast.domain.Module;
import cn.itcast.service.ModuleService;

@Service
@Transactional
public class ModuleServiceImpl extends BaseServiceImpl<Module, String> implements ModuleService {
	
	private ModuleDao moduleDao;
	
	@Resource
	public void setModuleDao(ModuleDao moduleDao) {
		this.moduleDao = moduleDao;
		super.setBaseDao(moduleDao);
	}
}





















