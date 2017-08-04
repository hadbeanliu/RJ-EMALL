package com.rongji.cms.emall.service.user.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.rongji.cms.emall.dao.user.UserDao;
import com.rongji.cms.emall.entity.EmlUser;
import com.rongji.cms.emall.service.social.SocialUserService;
import com.rongji.cms.emall.service.user.UserService;
import com.rongji.cms.emall.support.EmallException;
import com.rongji.cms.emall.support.EmallStatusCode;
import com.rongji.cms.emall.support.config;
import com.rongji.cms.emall.vo.User;
import com.rongji.dfish.cache.RedisProvider;
import com.rongji.dfish.cache.meta.HashMeta;
import com.rongji.dfish.platform.ums.service.UmsType.PubRole;
import com.rongji.dfish.platform.ums.service.UmsType.PubUser;
import com.rongji.dfish.platform.ums.service.impl.UmsClientJsonImpl;
import com.rongji.dfish.platform.ums.service.impl.UmsxClientJsonImpl;
import com.rongji.rjskeleton.service.CommonServiceImpl;

@Service
public class UserServiceImpl extends CommonServiceImpl<User, EmlUser, UserDao> implements UserService {
	
	public UmsClientJsonImpl ums = config.getUmsCLient();
	
	public UmsxClientJsonImpl umsx = config.getUmsXCLient();
	
	@Autowired
	private SocialUserService socialService;
	
	@Override
	protected User createVo() {
		return new User() ;
	}

	@Override
	protected EmlUser createEntity() {
		return new EmlUser();
	}
	
	@Transactional
	@Override
	public <S extends User> S save(S vo) {
		vo.setRegisterTime(new Date());
		String userId = this.addUserToUms(vo); 
		if(vo.getNickName()==null || "".equals(vo.getNickName())){
			vo.setNickName(vo.getLoginName());
		}
		vo.setId(userId);
		vo.setPassword("");
		return super.save(vo); 
	}
	
	public List<User> getUserLogin(final String loginName,final String password){
		return convertToVos(getDao().getUserLogin(loginName, password));
	}
	
   
	public User getUserByLoginName(String loginName) {
		return convertToVo(getDao().getUserByLoginName(loginName));
	}
	
