package cn.itcast.service;

import java.util.List;

import cn.itcast.common.service.BaseService;
import cn.itcast.domain.User;

/**
 * 用户模块业务逻辑接口
 */
public interface UserService extends BaseService<User, String> {

	/**
	 * 根据用户名称查询
	 * @param inputName
	 * @return
	 */
	List<User> findByUsername(String inputName);

	/**
	 * 根据用户id查询用户权限
	 * @param userId
	 * @return
	 */
	List<String> findPermissionsById(String userId);
	
}













