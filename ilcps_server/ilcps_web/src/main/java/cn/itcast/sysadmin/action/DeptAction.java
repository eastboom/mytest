package cn.itcast.sysadmin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Dept;
import cn.itcast.service.DeptService;

/**
 * 跳转的类型：
 *	 dispatcher 转发
 *   redirect   重定向到页面
 *   redirectAction 重定向到action
 *   stream  返回流
 */
@Namespace("/sysadmin")
@Results({
	@Result(name="alist",type="redirectAction",location="deptAction_list")
})
public class DeptAction extends BaseAction<Dept>{

	// 注入service
	@Resource
	private DeptService deptService;
	
	
	/**
	 * 1. 进入jDeptList.jsp页面
	 */
	@Action(value="deptAction_list",results={@Result(name="list",location="/WEB-INF/pages/sysadmin/dept/jDeptList.jsp")})
	public String list(){
		// shiro权限校验
		// a. 先获取Subject用户
		//Subject subject = SecurityUtils.getSubject();
		// b. 权限校验
		// subject.isPermitted("部门列表");  如果有权限为true，否则返回false
		// subject.checkPermission("部门列表");如果没有权限，则报错
		// subject.checkPermission("部门列表");
		return "list";
	}
	
	/**
	 * 2. jDeptList.jsp datagrid发送的异步请求，返回json格式数据
	 */
	@Action(value="deptAction_listResult")
	public void listResult(){
		
		// 构造条件表达式
		Specification<Dept> spec = new Specification<Dept>() {
			public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				//root.get("parent.deptName")
				//涉及对象关联查询（多表关联查询）
				//Join<Object, Object> root2 = root.join("parent",JoinType.INNER);
				//root2.get("deptName");
				
				// 1. 保存所有条件
				List<Predicate> predicateList = new ArrayList<>();
				
				// 2. 条件1： 部门名称（模糊查询)
				if (StringUtils.isNotBlank(getModel().getDeptName())) {
					Predicate p_name = cb.like(root.get("deptName").as(String.class), "%"+getModel().getDeptName()+"%");
					predicateList.add(p_name);	//添加条件
				}
				// 3. 条件2： 状态
				if (getModel().getState() != null) {
					Predicate p_state = cb.equal(root.get("state").as(Integer.class), getModel().getState());
					predicateList.add(p_state);	//添加条件
				}
				
				// 4. 拼接条件
				Predicate[] ps = new Predicate[predicateList.size()];
				return cb.and(predicateList.toArray(ps));
			}
		};
		
		
		//2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		
		//2.2 调用service,
		Page<Dept> pageData = deptService.findAll(spec,pageable);
		
		//2.3构造datagrid需要的json格式数据
		//{"total":100, "rows":[{},{}]}
		Map<String,Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", pageData.getContent());
		
		// 对象转换为json
		parseObjToJson(results);
	}
	
	/**
	 * 3. 进入添加页面
	 */
	@Action(value="deptAction_tocreate",
			results={@Result(name="tocreate",location="/WEB-INF/pages/sysadmin/dept/jDeptCreate.jsp")})
	public String tocreate(){
		return "tocreate";
	}
	
	/**
	 * 4. 查询所有部门（页面作为父部门显示）
	 */
	@Action("deptAction_ajaxDept")
	public void ajaxDept(){
		// 条件
		Specification<Dept> spec = new Specification<Dept>() {
			public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 构造条件并返回, state=1
				return cb.equal(root.get("state").as(Integer.class), 1);
			}
		};
		// 查询所有部门 (查询状态为1的部门)
		List<Dept> list = deptService.findAll(spec);
		
		// 对象转json， 输出
		parseObjToJson(list);
	}
	
	/**
	 * 5. 添加保存
	 */
	@Action(value="deptAction_insert")
	public String insert(){
		//3.1 调用service新增保存
		deptService.saveOrUpdate(getModel());
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	/**
	 * 6. 进入修改页面
	 */
	@Action(value="deptAction_toupdate",
			results={@Result(name="toupdate",location="/WEB-INF/pages/sysadmin/dept/jDeptUpdate.jsp")})
	public String toupdate(){
		//6.1 根据id查询
		Dept dept =  deptService.findOne(getModel().getId());
		//6.2放入值栈
		push(dept); //${id}
		return "toupdate";
	}
	
	
	/**
	 * 7. 修改保存
	 */
	@Action(value="deptAction_update")
	public String update(){
		//3.1 调用service保存
		deptService.saveOrUpdate(getModel());
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	/**
	 * 8. 删除 (实现1)
	 */
	//@Action(value="deptAction_delete")
	public void delete_bak(){
		// 返回结果(0失败； 1成功)
		String message = "";
		
		//3.0查询 (把当前部门的id，作为父部门条件查询, 如果当前id有被其他部门引用不能删除)
		List<Dept> childList = deptService.findDeptByParent(getModel().getId());
		
		if (childList == null || childList.size() == 0) {
			//3.1 调用service删除
			deptService.delete(getModel().getId());
			
			// 返回的json数据   {"message":1}
			message = "{\"message\":\"1\"}";
		} else {
			// 不能删除(页面需要给提示)
			message = "{\"message\":\"0\"}";
		}
		
		// 返回json
		try {
			//2.4响应,返回json
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 变更需求： 可以删除多个
	@Action(value="deptAction_delete")
	public void delete(){
		// 删除成功
		int success = 0;
		// 删除失败
		int fail = 0;
		
		// 获取页面提交的删除的部门的ids
		String deptIds = getModel().getId();
		// 分隔字符串
		String[] ids = deptIds.split(",");
		// 判断
		if (ids != null && ids.length>0) {
			for (String id : ids) {
				// 根据当前要删除的部门id，查询子部门
				List<Dept> childList = deptService.findDeptByParent(id);
				if (childList == null || childList.size() == 0) {
					// 可以删除
					deptService.delete(id);
					// 删除成功，success
					success++;
				} else {
					// 删除失败
					fail++;
				}
			}
		}
		
		
		// 返回结果(0失败； 1成功)  {"success":0,"fail":0}
		String message = "{\"success\":"+success+",\"fail\":"+fail+"}";
		
		// 返回json
		try {
			//2.4响应,返回json
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}














