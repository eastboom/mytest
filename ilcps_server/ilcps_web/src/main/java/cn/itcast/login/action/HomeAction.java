package cn.itcast.login.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({
	@Result(name="title",location="/WEB-INF/pages/home/title.jsp"),
	@Result(name="toleft",location="/WEB-INF/pages/${moduleName}/left.jsp"),
	@Result(name="tomain",location="/WEB-INF/pages/${moduleName}/main.jsp")
})
public class HomeAction extends ActionSupport{
	
	// 封装请求参数
	private String moduleName;
	public String getModuleName() {//struts.xml 取属性值。  ${moduleName}
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	//1. title.jsp 导航页面
	@Action("homeAction_title")
	public String title(){
		return "title";
	}
	
	//2. left.jsp
	@Action("homeAction_toleft")
	public String toleft(){
		return "toleft";
	}
	
	//3. main.jsp 
	@Action("homeAction_tomain")
	public String tomain(){
		return "tomain";
	}
}











