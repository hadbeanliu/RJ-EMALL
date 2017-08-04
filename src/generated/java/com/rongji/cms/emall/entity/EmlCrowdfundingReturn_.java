package com.rongji.cms.emall.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlCrowdfundingReturn.class)
public abstract class EmlCrowdfundingReturn_ {

	public static volatile SingularAttribute<EmlCrowdfundingReturn, String> id;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, String> returnDetails;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, String> title;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, Integer> releaseDays;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, String> crowdfundingId;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, Integer> returnNumber;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, Float> money;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, Character> logisticsWay;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, String> returnPicture;
	public static volatile SingularAttribute<EmlCrowdfundingReturn, Integer> individualPurchase;

}

