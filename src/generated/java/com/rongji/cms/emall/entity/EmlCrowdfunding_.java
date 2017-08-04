package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlCrowdfunding.class)
public abstract class EmlCrowdfunding_ {

	public static volatile SingularAttribute<EmlCrowdfunding, Date> createTime;
	public static volatile SingularAttribute<EmlCrowdfunding, String> projectOutline;
	public static volatile SingularAttribute<EmlCrowdfunding, Character> postCategories;
	public static volatile SingularAttribute<EmlCrowdfunding, Character> crowdfundingStatus;
	public static volatile SingularAttribute<EmlCrowdfunding, String> projectPicture;
	public static volatile SingularAttribute<EmlCrowdfunding, Integer> raisingDays;
	public static volatile SingularAttribute<EmlCrowdfunding, Float> targetAmount;
	public static volatile SingularAttribute<EmlCrowdfunding, Date> endTime;
	public static volatile SingularAttribute<EmlCrowdfunding, String> id;
	public static volatile SingularAttribute<EmlCrowdfunding, Date> startTime;
	public static volatile SingularAttribute<EmlCrowdfunding, Float> currentTotalFunding;
	public static volatile SingularAttribute<EmlCrowdfunding, Integer> supportNumber;
	public static volatile SingularAttribute<EmlCrowdfunding, String> userId;
	public static volatile SingularAttribute<EmlCrowdfunding, String> projectDescription;
	public static volatile SingularAttribute<EmlCrowdfunding, Integer> depositRatioOf;
	public static volatile SingularAttribute<EmlCrowdfunding, String> storeId;
	public static volatile SingularAttribute<EmlCrowdfunding, EmlCrowdfundingSponsor> crowdfundingSponsor;
	public static volatile SingularAttribute<EmlCrowdfunding, String> projectName;
	public static volatile SingularAttribute<EmlCrowdfunding, Float> achievementRate;

}

