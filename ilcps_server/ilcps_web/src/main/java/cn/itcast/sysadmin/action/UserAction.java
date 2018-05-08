package cn.itcast.sysadmin.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Role;
import cn.itcast.domain.User;
import cn.itcast.service.RoleService;
import cn.itcast.service.UserService;

@Namespace("/sysadmin")
@Results({
	@Result(name="alist",type="redirectAction",location="userAction_list")
})
public class UserAction extends BaseAction<User>{
	private static final long serialVersionUID = 1L;
	
	
	// 注入service
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	
	/**
	 * 1. 进入jUserList.jsp页面
	 */
	@Action(value="userAction_list",
			results={@Result(name="list",location="/WEB-INF/pages/sysadmin/user/jUserList.jsp")})
	public String list(){
		return "list";
	}
	
	/**
	 * 2. jUserList.jsp datagrid发送的异步请求，返回json格式数据
	 */
	@Action(value="userAction_listResult")
	public void listResult(){
		
		//2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		
		//2.2 调用service,
		Page<User> pageData = userService.findAll(pageable);
		
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
	@Action(value="userAction_tocreate",
			results={@Result(name="tocreate",location="/WEB-INF/pages/sysadmin/user/jUserCreate.jsp")})
	public String tocreate(){
		return "tocreate";
	}
	
	/**
	 * 5. 添加保存
	 */
	@Action(value="userAction_insert")
	public String insert(){
		//3.1 调用service新增保存
		userService.saveOrUpdate(getModel());
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	/**
	 * 6. 进入修改页面
	 */
	@Action(value="userAction_toupdate",
			results={@Result(name="toupdate",location="/WEB-INF/pages/sysadmin/user/jUserUpdate.jsp")})
	public String toupdate(){
		//6.1 根据id查询
		User User =  userService.findOne(getModel().getId());
		//6.2放入值栈
		push(User); //${id}
		return "toupdate";
	}
	
	
	/**
	 * 7. 修改保存
	 */
	@Action(value="userAction_update")
	public String update(){
		//3.1 先根据id查询
		User user = userService.findOne(getModel().getId());
		//3.2修改user对象属性，为用户输入的数据
		user.setDept(getModel().getDept());
		user.setUserName(getModel().getUserName());
		user.setState(getModel().getState());
		
		// 3.3 保存持久化对象
		userService.saveOrUpdate(user);
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	// 8. 删除
	@Action(value="userAction_delete")
	public String delete(){
		userService.delete(getModel().getId());
		return "alist";
	}
	
	// 9. 进入用户角色页面
	@Action(value="userAction_userRole",
			results={@Result(name="userRole",location="/WEB-INF/pages/sysadmin/user/jUserRole.jsp")})
	public String userRole(){
		//9.1 根据用户id查询用户，放入值栈
		User user = userService.findOne(getModel().getId());
		push(user);
		
		//9.2获取用户的角色, 放入值栈   moduleStr = "船运经理,合同专员,财务经理,"
		Set<Role> rolesSet = user.getRoles();
		//9.2.1 定义字符串，保存用户的角色
		String moduleStr = "";
		//9.2.2遍历用户角色集合
		if (rolesSet != null && rolesSet.size() > 0) {
			for (Role role : rolesSet) {
				moduleStr += role.getName() + ",";
			}
		}
		//9.2.3角色权限字符串，放入值栈
		set("moduleStr", moduleStr);
		
		//9.3查询所有角色，放入值栈         [船运经理,合同专员,财务经理,系统管理员,,,,] 
		// -->页面  moduleStr.contains(船运经理)
		List<Role> roleList = roleService.findAll();
		set("roleList", roleList);
		
		return "userRole";
	}
	
	// 10. 保存用户角色
	
	// 封装用户选择的checkbox的角色的id
	// private String roleIds;  //"11, 22, 33"
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	@Action(value="userAction_saveUserRole")
	public String saveUserRole(){
		//10.1 根据用户id查询
		User user = userService.findOne(getModel().getId());
		
		//10.2创建角色集合，封装角色集合, 设置到用户对象中
		Set<Role> roleSet = new HashSet<>();
		// 遍历角色id，封装集合
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				// 根据id查询
				Role role = roleService.findOne(roleId);
				// 添加到集合
				roleSet.add(role);
			}
		}
		// 角色集合设置到用户对象中
		user.setRoles(roleSet);
		
		//10.3保存用户
		userService.saveOrUpdate(user);
		return "alist";
	}
}














