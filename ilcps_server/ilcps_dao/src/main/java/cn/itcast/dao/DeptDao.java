package cn.itcast.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.Dept;

/**
 * Spring Data Jpa dao 接口
 *
 */
public interface DeptDao extends BaseDao<Dept, String>{

	/**
	 * 1. findBy + 属性查询
	 * 2. JPQL  Java Persist Query Language  Java持久化查询预压，JPA查询。 （相当于HQL）
	 *    JPQL查询
	 *    JPQL本地sql查询
	 *    
	 *    JPQL 更新
	 */
	
	// 需求1： 根据部门名称查询
	List<Dept> findByDeptName(String name);
	
	// 需求2： 根据部门名称和状态查询
	List<Dept> findByDeptNameAndState(String name,Integer state);
	
	// 需求3： 根据部门名称模糊查询
	List<Dept> findByDeptNameLike(String name);
	
	// 需求4： 自定义JPQL语句，查询部门、状态.
	// nativeQuery 是否本地原生sql查询； 默认false
	@Query(value="from Dept d where d.state=?2 and d.deptName=?1",nativeQuery=false)
	List<Dept> queryByCondition(String deptName,Integer state);
	
	// 原生sql查询
	@Query(value="select * from dept_p where dept_name=? and state=?",nativeQuery=true)
	List<Dept> queryByCondition2(String deptName,Integer state);
	
	// 需求5： JPQL 更新
	@Query("update Dept set deptName=?2 where id=?1")
	@Modifying
	void updateDept(String id,String deptName);

	/**
	 * 根据父部门查询
	 * @param parentId
	 * @return
	 */
	@Query("from Dept d where d.parent.id=?")
	List<Dept> findDeptByParent(String parentId);
}












