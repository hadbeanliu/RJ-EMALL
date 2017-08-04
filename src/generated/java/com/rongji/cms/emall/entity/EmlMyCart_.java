package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlMyCart.class)
public abstract class EmlMyCart_ {

	public static volatile SingularAttribute<EmlMyCart, String> id;
	public static volatile SingularAttribute<EmlMyCart, Date> createTime;
	public static volatile SingularAttribute<EmlMyCart, String> goodsId;
	public static volatile SingularAttribute<EmlMyCart, Integer> buyNumber;
	public static volatile SingularAttribute<EmlMyCart, String> userId;
	public static volatile SingularAttribute<EmlMyCart, String> skuId;
	public static volatile SingularAttribute<EmlMyCart, String> storeId;

}

