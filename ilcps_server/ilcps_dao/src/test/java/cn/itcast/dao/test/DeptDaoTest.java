package cn.itcast.dao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.DeptDao;
import cn.itcast.domain.Dept;

//1. 基本用法
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DeptDaoTest {

	// 注入dao接口
	@Resource
	private DeptDao deptDao;

	// 主键查询
	@Test
	public void find() {
		Dept dept = deptDao.findOne("100");
		System.out.println(dept);
	}

	
		

}
