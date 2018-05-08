package cn.itcast.realm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;

public class CustomRealm extends AuthorizingRealm{
	
	// 注入userService
	@Resource
	private UserService userService;
	
	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1. 获取用户输入的账号
		String inputName = (String) token.getPrincipal();
		// 用户输入的密码
		//Object credentials = token.getCredentials();
		
		//2. 检查账号是否存在
		List<User> list = userService.findByUsername(inputName);
		// 判断
		if (list == null || list.size() <= 0) {
			// 认证方法返回NULL，表示账号错误。 UnknownAccountException
			return null;
		}
		
		//3. 获取数据库正确的密码
		User user = list.get(0);
		String pwd = user.getPassword();
		
		//4. 返回结果
		// 参数1： 用户身份信息 ,通过这里可以获取：SecurityUtils.getSubject().getPrincipal()
		// 参数2： 数据库正确的密码
		// 参数3： realm名称，可以随意定义。只是shiro内部管理多个realm时候区分每个realm.
		SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user,pwd,this.getName());
		return sai;
	}

	// 授权(返回：用户的所有权限)
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/** 权限写死，测试 */
		/* 获取用户
		User user = (User) principals.getPrimaryPrincipal();
		//返回权限
		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		sai.addStringPermission("部门列表");
		sai.addStringPermission("添加部门");
		sai.addStringPermission("修改部门"); */
		
		
		
		/**
		 * 查询数据库返回用户的权限
		 * 1. 获取用户id
		 * 2. 查询用户权限
		 * 3. 返回用户权限
		 */
		// 1. 获取用户id
		String userId = ((User) principals.getPrimaryPrincipal()).getId();
		// 2. 查询用户权限
		List<String> permissionsList = userService.findPermissionsById(userId);
		
		// 3. 返回用户权限
		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		sai.addStringPermissions(permissionsList);
		return sai;
	}


}
