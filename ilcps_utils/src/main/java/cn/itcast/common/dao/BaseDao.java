package cn.itcast.common.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 通用dao
 * @param <T>  当前操作的持久化对象
 * @param <ID> 持久化对象的主键的类型
 */
@NoRepositoryBean // 表示当前接口不会生成代理对象
public interface BaseDao<T,ID extends Serializable> 
	extends JpaRepository<T, ID>,JpaSpecificationExecutor<T> {

}
