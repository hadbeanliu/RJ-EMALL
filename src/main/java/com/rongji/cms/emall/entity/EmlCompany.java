package com.rongji.cms.emall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rongji.cms.emall.support.TablePrefix;
import com.rongji.rjskeleton.support.Comment;
import com.rongji.rjskeleton.support.id.Sid;

@Entity
@Table(name = TablePrefix.NAME + "COMPANY")
@Comment("企业表")
public class EmlCompany implements Sid {

	@Id
	@Column(name = "COMPANY_ID")
	private String id;
	
	@Column(name = "USER_ID")
	@Comment("用户ID")
	private String userId;
	
	@Comment("公司名称")
	private String companyName;

	@Comment("企业性质")
	private String companyProperty;
	
	@Comment("公司成立时间")
	private String companyCreateTime;
	
	@Comment("公司注册地址")
	private String companyRegisterAddress;
	
	@Comment("公司联系地址")
	private String companyContactAddress;
	
	@Comment("邮政编号")
	private String companyZipcode;

	@Comment("注册资金")
	private String companyRegisterFund;
	
	@Comment("工商营业执照注册号")
	private String licenceId;

	@Comment("营业执照正面")
	private String licenceFrontcard;

	@Comment("营业执照背面")
	private String licenceBackcard;
	
	@Comment("营业执照有效期（前）")
	private String licenceStartPeriod;
	
	@Comment("营业执照有效期（后）")
	private String licenceEndPeriod;
	
	@Comment("营业执照经营范围")
	private String licenceField;

	@Comment("法定代表人")
	private String legalPerson;

	@Comment("法人联系电话")
	private String legalPhone;

	@Comment("服务认证内容")
	private String authenticationService;
	
	@Comment("企业认证")
	private String isCompanyauthentication;
	
	@Comment("服务认证")
	private String isServiceauthentication;

	@Comment("状态")
	private String status;
	
	@Comment("开店须知")
	private String informance;

	public String getInformance() {
		return informance;
	}

	public void setInformance(String informance) {
		this.informance = informance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}

	public String getCompanyCreateTime() {
		return companyCreateTime;
	}

	public void setCompanyCreateTime(String companyCreateTime) {
		this.companyCreateTime = companyCreateTime;
	}

	public String getCompanyRegisterAddress() {
		return companyRegisterAddress;
	}

	public void setCompanyRegisterAddress(String companyRegisterAddress) {
		this.companyRegisterAddress = companyRegisterAddress;
	}

	public String getCompanyContactAddress() {
		return companyContactAddress;
	}

	public void setCompanyContactAddress(String companyContactAddress) {
		this.companyContactAddress = companyContactAddress;
	}

	public String getCompanyZipcode() {
		return companyZipcode;
	}

	public void setCompanyZipcode(String companyZipcode) {
		this.companyZipcode = companyZipcode;
	}

	public String getCompanyRegisterFund() {
		return companyRegisterFund;
	}

	public void setCompanyRegisterFund(String companyRegisterFund) {
		this.companyRegisterFund = companyRegisterFund;
	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public String getLicenceFrontcard() {
		return licenceFrontcard;
	}

	public void setLicenceFrontcard(String licenceFrontcard) {
		this.licenceFrontcard = licenceFrontcard;
	}

	public String getLicenceBackcard() {
		return licenceBackcard;
	}

	public void setLicenceBackcard(String licenceBackcard) {
		this.licenceBackcard = licenceBackcard;
	}

	public String getLicenceField() {
		return licenceField;
	}

	public void setLicenceField(String licenceField) {
		this.licenceField = licenceField;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalPhone() {
		return legalPhone;
	}

	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
	}

	public String getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(String authenticationService) {
		this.authenticationService = authenticationService;
	}

	public String getIsCompanyauthentication() {
		return isCompanyauthentication;
	}

	public void setIsCompanyauthentication(String isCompanyauthentication) {
		this.isCompanyauthentication = isCompanyauthentication;
	}

	public String getIsServiceauthentication() {
		return isServiceauthentication;
	}

	public void setIsServiceauthentication(String isServiceauthentication) {
		this.isServiceauthentication = isServiceauthentication;
	}

	public String getLicenceStartPeriod() {
		return licenceStartPeriod;
	}

	public void setLicenceStartPeriod(String licenceStartPeriod) {
		this.licenceStartPeriod = licenceStartPeriod;
	}

	public String getLicenceEndPeriod() {
		return licenceEndPeriod;
	}

	public void setLicenceEndPeriod(String licenceEndPeriod) {
		this.licenceEndPeriod = licenceEndPeriod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		EmlCompany other = (EmlCompany) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
