package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlAuctionRecord.class)
public abstract class EmlAuctionRecord_ {

	public static volatile SingularAttribute<EmlAuctionRecord, String> id;
	public static volatile SingularAttribute<EmlAuctionRecord, String> goodsId;
	public static volatile SingularAttribute<EmlAuctionRecord, Integer> auctionQuantity;
	public static volatile SingularAttribute<EmlAuctionRecord, Integer> accessQuantity;
	public static volatile SingularAttribute<EmlAuctionRecord, String> userId;
	public static volatile SingularAttribute<EmlAuctionRecord, Date> shootingTime;
	public static volatile SingularAttribute<EmlAuctionRecord, Float> currentPrice;
	public static volatile SingularAttribute<EmlAuctionRecord, Date> endTime;
	public static volatile SingularAttribute<EmlAuctionRecord, Float> myHighestPrice;
	public static volatile SingularAttribute<EmlAuctionRecord, EmlMyBond> myBond;

}

