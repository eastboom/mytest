package cn.itcast.common.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * 通用的业务逻辑接口抽取
 * @param <T>  持久化对象
 * @param <ID> 主键类型
 */
public interface BaseService<T,ID extends Serializable> {

	/**
	 * 列表查询
	 */
	List<T> findAll();
	
	/**
	 * 根据id查询
	 */
	T findOne(ID id);

	/**
	 * 条件查询
	 * @param spec 条件表达式
	 * @return
	 */
	List<T> findAll(Specification<T> spec);
	
	/**
	 * 分页查询
	 */
	Page<T> findAll(Pageable pageable);
	
	/**
	 * 条件分页
	 */
	Page<T> findAll(Specification<T> spec,Pageable pageable);
	
	/**
	 * 添加或修改
	 */
	void saveOrUpdate(T t);
	
	/**
	 * 删除
	 */
	void delete(ID id);

}
