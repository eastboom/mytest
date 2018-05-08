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
public class DeptDaoTest5 {

	// 注入dao接口
	@Resource
	private DeptDao deptDao;

	@Test
	public void find() {
		// 查询条件（封装jsp提交数据）
		final String dept="";
		final Integer state = 0;
		
		
		// 条件
		Specification<Dept> spec = new Specification<Dept>() {
			// hql = "from Dept where 1=1 and deptName =? and state=?"
			
			// Predicate 方法返回的对象，一个Predicate表示一个条件  state=?
			// 参数1：root 通过root可以获取查询的属性对象
			// 参数2：query 对Criteria查询支持
			// 参数3：cb 条件构造器，构造条件
			public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				// 封装所有拼接的条件
				List<Predicate> predicateList = new ArrayList<>();
				
				// 条件1
				if (StringUtils.isNoneBlank(dept)) {
					// 构造查询的条件属性
					Expression<String> as = root.get("deptName").as(String.class);
					// 构造条件、设置查询条件值
					Predicate p1 = cb.equal(as, dept);
					// 添加条件
					predicateList.add(p1);
				}
				
				// 条件2
				if (state != null) {
					Predicate p2 = cb.equal(root.get("state").as(Integer.class), state);
					// 添加条件
					predicateList.add(p2);
				}
				
				// 拼接条件并返回: list--->数组
				Predicate[] ps = new Predicate[predicateList.size()];
				return cb.and(predicateList.toArray(ps));
			}
		};
		// 查询
		List<Dept> list = deptDao.findAll(spec);
		System.out.println(list);
	}
}










