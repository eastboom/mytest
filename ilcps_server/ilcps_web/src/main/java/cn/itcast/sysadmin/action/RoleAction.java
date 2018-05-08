package cn.itcast.sysadmin.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Module;
import cn.itcast.domain.Role;
import cn.itcast.service.ModuleService;
import cn.itcast.service.RoleService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Namespace("/sysadmin")
@Results({
	@Result(name="alist",type="redirectAction",location="roleAction_list")
})
public class RoleAction extends BaseAction<Role>{
	private static final long serialVersionUID = 1L;
	
	
	// 注入service
	@Resource
	private RoleService roleService;
	@Resource
	private ModuleService moduleService;
	
	
	/**
	 * 1. 进入jRoleList.jsp页面
	 */
	@Action(value="roleAction_list",
			results={@Result(name="list",location="/WEB-INF/pages/sysadmin/role/jRoleList.jsp")})
	public String list(){
		return "list";
	}
	
	/**
	 * 2. jRoleList.jsp datagrid发送的异步请求，返回json格式数据
	 */
	@Action(value="roleAction_listResult")
	public void listResult(){
		
		//2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		
		//2.2 调用service,
		Page<Role> pageData = roleService.findAll(pageable);
		
		//2.3构造datagrid需要的json格式数据
		Map<String,Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", pageData.getContent());
		
		parseObjToJson(results);
	}
	
	/**
	 * 3. 进入添加页面
	 */
	@Action(value="roleAction_tocreate",
			results={@Result(name="tocreate",location="/WEB-INF/pages/sysadmin/role/jRoleCreate.jsp")})
	public String tocreate(){
		return "tocreate";
	}
	
	/**
	 * 5. 添加保存
	 */
	@Action(value="roleAction_insert")
	public String insert(){
		//3.1 调用service新增保存
		roleService.saveOrUpdate(getModel());
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	/**
	 * 6. 进入修改页面
	 */
	@Action(value="roleAction_toupdate",
			results={@Result(name="toupdate",location="/WEB-INF/pages/sysadmin/role/jRoleUpdate.jsp")})
	public String toupdate(){
		//6.1 根据id查询
		Role Role =  roleService.findOne(getModel().getId());
		//6.2放入值栈
		push(Role); //${id}
		return "toupdate";
	}
	
	
	/**
	 * 7. 修改保存
	 */
	@Action(value="roleAction_update")
	public String update(){
		//3.1 先根据id查询
		Role role = roleService.findOne(getModel().getId());
		role.setName(getModel().getName());
		role.setRemark(getModel().getRemark());
		
		// 3.3 保存持久化对象
		roleService.saveOrUpdate(role);
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	// 8. 删除
	@Action(value="roleAction_delete")
	public String delete(){
		roleService.delete(getModel().getId());
		return "alist";
	}
	
	// 9. 进入角色权限页面,
	@Action(value="roleAction_roleModule",
			results={@Result(name="roleModule",location="/WEB-INF/pages/sysadmin/role/jRoleModule.jsp")})
	public String roleModule(){
		//6.1 根据id查询
		Role Role =  roleService.findOne(getModel().getId());
		//6.2放入值栈
		push(Role); //${id}
		return "roleModule";
	}
	
	
	// 10. jRoleModule.jsp 发送异步请求，返回json格式数据
	/*
	 * 扩展需求： 权限树json字符串需要放入redis缓存、或从缓存获取
	 */
	@Resource
	private JedisPool jedisPool;
	
	@Action(value="roleAction_getModulesJson")
	public void getModulesJson(){
		
		//第一步：先获取Jedis对象
		Jedis jedis = jedisPool.getResource();
		//第二步： 先从缓存中获取数据 （角色的id作为key）
		String moduleStr = jedis.get(getModel().getId());
		// 判断
		if (StringUtils.isBlank(moduleStr)) {
			/** 缓存中没有数据，需要查询数据库 */
		
			//10.1 根据角色id查询
			Role role = roleService.findOne(getModel().getId());
			
			//10.2当前角色对用的权限
			Set<Module> modules = role.getModules();
			
			//10.3查询所有的权限
			List<Module> moduleList = moduleService.findAll();
			
			/*
			 * 10.4 构造json格式、返回
			 * ztree需要的json格式是：[{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},,,,]
			 * 封装json格式：List<Map<String,Object>> results;
			 */
			//10.4.1 封装json格式的对象
			List<Map<String,Object>> results = new ArrayList<>();
			//10.4.2 填充list集合
			for(int i=0; i<moduleList.size(); i++) {
				//10.4.3每一个权限对象
				Module module = moduleList.get(i);
				//10.4.4封装map
				Map<String,Object> moduleMap = new HashMap<>();
				moduleMap.put("id", module.getId());
				moduleMap.put("pId", module.getParentId());
				moduleMap.put("name", module.getName());
				//10.4.5判断：当前角色的权限是否有包含遍历的每一个module对用的权限，如果有则设置选中
				if (modules.contains(module)) {
					moduleMap.put("checked", true);
				} else {
					moduleMap.put("checked", false);
				}
				//10.4.6添加到集合
				results.add(moduleMap);
			}
			// 对象转换为json，获取json字符串，放入redis缓存
			String jsonString = 
					JSON.toJSONString(results, SerializerFeature.DisableCircularReferenceDetect);
			// 放入缓存
			jedis.set(getModel().getId(), jsonString);
			// 赋值
			moduleStr = jsonString;
			
		} else {
			/** 从缓存中获取json字符串 */
			System.out.println("---------------->从redis缓存获取数据");
		}
		
		try {
			//2.4响应,返回json
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(moduleStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭
			jedis.close();
		}
	}
	
	//11. 保存角色权限
	
	// 封装权限ids
	private String moduleIds;
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}
	
	@Action(value="roleAction_saveRoleModule")
	public String saveRoleModule(){
		//11.1 根据角色id查询角色对象
		Role role = roleService.findOne(getModel().getId());
		
		//11.2封装角色权限、设置角色权限
		Set<Module> modules = new HashSet<Module>(0);
		// 判断、分隔字符串
		if (moduleIds != null) {
			// 选中的权限ids数组
			String[] ids = moduleIds.split(",");
			// 遍历
			if (ids != null && ids.length > 0) {
				for (String mId : ids) {
					Module module = moduleService.findOne(mId);
					// 添加到权限集合
					modules.add(module);
				}
			}
		}
		// 设置角色权限
		role.setModules(modules);
		
		//11.3保存角色（关联保存对应的权限集合）
		roleService.saveOrUpdate(role);
		
		/**
		 * 修改角色权限，清空redis缓存
		 */
		Jedis jedis = jedisPool.getResource();
		jedis.del(role.getId());
		jedis.close();
		
		return "alist";
	}
}














