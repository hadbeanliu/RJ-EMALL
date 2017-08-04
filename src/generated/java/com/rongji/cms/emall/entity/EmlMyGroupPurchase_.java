package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlMyGroupPurchase.class)
public abstract class EmlMyGroupPurchase_ {

	public static volatile SingularAttribute<EmlMyGroupPurchase, String> id;
	public static volatile SingularAttribute<EmlMyGroupPurchase, String> goodsId;
	public static volatile SingularAttribute<EmlMyGroupPurchase, Character> purchaseGoodsState;
	public static volatile SingularAttribute<EmlMyGroupPurchase, Date> joinTime;
	public static volatile SingularAttribute<EmlMyGroupPurchase, String> userId;
	public static volatile SingularAttribute<EmlMyGroupPurchase, String> goodsTitle;
	public static volatile SingularAttribute<EmlMyGroupPurchase, Float> purchaseGoodsMonery;
	public static volatile SingularAttribute<EmlMyGroupPurchase, Integer> purchaseGoodsNumber;

}

