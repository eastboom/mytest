package cn.itcast.service;

import java.util.List;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.Dept;

/**
 * 部门模块业务逻辑接口
 */
public interface DeptService extends BaseService<Dept, String> {
	
	/**
	 * 根据父部门id查询
	 * @param id
	 * @return
	 */
	List<Dept> findDeptByParent(String parentId);
}













