package cn.itcast.dao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.DeptDao;

//4. 接口自定义方法
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DeptDaoTest4 {

	// 注入dao接口
	@Resource
	private DeptDao deptDao;

	@Test
	public void find() {
		System.out.println(deptDao.findByDeptName("商贸集团"));
		System.out.println(deptDao.findByDeptNameAndState("商贸集团",0));
		System.out.println(deptDao.findByDeptNameLike("%贸%"));
		
		// 测试JPQL查询
		System.out.println(deptDao.queryByCondition("商贸集团",1));
	}
	
	/*
	 *  JPQL 更新
	 *  1） JPQL 更新，必须在接口添加注解@Modifying，否则报错：
	 *     Not supported for DML operations 
	 *  2）  JPQL 更新，需要事务,在测试方法上添加事物注解：@Transactional
	 *     TransactionRequiredException: Executing an update/delete query
	 *  3）  如果操作要反映到数据库，需要提交事务，再测试方法添加@Rollback(false)
	 *     @Rollback(false)     Committed transaction for test
	 *     @Rollback(true) 		Rolled back transaction for test 
	 */
	@Test
	@Transactional
	@Rollback(false)  // 不回滚
	public void update() {
		deptDao.updateDept("8a7e81bf61f8d5f00161f8dd4b8f0002", "取经部");
	}
}










