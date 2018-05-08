package cn.itcast.dao.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.dao.DeptDao;
import cn.itcast.domain.Dept;

//3. JpaRepository 接口方法使用测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DeptDaoTest3 {

	// 注入dao接口
	@Resource
	private DeptDao deptDao;

	@Test
	public void find() {
		// deptDao.findOne("");
		// deptDao.findAll();
		// deptDao.findAll(pageable)
		
		// 查询全部
		List<Dept> list = deptDao.findAll();//alt+shit+L
		System.out.println(list);
		
		// 分页查询, 查询第一页
		int page = 1;
		int size = 5;
		// 封装分页参数, 注意：参数1表示当前页，从0开始
		Pageable pageable = new PageRequest(page-1, size);
		
		// 查询
		Page<Dept> pageData = deptDao.findAll(pageable);
		
		// 结果结果：总记录数、当前页数据
		long totalElements = pageData.getTotalElements();
		List<Dept> listPage = pageData.getContent();
		int totalPages = pageData.getTotalPages();
		
		System.out.println("总记录数 " + totalElements);
		System.out.println("当前页数据 " + listPage);
		System.out.println("总页数 " + totalPages);
		
	}
	
	@Test
	public void delete() {
		deptDao.delete("8a7e81606255c59e016255c5a4e60000");
		
		
		// Dept d = new Dept();
		// d.setId("8a7e81bf61f8def80161f8df05800000");
		// deptDao.delete(d);
	}
}










