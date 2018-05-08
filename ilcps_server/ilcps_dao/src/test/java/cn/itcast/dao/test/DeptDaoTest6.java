package cn.itcast.dao.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.DeptDao;
import cn.itcast.domain.Dept;

//4. 动态条件查询
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DeptDaoTest6 {

	// 注入dao接口
	@Resource
	private DeptDao deptDao;

	/**
	 * @DynamicInsert(false) 不支持动态sql
	 *     Dept dept = new Dept();
	 *     deptDao.save(dept);
	 *     生成的sql： insert into dept_p (dept_name, parent_id, state, dept_id) values (?, ?, ?, ?)
	 *     
	 * @DynamicInsert(true)  支持动态sql
	 *     Dept dept = new Dept();
	 *     deptDao.save(dept);
	 *     生成的SQL：insert into dept_p (dept_id) values (?)
	 *     
	 */
	@Test
	public void dynamic_insert() {
		// 对象
		Dept dept = new Dept();
		
		// 保存
		deptDao.save(dept);
	}
}










