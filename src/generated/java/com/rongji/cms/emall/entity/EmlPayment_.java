package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlPayment.class)
public abstract class EmlPayment_ {

	public static volatile SingularAttribute<EmlPayment, String> id;
	public static volatile SingularAttribute<EmlPayment, Date> createTime;
	public static volatile SingularAttribute<EmlPayment, String> createUser;
	public static volatile SingularAttribute<EmlPayment, EmlStore> store;
	public static volatile SingularAttribute<EmlPayment, String> status;
	public static volatile SingularAttribute<EmlPayment, String> payServiceProviderTitle;
	public static volatile SingularAttribute<EmlPayment, String> payServiceProviderDesc;
	public static volatile SingularAttribute<EmlPayment, String> config;
	public static volatile SingularAttribute<EmlPayment, String> payServiceProviderCode;
	public static volatile SingularAttribute<EmlPayment, Date> modifyTime;

}

