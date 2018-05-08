package cn.itcast.dao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.DeptDao;
import cn.itcast.domain.Dept;

//2. 执行流程
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DeptDaoTest2 {

	// 注入dao接口
	@Resource
	private DeptDao deptDao;

	/**
	 * 1. 加载Spring配置文件，扫描sao接口
	 * 2. 对dao接口，生成代理对象
	 *    通过JdkDynamicAopProxy这个类生成代理对象
	 * 3. 执行接口方法，
	 *    由代理对象，调用SimpleJpaRepository中的findOne()默认实现方法
	 */
	@Test
	public void findOne() {
		//class com.sun.proxy.$Proxy34  在运行时期，生成的代理对象。
		System.out.println(deptDao.getClass());
		
		//代理--->SimpleJpaRepository.findOne();
		Dept dept = deptDao.findOne("100");
		System.out.println(dept);
	}
}










