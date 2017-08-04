package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlOrder.class)
public abstract class EmlOrder_ {

	public static volatile SingularAttribute<EmlOrder, Date> dealTime;
	public static volatile SingularAttribute<EmlOrder, Date> createTime;
	public static volatile SingularAttribute<EmlOrder, String> tradeStatus;
	public static volatile SingularAttribute<EmlOrder, String> payCode;
	public static volatile SingularAttribute<EmlOrder, Date> storeConfirmTime;
	public static volatile SingularAttribute<EmlOrder, EmlStore> store;
	public static volatile SingularAttribute<EmlOrder, String> tradeNo;
	public static volatile SingularAttribute<EmlOrder, String> status;
	public static volatile SingularAttribute<EmlOrder, String> remark;
	public static volatile SingularAttribute<EmlOrder, Date> paymentTime;
	public static volatile SingularAttribute<EmlOrder, Date> rateTime;
	public static volatile SingularAttribute<EmlOrder, String> type;
	public static volatile SingularAttribute<EmlOrder, String> carryId;
	public static volatile SingularAttribute<EmlOrder, String> id;
	public static volatile SingularAttribute<EmlOrder, String> createUser;
	public static volatile SingularAttribute<EmlOrder, Date> doneTime;
	public static volatile SingularAttribute<EmlOrder, String> orderNo;
	public static volatile SingularAttribute<EmlOrder, Long> price;
	public static volatile SingularAttribute<EmlOrder, EmlAddress> address;
	public static volatile SingularAttribute<EmlOrder, String> rateId;
	public static volatile SingularAttribute<EmlOrder, String> confirmUser;
	public static volatile SingularAttribute<EmlOrder, Date> modifyTime;
	public static volatile SingularAttribute<EmlOrder, EmlLogistics> logistics;

}

