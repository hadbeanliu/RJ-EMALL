package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlExamine.class)
public abstract class EmlExamine_ {

	public static volatile SingularAttribute<EmlExamine, Date> startTime;
	public static volatile SingularAttribute<EmlExamine, String> id;
	public static volatile SingularAttribute<EmlExamine, String> errorReason;
	public static volatile SingularAttribute<EmlExamine, String> results;
	public static volatile SingularAttribute<EmlExamine, String> status;
	public static volatile SingularAttribute<EmlExamine, String> description;
	public static volatile SingularAttribute<EmlExamine, String> userId;
	public static volatile SingularAttribute<EmlExamine, String> authentications;
	public static volatile SingularAttribute<EmlExamine, String> companyId;
	public static volatile SingularAttribute<EmlExamine, String> errorField;
	public static volatile SingularAttribute<EmlExamine, Date> endTime;

}

