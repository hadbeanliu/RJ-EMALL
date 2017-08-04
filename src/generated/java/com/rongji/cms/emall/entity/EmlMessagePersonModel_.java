package com.rongji.cms.emall.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlMessagePersonModel.class)
public abstract class EmlMessagePersonModel_ {

	public static volatile SingularAttribute<EmlMessagePersonModel, String> smsStatus;
	public static volatile SingularAttribute<EmlMessagePersonModel, String> id;
	public static volatile SingularAttribute<EmlMessagePersonModel, String> userId;
	public static volatile SingularAttribute<EmlMessagePersonModel, String> inMsgStatus;
	public static volatile SingularAttribute<EmlMessagePersonModel, EmlMessageModel> messageModel;
	public static volatile SingularAttribute<EmlMessagePersonModel, String> emailStatus;

}

