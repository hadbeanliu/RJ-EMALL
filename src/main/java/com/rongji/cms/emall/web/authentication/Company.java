package com.rongji.cms.emall.web.authentication;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.rongji.rjskeleton.support.id.Sid;
import com.rongji.rjskeleton.support.valid.regexp.RegexpInfo;

public class Company implements Sid {

	private String id;
	
	private String userId;
	
	@NotBlank
	@Max(100)
	private String companyName;

	@NotBlank
	@Max(1)
	private String companyProperty;
	
	@NotBlank
	@Max(20)
	private String companyCreateTime;
	
	@NotBlank
	private String companyRegisterAddress;
	
	@NotBlank
	private String companyContactAddress;
	
	@Pattern(regexp = RegexpInfo.ZIPCODE)
	@Max(6)
	private String companyZipcode;
	
	@Pattern(regexp = RegexpInfo.NUMBER)
	@NotBlank
	@Max(18)
	private String companyRegisterFund;
	
	@NotBlank
	@Max(15)
	private String licenceId;

	@NotBlank
	private String licenceFrontcard;

	@NotBlank
	private String licenceBackcard;
	
	@NotBlank
	@Max(20)
	private String licenceStartPeriod;
	
	@NotBlank
	@Max(20)
	private String licenceEndPeriod;
	
	@NotBlank
	@Max(500)
	private String licenceField;

	@Pattern(regexp = RegexpInfo.ALPHA_CHN)
	@NotBlank
	@Max(40)
	private String legalPerson;

	@Pattern(regexp = RegexpInfo.PHONE_MOBILE)
	@NotBlank
	@Max(18)
	private String legalPhone;

	@NotBlank
	private String authenticationService;
	
	private String isCompanyauthentication;
	
	private String isServiceauthentication;

	private String status;
	
	@NotBlank
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
}


