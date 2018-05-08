package cn.itcast.common.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.common.service.BaseService;

/**
 * 通用的业务逻辑接口默认实现
 * @param <T>  持久化对象
 * @param <ID> 主键类型
 */
public abstract class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T, ID>{
	
	// DeptDao、UserDao, 这些dao都继承了BaseDao
	
	private BaseDao<T, ID> baseDao;
	// 提供set方法，交给子类调用，给baseDao赋值
	public void setBaseDao(BaseDao<T, ID> baseDao) {
		this.baseDao = baseDao;
	}
	

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	public T findOne(ID id) {
		return baseDao.findOne(id);
	}

	@Override
	public List<T> findAll(Specification<T> spec) {
		return baseDao.findAll(spec);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return baseDao.findAll(pageable);
	}

	@Override
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return baseDao.findAll(spec, pageable);
	}

	@Override
	public void saveOrUpdate(T t) {
		baseDao.save(t);		
	}

	@Override
	public void delete(ID id) {
		baseDao.delete(id);		
	}

}