	@Transactional
	public User getUserById(String userId) {
		try {
			EmlUser entity = getDao().findOne(userId);
			PubUser user = ums.getUserById(userId);
			User vo = new User();
			if(user!=null&&!"".equals(user)){
				if(!this.isAdmin(userId) && !this.isSeller(userId) && !this.isBuyer(userId)){
					umsx.addRoleMember("04000721", userId, null);
					umsx.addRoleMember("04000722", userId, null);
				}
				if(entity==null){
					vo.setId(userId);
					vo.setLoginName(user.getLoginName());
					vo.setMobiletel(user.getMobilePhone());
					vo.setEmail(user.getEmail());
					vo.setHead(user.getPhotoUrlLarge());
					vo.setNickName(user.getShortName());
					vo.setSex(user.getUserGender());
					vo.setRegisterTime(new Date());
					vo = super.save(vo);
					this.addUserToRedis(vo);
				}else{
					vo = convertToVo(entity);
				}
				return vo;
			}else{
				throw new EmallException(EmallStatusCode.ERROR_DATA_EMPTY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}

	public List<User> findRolesByUserId(String userId) {
		try {
			List<PubRole> role = ums.findRolesByUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String addUserToUms(User vo) {
		String userId = null;
		try {
			PubUser user = new PubUser();
			user.setLoginName(vo.getLoginName());
			user.setUserName(vo.getLoginName());
			user.setMobilePhone(vo.getMobiletel());
			user.setCreateTime(vo.getRegisterTime());
			user.setUserStatus("1");
			user.setDeptId("04000721");    //平台电商部门ID
			userId = umsx.addUser(user);         //平台添加新用户
			umsx.addRoleMember("04000722", userId,null); //平台添加用户买家角色
			if(vo.getPassword()!=null&&!"".equals(vo.getPassword())){
				ums.updateUserPassword(userId, "abc123", vo.getPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
	
	@Transactional
	public User addNoActiveUser(User vo) {
		String userId = null;
		vo.setRegisterTime(new Date());
		try {
			PubUser user = new PubUser();
			user.setLoginName(vo.getLoginName());
			user.setUserName(vo.getLoginName());
			user.setCreateTime(vo.getRegisterTime());
			user.setUserStatus("1");
			user.setDeptId("04000721");    //平台未验证角色ID
			userId = umsx.addUser(user); 
			umsx.addRoleMember("04000747", userId,null); //平台未验证角色ID
			vo.setId(userId);
			vo = super.save(vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	public void addUserToRedis(User vo) {
		HashMeta userMap = new HashMeta("user.userId:"+vo.getId()); //Redis添加用户数据
		if(vo.getNickName()==null || "".equals(vo.getNickName())){
			vo.setNickName(vo.getLoginName());
		}
		Gson gson = new Gson();
		vo.setPassword("");
		userMap.set("extJson", gson.toJson(vo));
		userMap.set("system", "电商");
		userMap.set("nickName", vo.getNickName());
		userMap.set("loginName", vo.getLoginName());
		userMap.set("userId", vo.getId());
	}

	public boolean isUserLoginNameRepeat(String loginName) {
		try {
		    String userId = null;
			return ums.isUserLoginNameRepeat(userId, loginName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}//判断是否重名 
		return false;
	}

	public String checkUserPassword(String loginName, String pwd) {
		PubUser user = null;
		try {
			user = ums.checkUserPassword(loginName,pwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user.getUserId();
	}

	@Override
	@Transactional
	public User updateUser(User user) {
		EmlUser u = getDao().findOne(user.getId());
		if(user.getNickName()!=null){
			u.setNickName(user.getNickName());
		}
		if(user.getSex()!=null){
			u.setSex(user.getSex());
		}
		if(user.getBirthday()!=null){
			u.setBirthday(user.getBirthday());
		}
		if(user.getQq()!=null){
			u.setQq(user.getQq());
		}
		if(user.getEmail()!=null){
			u.setEmail(user.getEmail());
		}
		if(user.getIsActiveemail()!=null){
			u.setIsActiveemail(user.getIsActiveemail());
		}
		if(user.getMobiletel()!=null){
			u.setMobiletel(user.getMobiletel());
		}
		if(user.getField()!=null){
			u.setField(user.getField());
		}
		if(user.getRank()!=null){
			u.setRank(user.getRank());
		}
		if(user.getWork()!=null){
			u.setWork(user.getWork());
		}
		if(user.getUserCredentials()!=null){
			u.setUserCredentials(user.getUserCredentials());
		}
		if(user.getRealName()!=null){
			u.setRealName(user.getRealName());
		}
		if(user.getBackCard()!=null){
			u.setBackCard(user.getBackCard());
		}
		if(user.getFrontCard()!=null){
			u.setFrontCard(user.getFrontCard());
		}
		if(user.getCardId()!=null){
			u.setCardId(user.getCardId());
		}
		if(user.getHead()!=null){
			u.setHead(user.getHead());
		}
		if(user.getInterestList()!=null){
			u.setInterestList(user.getInterestList());
		}
		if(user.getExpertMobile()!=null){
			u.setExpertMobile(user.getExpertMobile());
		}
		user = convertToVo(getDao().save(u));
		HashMeta userMap = new HashMeta("user.userId:"+user.getId()); //Redis添加用户数据
		Gson gson = new Gson();
		user.setPassword("");
		userMap.set("extJson", gson.toJson(user));
		userMap.set("system", "电商");
		userMap.set("nickName", user.getLoginName());
		userMap.set("loginName", user.getLoginName());
		userMap.set("userId", user.getId());
		return user;
	}

	public String updateUserPsd(String userId, String oldPwd, String newPwd) {
		try {
			if(ums.updateUserPassword(userId, oldPwd, newPwd)){
				return "密码修改成功";
			}else{
				return "密码修改失败";
			}
			 
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public boolean addSellerRole(String userId){
		try {
			if(umsx.addRoleMember("04000723", userId,null)){ //平台卖家角色ID
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

	@Override
	public void addUserToLocal(User user) {
		super.save(user);
	}

	public boolean isAdmin(String userId) {
		try {
			return ums.isRoleMember("04000724", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isSeller(String userId) {
		try {
			return ums.isRoleMember("04000723", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isBuyer(String userId) {
		try {
			return ums.isRoleMember("04000722", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean isPassForPerson(String userId) {
		if(userId != null && getOne(userId)!= null){
			if(getOne(userId).getIsPersonauthentication() == "1") return true;
		}
		return false;
	}

	@Override
	public boolean isPassForExpert(String userId) {
		if(userId != null && getOne(userId)!= null){
			if(getOne(userId).getIsExpertauthentication() == "1") return true;
		}
		return false;
	}

	public boolean checkPwdByloginName(String loginName, String pwd) {
		try {
			PubUser user = ums.checkUserPassword(loginName, pwd);
			if(user.getUserId()!=null&&!"".equals(user.getUserId())){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkPhone(String mobiletel) {
		int i = getDao().getPhone(mobiletel).size();
		if(getDao().getPhone(mobiletel).size()>0){
			return true;
		}else{
			return false;
		}
		
	}

	
}
