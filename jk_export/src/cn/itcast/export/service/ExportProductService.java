package cn.itcast.export.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.export.domain.ExportProduct;
import cn.itcast.export.pagination.Page;


public interface ExportProductService {

	public List<ExportProduct> find(String hql, Class<ExportProduct> entityClass, Object[] params);
	public ExportProduct get(Class<ExportProduct> entityClass, Serializable id);
	public Page<ExportProduct> findPage(String hql, Page<ExportProduct> page, Class<ExportProduct> entityClass, Object[] params);
	
	public void saveOrUpdate(ExportProduct entity);
	public void saveOrUpdateAll(Collection<ExportProduct> entitys);
	
	public void deleteById(Class<ExportProduct> entityClass, Serializable id);
	public void delete(Class<ExportProduct> entityClass, Serializable[] ids);
}
