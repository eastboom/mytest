package cn.itcast.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.service.impl.BaseServiceImpl;
import cn.itcast.dao.UserDao;
import cn.itcast.domain.Module;
import cn.itcast.domain.Role;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
	
	private UserDao userDao;
	
	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
		super.setBaseDao(userDao);
	}
	
	@Override
	public void saveOrUpdate(User t) {
		// 判断
		if (StringUtils.isBlank(t.getId())) {
			// 新增
			// a. 生成uuid
			String uuid = UUID.randomUUID().toString();
			// b. 作为用户、用户扩展信息主键
			t.setId(uuid);
			t.getUserinfo().setId(uuid);
			// (新增或修改)保存
			userDao.save(t);
			
			// 发邮件 (开一个独立的线程。)
			
		} else {
			// (新增或修改)保存
			userDao.save(t);
		}
		
		
	}
	
	@Override
	public List<User> findByUsername(String inputName) {
		return userDao.findByUserName(inputName);
	}
	
	@Override
	public List<String> findPermissionsById(String userId) {
		List<String> results = new ArrayList<>();
		
		//1. 根据用户id查询
		User user = userDao.findOne(userId);
		
		//2. 获取角色、遍历角色
		Set<Role> roles = user.getRoles();
		if (roles != null && roles.size() > 0) {
			for (Role role : roles) {
				//3. 获取角色权限、遍历权限，累加用户权限
				Set<Module> modules = role.getModules();
				if (modules != null && modules.size() > 0) {
					for (Module module : modules) {
						if (StringUtils.isNotBlank(module.getCpermission())) {
							// 累加用户角色的所有权限
							results.add(module.getCpermission());
						}
					}
				}
			}
		}
		return results;
	}
}





















