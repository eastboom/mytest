package cn.itcast.sysadmin.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cn.itcast.common.action.BaseAction;
import cn.itcast.domain.Module;
import cn.itcast.service.ModuleService;

@Namespace("/sysadmin")
@Results({
	@Result(name="alist",type="redirectAction",location="moduleAction_list")
})
public class ModuleAction extends BaseAction<Module>{
	private static final long serialVersionUID = 1L;
	
	
	// 注入service
	@Resource
	private ModuleService moduleService;
	
	
	/**
	 * 1. 进入jModuleList.jsp页面
	 */
	@Action(value="moduleAction_list",
			results={@Result(name="list",location="/WEB-INF/pages/sysadmin/module/jModuleList.jsp")})
	public String list(){
		return "list";
	}
	
	/**
	 * 2. jModuleList.jsp datagrid发送的异步请求，返回json格式数据
	 */
	@Action(value="moduleAction_listResult")
	public void listResult(){
		
		//2.1 获取请求数据（分页参数）
		Pageable pageable = new PageRequest(getPage()-1, getRows());
		
		//2.2 调用service,
		Page<Module> pageData = moduleService.findAll(pageable);
		
		//2.3构造datagrid需要的json格式数据
		Map<String,Object> results = new HashMap<>();
		results.put("total", pageData.getTotalElements());
		results.put("rows", pageData.getContent());
		
		parseObjToJson(results);
	}
	
	/**
	 * 3. 进入添加页面
	 */
	@Action(value="moduleAction_tocreate",
			results={@Result(name="tocreate",location="/WEB-INF/pages/sysadmin/module/jModuleCreate.jsp")})
	public String tocreate(){
		return "tocreate";
	}
	
	/**
	 * 5. 添加保存
	 */
	@Action(value="moduleAction_insert")
	public String insert(){
		//3.1 调用service新增保存
		moduleService.saveOrUpdate(getModel());
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	/**
	 * 6. 进入修改页面
	 */
	@Action(value="moduleAction_toupdate",
			results={@Result(name="toupdate",location="/WEB-INF/pages/sysadmin/module/jModuleUpdate.jsp")})
	public String toupdate(){
		//6.1 根据id查询
		Module Module =  moduleService.findOne(getModel().getId());
		//6.2放入值栈
		push(Module); //${id}
		return "toupdate";
	}
	
	
	/**
	 * 7. 修改保存
	 */
	@Action(value="moduleAction_update")
	public String update(){
		//3.1 先根据id查询
		Module Module = moduleService.findOne(getModel().getId());
		Module.setName(getModel().getName());
		Module.setRemark(getModel().getRemark());
		
		// 3.3 保存持久化对象
		moduleService.saveOrUpdate(Module);
		//3.2保存成功，跳转到列表
		return "alist";
	}
	
	
	// 8. 删除
	@Action(value="moduleAction_delete")
	public String delete(){
		moduleService.delete(getModel().getId());
		return "alist";
	}
}














