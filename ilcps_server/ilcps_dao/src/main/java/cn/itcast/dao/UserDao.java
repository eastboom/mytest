package cn.itcast.dao;

import java.util.List;

import cn.itcast.common.dao.BaseDao;
import cn.itcast.domain.User;

/**
 * 用户模块，dao
 */
public interface UserDao extends BaseDao<User, String>{

	/**
	 * 根据用户名称查询
	 * @param inputName
	 * @return
	 */
	List<User> findByUserName(String inputName);
}












