package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlMyAttention.class)
public abstract class EmlMyAttention_ {

	public static volatile SingularAttribute<EmlMyAttention, String> id;
	public static volatile SingularAttribute<EmlMyAttention, Date> createTime;
	public static volatile SingularAttribute<EmlMyAttention, String> goodsId;
	public static volatile SingularAttribute<EmlMyAttention, String> attentionType;
	public static volatile SingularAttribute<EmlMyAttention, String> userId;
	public static volatile SingularAttribute<EmlMyAttention, String> storeId;

}

