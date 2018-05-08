package cn.itcast.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.DeptDao;
import cn.itcast.domain.Dept;
import cn.itcast.service.DeptService;

@Service
@Transactional
public class DeptServiceImpl extends BaseServiceImpl<Dept, String> implements DeptService {
	
	// 注入dao
	private DeptDao deptDao;
	
	// 访问此方法需要进行权限校验，当前用户要有“部门列表”的权限
	@RequiresPermissions("部门管理")
	@Override
	public Page<Dept> findAll(Specification<Dept> spec, Pageable pageable) {
		return deptDao.findAll(spec, pageable);
	}
	
	// 根据方法参数类型去容器找对象注入进来（方法注入）
	@Resource
	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
		// 调用父类的方法，把deptDao传给父类
		super.setBaseDao(deptDao);
	}
	
	@Override
	public void saveOrUpdate(Dept dept) {
		// 判断，如果父部门的id为"", 设置为null
		if (dept.getParent() != null) {
			if ("".equals(dept.getParent().getId())) {
				// 父部门设置为NULL，这样就不会用空字符串查询找对应的部门
				dept.setParent(null);
			}
		}
		deptDao.save(dept);
	}
	@Override
	public List<Dept> findDeptByParent(String parentId) {
		return deptDao.findDeptByParent(parentId);
	}
}







