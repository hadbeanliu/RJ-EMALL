package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlMyBond.class)
public abstract class EmlMyBond_ {

	public static volatile SingularAttribute<EmlMyBond, String> id;
	public static volatile SingularAttribute<EmlMyBond, String> goodsId;
	public static volatile SingularAttribute<EmlMyBond, EmlOrder> order;
	public static volatile SingularAttribute<EmlMyBond, Float> bondMonery;
	public static volatile SingularAttribute<EmlMyBond, String> submitTime;
	public static volatile SingularAttribute<EmlMyBond, String> userId;
	public static volatile SingularAttribute<EmlMyBond, Date> releaseTime;
	public static volatile SingularAttribute<EmlMyBond, Date> transferTime;

}

