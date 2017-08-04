package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlRefund.class)
public abstract class EmlRefund_ {

	public static volatile SingularAttribute<EmlRefund, String> refundStatus;
	public static volatile SingularAttribute<EmlRefund, EmlOrder> order;
	public static volatile SingularAttribute<EmlRefund, String> refundId;
	public static volatile SingularAttribute<EmlRefund, String> status;
	public static volatile SingularAttribute<EmlRefund, Integer> refundSum;
	public static volatile SingularAttribute<EmlRefund, String> buyerRefundReason;
	public static volatile SingularAttribute<EmlRefund, Date> creatTime;
	public static volatile SingularAttribute<EmlRefund, Date> endTime;
	public static volatile SingularAttribute<EmlRefund, String> orderStatus;
	public static volatile SingularAttribute<EmlRefund, String> handlingSuggestion;

}

