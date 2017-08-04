package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlMyBrowse.class)
public abstract class EmlMyBrowse_ {

	public static volatile SingularAttribute<EmlMyBrowse, String> id;
	public static volatile SingularAttribute<EmlMyBrowse, Date> createTime;
	public static volatile SingularAttribute<EmlMyBrowse, String> goodsId;
	public static volatile SingularAttribute<EmlMyBrowse, Integer> perNum;
	public static volatile SingularAttribute<EmlMyBrowse, String> goodsCategory;
	public static volatile SingularAttribute<EmlMyBrowse, String> userId;
	public static volatile SingularAttribute<EmlMyBrowse, String> currLast;

}

