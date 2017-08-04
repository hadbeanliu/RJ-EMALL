package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlMyCrowdfunding.class)
public abstract class EmlMyCrowdfunding_ {

	public static volatile SingularAttribute<EmlMyCrowdfunding, String> id;
	public static volatile SingularAttribute<EmlMyCrowdfunding, EmlCrowdfundingReturn> emlCrowdfundingReturn;
	public static volatile SingularAttribute<EmlMyCrowdfunding, String> userId;
	public static volatile SingularAttribute<EmlMyCrowdfunding, Date> supportTime;
	public static volatile SingularAttribute<EmlMyCrowdfunding, EmlCrowdfunding> emlCrowdfunding;

}

