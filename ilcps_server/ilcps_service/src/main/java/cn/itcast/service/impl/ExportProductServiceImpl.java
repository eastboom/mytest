package cn.itcast.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.ExportProductDao;
import cn.itcast.domain.ExportProduct;
import cn.itcast.service.ExportProductService;

@Service  // 加入ioc容器
@Transactional
public class ExportProductServiceImpl extends BaseServiceImpl<ExportProduct, String> implements ExportProductService{
	
	private ExportProductDao exportProductDao;
	
	@Resource
	protected void setBaseDao(ExportProductDao exportProductDao) {
		super.setBaseDao(exportProductDao);
		this.exportProductDao = exportProductDao;
	}
}








