package com.rongji.cms.emall.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmlOrderGoods.class)
public abstract class EmlOrderGoods_ {

	public static volatile SingularAttribute<EmlOrderGoods, String> goodsId;
	public static volatile SingularAttribute<EmlOrderGoods, Date> createTime;
	public static volatile SingularAttribute<EmlOrderGoods, Date> rateTime;
	public static volatile SingularAttribute<EmlOrderGoods, String> goodsImage;
	public static volatile SingularAttribute<EmlOrderGoods, Long> discount;
	public static volatile SingularAttribute<EmlOrderGoods, String> id;
	public static volatile SingularAttribute<EmlOrderGoods, EmlOrder> order;
	public static volatile SingularAttribute<EmlOrderGoods, String> goodsUrl;
	public static volatile SingularAttribute<EmlOrderGoods, String> skuId;
	public static volatile SingularAttribute<EmlOrderGoods, String> rateId;
	public static volatile SingularAttribute<EmlOrderGoods, String> goodsTitle;
	public static volatile SingularAttribute<EmlOrderGoods, Long> goodsPrice;
	public static volatile SingularAttribute<EmlOrderGoods, String> discountTitle;
	public static volatile SingularAttribute<EmlOrderGoods, String> goodsDesc;
	public static volatile SingularAttribute<EmlOrderGoods, Date> modifyTime;
	public static volatile SingularAttribute<EmlOrderGoods, Long> purchasePrice;
	public static volatile SingularAttribute<EmlOrderGoods, Integer> purchaseQuantity;

}

