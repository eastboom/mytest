package cn.itcast.client.action;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.client.domain.CompanyClient;
import cn.itcast.client.domain.UserClient;
import cn.itcast.client.domain.UserinfoClient;
import cn.itcast.client.service.CompanyClientService;
import cn.itcast.client.service.UserClientService;
import cn.itcast.client.service.UserinfoClientService;
import cn.itcast.common.action.BaseAction;
import cn.itcast.utils.ImageUtil;

@Namespace("/")
public class UserAction extends BaseAction<UserClient>{

	
	/**
	 * 1. 图片验证码
	 */
	@Action("userAction_genActiveCode")
	public void genActiveCode(){
		//1.1 生成四位随机数, 
		String randomCode = ImageUtil.getRundomStr();
		
		//1.2 保存到session中
		ActionContext.getContext().getSession().put("randomCode", randomCode);
		
		try {
			//1.2生成图片验证码，输出到浏览器
			ImageUtil.getImage(randomCode, ServletActionContext.getResponse().getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2. 发送手机短信验证码（这里发送消息到ActiveMQ容器，由消息处理系统实现短信发送）
	 */
	// 注入JmsTemplate对象
	@Resource
	private JmsTemplate jmsTemplate;
	
	@Action("userAction_sendVerCode")
	public void sendVerCode(){
		// 发送消息
		jmsTemplate.send("phone",new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				// 创建消息
				MapMessage mapMsg = session.createMapMessage();
				mapMsg.setString("telephone", getModel().getTelephone());
				// 返回消息
				return mapMsg;
			}
		});
	}
	
	/**
	 * 3. 点击注册
	 */
	@Resource
	private UserClientService userClientService;
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	// 属性驱动封装请求数据
	// 短信验证码
	private String vercode;
	public void setVercode(String vercode) {
		this.vercode = vercode;
	}
	// 手机验证码
	private String phoneVercode;
	public void setPhoneVercode(String phoneVercode) {
		this.phoneVercode = phoneVercode;
	}
	
	@Action("userAction_register")
	public void register(){
		// 返回的结果  0图片验证码错误；  1手机验证码错误；  2注册成功
		String result = "2";
		// 获取redis中存储的服务端发送到手机上的随机6位验证码
		String serverPhoneCode = redisTemplate.opsForValue().get(getModel().getTelephone());
		
		//1. 校验：图片验证码  （session）
		//1.1 从session获取验证码
		String serverImgCode = (String) ActionContext.getContext().getSession().get("randomCode");
		//1.2判断
		if (!serverImgCode.equals(vercode)) {
			result = "0";
		}
		//2. 校验： 手机短信验证码 （redis）
		else if (!serverPhoneCode.equals(phoneVercode)) {
			result = "1";
		} else {
			//设置用户的状态为0
			getModel().setState(0);
			//3. 调用service保存
			userClientService.saveOrUpdate(getModel());
			
			ServletActionContext.getRequest().getSession().setAttribute("user", getModel());
			
			//4. 注册成功，发送邮件提醒(发送消息)
			jmsTemplate.send("email", new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					MapMessage msg = session.createMapMessage();
					msg.setString("email", getModel().getEmail());
					return msg;
				}
			});
		}
		
		//5. 返回处理结果
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//激活码
	private String code;
	
	public void setCode(String code) {
		this.code = code; //
	}
	
	public String getCode() {
		return code;
	}
	
	/**
	 * 通过邮件的email和uuid查询设置激活用户的状态
	 * @return
	 */
	@Action(value="userAction_activeUser",results= {
			@Result(name="index",location="/index.html")
	})
	public String activeUser () {
		//获取对象
		UserClient userClient = getModel();
		//获取对象中的email
		String email = userClient.getEmail();
		//获取在redisTemplate中的以email为key的uuid
		String uuid = redisTemplate.opsForValue().get(email);
		//通过email查询数据库中的用户
		UserClient byEmail = userClientService.findByEmail(email);

		//判断uuid的验证码与邮件中的验证码是否相同
		if(uuid.equals(code)) {
			//设置用户状态
			byEmail.setState(1);
			//更新数据库中的用户状态
			userClientService.saveOrUpdate(byEmail);
		}
		
		return "index";
	}
	
	private UserinfoClient user;
	
	private CompanyClient company;
	
	@Resource
	private UserinfoClientService userinfoClientService;
	@Resource
	private CompanyClientService companyClientService;
	
	/**
	 * 提交资料
	 * 用户的信息和用户的公司信息
	 * @return
	 */
	@Action(value="userAction_userAndcompony",results= {
			@Result(name="userAndcompony",location="/process-step3.html")
	})
	public String userAction_userAndcompony() {
		//获取对话域中的用户信息
		UserClient userClient = (UserClient) ServletActionContext.getRequest().getSession().getAttribute("user");
		//设置用户的名字和密码
		userClient.setUserName(getModel().getUserName());
		userClient.setPassword(getModel().getPassword());
		
		//更新用户的信息
		userClientService.saveOrUpdate(userClient);
		//设置用户的信息id
		user.setId(userClient.getId());
		userinfoClientService.saveOrUpdate(user);
		//保存公司信息
		company.setId(getModel().getId());
		companyClientService.saveOrUpdate(company);
		
		return "userAndcompony";
	}

	public UserinfoClient getUser() {
		return user;
	}

	public void setUser(UserinfoClient user) {
		this.user = user;
	}

	public CompanyClient getCompony() {
		return company;
	}

	public void setCompony(CompanyClient compony) {
		this.company = compony;
	}
	
	
	
}







