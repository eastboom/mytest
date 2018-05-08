package cn.itcast.login.action;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.domain.User;
import cn.itcast.utils.SysConstant;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({
	@Result(name="login",location="/login.jsp")
})
public class LoginAction extends ActionSupport{
	
	// 封装用户名密码
	private String username;
	private String password;
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	// 登陆
	@Action(value="loginAction_login",
			results={@Result(name="success",type="dispatcher",location="/WEB-INF/pages/home/fmain.jsp")})
	public String login(){
		//1. 判断  (http://localhost:8080/ilcps_web/index.jsp--->来到loginAction_login)
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			return "login";
		}
		
		//2. 认证交给shiro的realm
		//2.1 创建subject对象
		Subject subject = SecurityUtils.getSubject();
		
		//2.2创建token，封装用户输入的账号密码（传给realm）
		AuthenticationToken token = new UsernamePasswordToken(username, password);
		
		try {
			//2.3调用login()方法，会去到realm做认证 
			subject.login(token);
			
			
			//3. 认证成功，保存数据到session
			// 获取用户身份信息
			User user = (User) subject.getPrincipal();
			// 保存到session
			ActionContext.getContext().getSession().put(SysConstant.CURRENT_USER_INFO, user);
		} catch (Exception e) {
			// 保存异常信息
			ActionContext.getContext().put("errorInfo", "用户名或密码错误，登陆认证失败！" + e.getMessage());
			e.printStackTrace();
			return "login";
		}
		
		
		// 现在只有跳转到主页就可以(fmain.jsp)
		return SUCCESS;
	}
	
	// 退出
	@Action("loginAction_logout")
	public String logout(){
		// 获取session （false 表示只获取session，绝不会创建session）
		HttpSession session = ServletActionContext.getRequest().getSession(false);
		// 判断
		if (session != null) {
			// 先清空session数据
			session.removeAttribute(SysConstant.CURRENT_USER_INFO);
			// 销毁session
			session.invalidate();
		}
		
		return "login";
	}
}








