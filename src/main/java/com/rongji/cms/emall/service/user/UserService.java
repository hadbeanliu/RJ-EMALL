package com.rongji.cms.emall.service.user;

import java.util.List;


import com.rongji.cms.emall.entity.EmlUser;
import com.rongji.cms.emall.vo.User;
import com.rongji.rjskeleton.service.CommonService;


public interface UserService extends CommonService<User, EmlUser>{
	/**
	 * 用户本地登录
	 * @param：loginName，password
	 */
	public List<User> getUserLogin(String loginName,String password);
	/**
	 * 根据用户名获取用户信息
	 * @param：loginName
	 */
	public User getUserByLoginName(String loginName);
	/**
	 * 根据用户ID获取用户信息
	 * @param：userId
	 * @throws Exception 
	 */
	public User getUserById(String userId);
	/**
	 * 根据用户ID获取用户权限列表
	 * @param：userId
	 */
	public List<User> findRolesByUserId(String userId);
	/**
	 * 平台买家注册
	 * @param：user用户对象
	 * @return:userId
	 */
	public String addUserToUms(User user);
	/**
	 * 未验证用户角色注册
	 * @param：user用户对象
	 * @return:userId
	 */
	public User addNoActiveUser(User user);
	/**
	 * 用户数据本地保存
	 * @param userId
	 * @return true:添加成功 false：添加失败
	 */
	public void addUserToLocal(User user);
	/**
	 * 用户数据存到redis
	 * @param：user用户对象
	 */
	public void addUserToRedis(User user);
	/**
	 * 判断用户名是否重复
	 * @param：loginName
	 * @return:true表示登录名重复  false 表示登录名不重复
	 */
	public boolean isUserLoginNameRepeat(String loginName);
	/**
	 * 用户登录平台校验
	 * @param loginName
	 * @return userId
	 * @throws Exception
	 */
	public String checkUserPassword(String loginName,String pwd);
	/**
	 * 用户基本信息修改
	 * @param user
	 * @return User
	 */
	public User updateUser(User user);
	/**
	 * 用户登录密码修改
	 * @param userId 用户ID, oldPwd 旧密码, newPwd 新密码
	 * @return 修改结果信息
	 */
	public String updateUserPsd(String userId, String oldPwd, String newPwd);
	/**
	 * 添加卖家角色
	 * @param userId
	 * @return true:添加成功 false：添加失败
	 * @throws Exception
	 */
	public boolean addSellerRole(String userId);
	/**
	 * 是否具有管理员角色
	 * @param userId
	 * @return true:是 false：否
	 * @throws Exception
	 */
	public boolean isAdmin(String userId);
	/**
	 * 是否具有卖家角色
	 * @param userId
	 * @return true:是 false：否
	 * @throws Exception
	 */
	public boolean isSeller(String userId);
	/**
	 * 是否具有买家角色
	 * @param userId
	 * @return true:是 false：否
	 * @throws Exception
	 */
	public boolean isBuyer(String userId);
	/**
	 * 判断个人认证是否通过
	 * @param userId 用户编号
	 * @return
	 */
	public boolean isPassForPerson(String userId);
	/**
	 * 判断专家认证是否通过
	 * @param userId 用户编号
	 * @return
	 */
	public boolean isPassForExpert(String userId);
	/**
	 * 校验用户名、密码是否正确
	 * @param userId 用户编号
	 * @return
	 */
	public boolean checkPwdByloginName(String loginName,String pwd);
	/**
	 * 校验手机号码是否存在
	 * @param mobiletel 手机
	 * @return true存在 false不存在
	 */
	public boolean checkPhone(String mobiletel);
	
}
