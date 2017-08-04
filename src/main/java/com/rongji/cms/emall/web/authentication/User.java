package com.rongji.cms.emall.web.authentication;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.rongji.rjskeleton.support.id.Sid;
import com.rongji.rjskeleton.support.valid.regexp.RegexpInfo;

public class User implements Sid {

	private String id;
	
	private String loginName;
	
	private String password;
	
	private String nickName;
	
	private String sex;
	
	private String birthday;
	
	@Pattern(regexp = RegexpInfo.PHONE_MOBILE)
	@NotBlank
	@Max(11)
	private String mobiletel;
	
	@Pattern(regexp = RegexpInfo.EMAIL)
	@NotBlank
	@Max(255)
	private String email;
	
	private String isActiveemail;
	
    private Date registerTime;
    
    private String userType;

	@NotBlank
    private String userCredentials;
	
	private String isDel;
	
	@Pattern(regexp = RegexpInfo.IDCARD)
	@NotBlank
	@Max(18)
	private String cardId;

	@NotBlank
	private String head;

	@NotBlank
	private String frontCard;

	@NotBlank
	private String backCard;
	
	@Length(max=20)
	@NotBlank
	private String realName;
	
	private String interestList;
	


	@NotBlank
	private String field;

	@NotBlank
	@Max(40)
	private String rank;

	@NotBlank
	@Max(255)
	private String work;

	@Max(15)
	@Pattern(regexp = "^\\d{5,10}$")
	private String qq;
	
	private String isPersonauthentication;
	
	private String isExpertauthentication;
	
	private String socialUserId;
	
	@NotBlank
	@Pattern(regexp = RegexpInfo.PHONE_MOBILE)
	private String expertMobile;

	public String getExpertMobile() {
		return expertMobile;
	}
	public void setExpertMobile(String expertMobile) {
		this.expertMobile = expertMobile;
	}
	public String getIsPersonauthentication() {
		return isPersonauthentication;
	}
	public void setIsPersonauthentication(String isPersonauthentication) {
		this.isPersonauthentication = isPersonauthentication;
	}
	public String getIsExpertauthentication() {
		return isExpertauthentication;
	}
	public void setIsExpertauthentication(String isExpertauthentication) {
		this.isExpertauthentication = isExpertauthentication;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}

	public User(){}
	public User(String id) {
		this.id = id;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getFrontCard() {
		return frontCard;
	}

	public void setFrontCard(String frontCard) {
		this.frontCard = frontCard;
	}

	public String getBackCard() {
		return backCard;
	}

	public void setBackCard(String backCard) {
		this.backCard = backCard;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobiletel() {
		return mobiletel;
	}

	public void setMobiletel(String mobiletel) {
		this.mobiletel = mobiletel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsActiveemail() {
		return isActiveemail;
	}

	public void setIsActiveemail(String isActiveemail) {
		this.isActiveemail = isActiveemail;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getUserType() {
		return userType;
	}

	public String getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(String userCredentials) {
		this.userCredentials = userCredentials;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getSocialUserId() {
		return socialUserId;
	}
	public void setSocialUserId(String socialUserId) {
		this.socialUserId = socialUserId;
	}
	public String getInterestList() {
		return interestList;
	}
	public void setInterestList(String interestList) {
		this.interestList = interestList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}

